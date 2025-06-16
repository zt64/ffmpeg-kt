package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_get_media_type_string
import kotlinx.cinterop.toKString

internal actual fun MediaType.getString(): String? {
    return av_get_media_type_string(num)?.toKString()
}