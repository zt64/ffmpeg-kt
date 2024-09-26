package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.global.avutil

internal actual fun AVMediaType.getString(): String? {
    return avutil.av_get_media_type_string(num).string
}