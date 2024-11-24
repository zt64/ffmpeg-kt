package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.global.avutil.av_get_pix_fmt_name

internal actual fun PixelFormat.commonToString(): String? {
    return av_get_pix_fmt_name(num).string
}