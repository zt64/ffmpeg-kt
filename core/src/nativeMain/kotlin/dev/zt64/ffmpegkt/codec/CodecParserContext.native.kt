package dev.zt64.ffmpegkt.codec

import ffmpeg.*
import kotlinx.cinterop.*

public actual class CodecParserContext(private val native: AVCodecParserContext) : AutoCloseable {
    public actual constructor(codec: CodecID) : this(av_parser_init(codec.num)!!.pointed)

    private val packet = Packet()

    public actual val parser: AVCodecParser
        get() = AVCodecParser(native.parser!!.pointed)
    public actual val frameOffset: Long
        get() = native.frame_offset

    public actual fun parse(
        avCtx: CodecContext,
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
                avctx = avCtx.native.ptr,
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

    public actual fun parsePackets(
        avCtx: CodecContext,
        input: ByteArray,
        pts: Long,
        dts: Long,
        pos: Long
    ): List<ParsedPacket> {
        TODO()
    }

    public actual override fun close() {
        av_parser_close(native.ptr)
    }
}