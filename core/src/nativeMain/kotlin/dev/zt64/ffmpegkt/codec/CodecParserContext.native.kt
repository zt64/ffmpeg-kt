package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.*
import kotlinx.cinterop.*
import platform.posix.memcpy

public actual class CodecParserContext(
    public val codecContext: CodecContext,
    @PublishedApi
    internal val native: AVCodecParserContext
) : AutoCloseable {
    public actual constructor(codec: CodecContext) : this(codec, av_parser_init(codec.codecId.num)!!.pointed)

    private val packet = av_packet_alloc()!!.pointed

    public actual val parser: CodecParser
        get() = CodecParser(native.parser!!.pointed)
    public actual val frameOffset: Long
        get() = native.frame_offset

    public actual fun parse(
        input: ByteArray,
        dataSize: Int,
        pts: Long,
        dts: Long,
        pos: Long
    ): ParsedPacket {
        return memScoped {
            val outputBuf = alloc<CPointerVar<UByteVar>>()
            val outputSizeBuf = alloc<IntVar>()

            val read = av_parser_parse2(
                s = native.ptr,
                avctx = codecContext.native.ptr,
                poutbuf = outputBuf.ptr,
                poutbuf_size = outputSizeBuf.ptr,
                buf = input.asUByteArray().refTo(0),
                buf_size = dataSize,
                pts = pts,
                dts = dts,
                pos = pos
            )

            packet.data = outputBuf.value
            packet.size = outputSizeBuf.value

            if (read > 0) {
                // Allocate a new buffer owned by the packet
                av_new_packet(packet.ptr, read).checkError()
                // Copy data from the parser's temporary buffer to the packet's buffer
                memcpy(packet.data, outputBuf.ptr, read.toULong())
            }

            ParsedPacket(Packet(packet), read)
        }
    }

    public actual fun parsePackets(input: ByteArray): List<ParsedPacket> {
        TODO()
    }

    public actual override fun close() {
        av_parser_close(native.ptr)
    }
}