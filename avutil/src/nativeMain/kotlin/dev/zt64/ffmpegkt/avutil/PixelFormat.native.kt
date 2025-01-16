package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_get_pix_fmt_name
import kotlinx.cinterop.toKString

public actual fun VideoFormat(name: String): VideoFormat {
    TODO("Not yet implemented")
}

internal actual fun PixelFormat(name: String): PixelFormat {
    return PixelFormat(ffmpeg.av_get_pix_fmt(name))
}

internal actual fun PixelFormat.commonToString(): String? {
    return av_get_pix_fmt_name(num)?.toKString() ?: num.toString()
}