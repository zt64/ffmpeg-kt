package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.CodecID
import dev.zt64.ffmpegkt.avutil.AVClass
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString

public actual class AVOutputFormat(public val native: ffmpeg.AVOutputFormat) {
    public actual inline val name: String
        get() = native.name!!.toKString()
    public actual inline val longName: String
        get() = native.long_name!!.toKString()
    public actual inline val mimeType: String
        get() = native.mime_type!!.toKString()
    public actual inline val extensions: String
        get() = native.extensions!!.toKString()
    public actual inline val audioCodec: CodecID
        get() = CodecID(native.audio_codec.toInt())
    public actual inline val videoCodec: CodecID
        get() = CodecID(native.video_codec.toInt())
    public actual inline val subtitleCodec: CodecID
        get() = CodecID(native.subtitle_codec.toInt())
    public actual inline val flags: Int
        get() = native.flags
    public actual inline val codecTag: List<AVCodecTag>
        get() = TODO()
    public actual inline val privClass: AVClass
        get() = AVClass(native.priv_class!!.pointed)
}