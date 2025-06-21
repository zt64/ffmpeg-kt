package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.util.FfmpegException
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.javacpp.Pointer

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
        val inputPointer = BytePointer(*input)
        val outputPointer = BytePointer()
        val outputSizePointer = IntPointer(1)

        try {
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

            val packet = Packet()
            val parsedSize = outputSizePointer.get()

            if (parsedSize > 0) {
                // Allocate a new buffer owned by the packet
                av_new_packet(packet.native, parsedSize).checkError()
                // Copy data from the parser's temporary buffer to the packet's buffer
                Pointer.memcpy(packet.native.data(), outputPointer, parsedSize.toLong())
            }

            return ParsedPacket(packet, read)
        } finally {
            inputPointer.deallocate()
            outputSizePointer.deallocate()
        }
    }

    public actual fun parsePackets(
        avCtx: CodecContext,
        input: ByteArray,
        pts: Long,
        dts: Long,
        pos: Long
    ): List<ParsedPacket> {
        val packets = mutableListOf<ParsedPacket>()
        var currentPosition = 0
        val chunkSize = 4096

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
                        packets.add(parsedPacket)
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

        return packets
    }

    public override fun close() {
        av_parser_close(native)
        packet.close()
    }
}