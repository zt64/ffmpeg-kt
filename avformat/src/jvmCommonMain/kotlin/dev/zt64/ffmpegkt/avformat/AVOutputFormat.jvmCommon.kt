package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodecID
import dev.zt64.ffmpegkt.avutil.AVClass

internal actual typealias NativeAVOutputFormat = org.bytedeco.ffmpeg.avformat.AVOutputFormat

@JvmInline
public actual value class AVOutputFormat(public val native: NativeAVOutputFormat) {
    public actual inline val name: String
        get() = native.name().string
    public actual inline val longName: String
        get() = native.long_name().string
    public actual inline val mimeType: String?
        get() = native.mime_type()?.string
    public actual inline val extensions: String?
        get() = native.extensions()?.string
    public actual inline val audioCodec: AVCodecID
        get() = AVCodecID(native.audio_codec())
    public actual inline val videoCodec: AVCodecID
        get() = AVCodecID(native.video_codec())
    public actual inline val subtitleCodec: AVCodecID
        get() = AVCodecID(native.subtitle_codec())
    public actual inline val flags: Int
        get() = native.flags()
    public actual inline val codecTag: List<AVCodecTag>
        get() = emptyList()
    public actual inline val privClass: AVClass
        get() = AVClass(native.priv_class())

    override fun toString(): String = commonToString()
}