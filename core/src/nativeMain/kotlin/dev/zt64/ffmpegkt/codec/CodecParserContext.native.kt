package dev.zt64.ffmpegkt.codec

import ffmpeg.*
import kotlinx.cinterop.*

public actual class CodecParserContext(
    public val codecContext: CodecContext,
    @PublishedApi
    internal val native: AVCodecParserContext
) : AutoCloseable {
    public actual constructor(codec: CodecContext) : this(codec, av_parser_init(codec.codecId.num)!!.pointed)

    private val packet = Packet()

    public actual val parser: AVCodecParser
        get() = AVCodecParser(native.parser!!.pointed)
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

            packet.native.data = outputBuf.value
            packet.native.size = outputSizeBuf.value

            ParsedPacket(packet, read)
        }
    }

    public actual fun parsePackets(input: ByteArray): List<ParsedPacket> {
        TODO()
    }

    public actual override fun close() {
        av_parser_close(native.ptr)
    }
}