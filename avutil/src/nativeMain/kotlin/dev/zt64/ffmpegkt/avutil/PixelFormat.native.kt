package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_get_pix_fmt_name
import kotlinx.cinterop.toKString

internal actual fun PixelFormat.commonToString(): String? {
    return av_get_pix_fmt_name(num)?.toKString() ?: num.toString()
}