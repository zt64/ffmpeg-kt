package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.IntPointer

internal typealias NativeAVCodecParserContext = org.bytedeco.ffmpeg.avcodec.AVCodecParserContext

public actual class CodecParserContext(
    @PublishedApi
    internal val native: NativeAVCodecParserContext
) : AutoCloseable {
    public actual constructor(codec: AVCodecID) : this(av_parser_init(codec.num))

    private val packet = AVPacket()

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

        inputPointer.deallocate()
        outputPointer.deallocate()
        outputSizePointer.deallocate()

        return ParsedPacket(packet, read)
    }

    public override fun close() {
        av_parser_close(native)
        packet.close()
    }
}