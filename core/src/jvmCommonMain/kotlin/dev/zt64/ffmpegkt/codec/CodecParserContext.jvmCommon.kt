package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.util.FfmpegException
import dev.zt64.ffmpegkt.avutil.util.checkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.IntPointer

internal typealias NativeAVCodecParserContext = org.bytedeco.ffmpeg.avcodec.AVCodecParserContext

public actual class CodecParserContext(
    @PublishedApi
    internal val native: NativeAVCodecParserContext
) : AutoCloseable {
    public actual constructor(codec: CodecID) : this(av_parser_init(codec.num))

    private val packet = Packet()

    public actual inline val parser: AVCodecParser
        get() = AVCodecParser(native.parser())

    public actual inline val frameOffset: Long
        get() = native.frame_offset()

    public actual fun parse(
        avCtx: CodecContext,
        input: ByteArray,
        dataSize: Int,
        pts: Long,
        dts: Long,
        pos: Long
    ): ParsedPacket {
        // Initialize BytePointer and IntPointer
        val inputPointer = BytePointer(*input)
        val outputPointer = BytePointer(5.toByte())
        val outputSizePointer = IntPointer(0)
        val packet = Packet()

        // Call the native function
        val read = av_parser_parse2(
            /* s = */ native,
            /* avctx = */ avCtx.native,
            /* poutbuf = */ outputPointer,
            /* poutbuf_size = */ outputSizePointer,
            /* buf = */ inputPointer,
            /* buf_size = */ dataSize,
            /* pts = */ pts,
            /* dts = */ dts,
            /* pos = */ pos
        ).checkError()

        packet.native.data(outputPointer)
        packet.native.size(outputSizePointer.get())

        // inputPointer.deallocate()
        outputPointer.deallocate()
        outputSizePointer.deallocate()

        return ParsedPacket(packet, read)
    }

    public actual fun parsePackets(
        avCtx: CodecContext,
        input: ByteArray,
        pts: Long,
        dts: Long,
        pos: Long
    ): Flow<ParsedPacket> {
        val packets = mutableListOf<ParsedPacket>()
        var currentPosition = 0
        val chunkSize = 4096

        return flow {
            while (currentPosition < input.size) {
                // Calculate how much data is left to process
                val remainingBytes = input.size - currentPosition
                val currentChunkSize = minOf(chunkSize, remainingBytes)

                // Create a chunk of data with padding
                val chunk = ByteArray(currentChunkSize + 64) // 64 bytes padding
                System.arraycopy(input, currentPosition, chunk, 0, currentChunkSize)

                var dataSize = currentChunkSize
                var shouldContinue = true

                while (dataSize > 0 && shouldContinue) {
                    try {
                        val parsedPacket = parse(avCtx, chunk, dataSize)

                        if (parsedPacket.packet.size > 0) {
                            // Add packet to list only if it has actual content
                            emit(parsedPacket)
                        }

                        if (parsedPacket.bytesRead <= 0) {
                            shouldContinue = false
                            continue
                        }

                        // Shift remaining data to start of buffer
                        if (parsedPacket.bytesRead < dataSize) {
                            System.arraycopy(
                                chunk,
                                parsedPacket.bytesRead,
                                chunk,
                                0,
                                dataSize - parsedPacket.bytesRead
                            )
                        }

                        dataSize -= parsedPacket.bytesRead
                    } catch (e: FfmpegException) {
                        if (e.code == -ERROR_EOF) { // AVERROR_EOF
                            shouldContinue = false
                            continue
                        }
                        throw e
                    }
                }

                currentPosition += if (dataSize > 0) {
                    (currentChunkSize - dataSize)
                } else {
                    currentChunkSize
                }
            }
        }
    }

    public override fun close() {
        av_parser_close(native)
        packet.close()
    }
}