package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.util.FfmpegException
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.avcodec.AVPacket
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.javacpp.Pointer

internal typealias NativeAVCodecParserContext = org.bytedeco.ffmpeg.avcodec.AVCodecParserContext

public actual class CodecParserContext(
    public val codecContext: CodecContext,
    @PublishedApi
    internal val native: NativeAVCodecParserContext
) : AutoCloseable {
    public actual constructor(codec: CodecContext) : this(
        codec,
        av_parser_init(codec.native.codec_id())
    )
    private val packet = AVPacket()

    public actual inline val parser: AVCodecParser
        get() = AVCodecParser(native.parser())

    public actual inline val frameOffset: Long
        get() = native.frame_offset()

    public actual fun parse(
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
                /* avctx = */ codecContext.native,
                /* poutbuf = */ outputPointer,
                /* poutbuf_size = */ outputSizePointer,
                /* buf = */ inputPointer,
                /* buf_size = */ dataSize,
                /* pts = */ pts,
                /* dts = */ dts,
                /* pos = */ pos
            ).checkError()

            val parsedSize = outputSizePointer.get()

            if (parsedSize > 0) {
                // Allocate a new buffer owned by the packet
                av_new_packet(packet, parsedSize).checkError()
                // Copy data from the parser's temporary buffer to the packet's buffer
                Pointer.memcpy(packet.data(), outputPointer, parsedSize.toLong())
            }

            return ParsedPacket(Packet(packet), read)
        } finally {
            inputPointer.deallocate()
            outputSizePointer.deallocate()
        }
    }

    public actual fun parsePackets(input: ByteArray): List<ParsedPacket> {
        val packets = mutableListOf<ParsedPacket>()
        var currentPosition = 0
        val chunkSize = 4096 // TODO: Hardcode this? or make it configurable?

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
                    val parsedPacket = parse(chunk, dataSize)

                    // Add packet to list only if it has actual content
                    if (parsedPacket.packet.size > 0) packets += parsedPacket

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
                    if (e.code == -ERROR_EOF) {
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

    public actual override fun close() {
        av_parser_close(native)
        av_packet_free(packet)
    }
}