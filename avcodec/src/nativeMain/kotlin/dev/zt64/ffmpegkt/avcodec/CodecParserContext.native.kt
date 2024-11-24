package dev.zt64.ffmpegkt.avcodec

import ffmpeg.*
import kotlinx.cinterop.*

public actual class CodecParserContext(private val native: AVCodecParserContext) : AutoCloseable {
    public actual constructor(codec: AVCodecID) : this(av_parser_init(codec.num)!!.pointed)

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
        TODO()
    }

    override fun close() {
        av_parser_close(native.ptr)
    }
}