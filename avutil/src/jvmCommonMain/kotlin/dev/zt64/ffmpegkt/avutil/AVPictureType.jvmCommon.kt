package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.global.avutil.av_get_picture_type_char

public actual fun AVPictureType.getPictureTypeChar(): Char {
    return av_get_picture_type_char(value).toInt().toChar()
}