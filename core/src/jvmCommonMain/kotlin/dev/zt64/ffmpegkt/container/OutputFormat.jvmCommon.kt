package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avformat.CodecTag
import dev.zt64.ffmpegkt.codec.CodecID
import org.bytedeco.ffmpeg.avformat.AVOutputFormat

internal actual typealias NativeAVOutputFormat = AVOutputFormat

@JvmInline
public actual value class OutputFormat(public val native: NativeAVOutputFormat) {
    public actual inline val name: String
        get() = native.name().string
    public actual inline val longName: String
        get() = native.long_name().string
    public actual inline val mimeType: String?
        get() = native.mime_type()?.string
    public actual inline val extensions: String?
        get() = native.extensions()?.string
    public actual inline val audioCodec: CodecID
        get() = CodecID(native.audio_codec())
    public actual inline val videoCodec: CodecID
        get() = CodecID(native.video_codec())
    public actual inline val subtitleCodec: CodecID
        get() = CodecID(native.subtitle_codec())
    public actual inline val flags: Int
        get() = native.flags()
    public actual inline val codecTag: List<CodecTag>
        get() = emptyList()

    override fun toString(): String = commonToString()
}