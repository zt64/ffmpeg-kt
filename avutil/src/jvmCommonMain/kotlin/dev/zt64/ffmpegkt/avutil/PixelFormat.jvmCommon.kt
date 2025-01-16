package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.avutil.AVPixFmtDescriptor
import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.ffmpeg.global.avutil.av_get_pix_fmt
import org.bytedeco.ffmpeg.global.avutil.av_get_pix_fmt_name

public actual fun VideoFormat(name: String): VideoFormat {
    return JvmVideoFormat(name)
}

@JvmInline
private value class JvmVideoFormat(private val num: Int) : VideoFormat {
    private val desc: AVPixFmtDescriptor get() = org.bytedeco.ffmpeg.global.avutil.av_pix_fmt_desc_get(num)
    constructor(name: String) : this(av_get_pix_fmt(name))

    override val name: String
        get() = desc.name().string
    override val isPlanar: Boolean
        get() = desc.flags() and avutil.AV_PIX_FMT_FLAG_PLANAR.toLong() != 0L
    override val isRgb: Boolean
        get() = desc.flags() and avutil.AV_PIX_FMT_FLAG_RGB.toLong() != 0L
}

internal actual fun PixelFormat(name: String): PixelFormat {
    return PixelFormat(av_get_pix_fmt(name))
}
internal actual fun PixelFormat.commonToString(): String? {
    return av_get_pix_fmt_name(num).string
}