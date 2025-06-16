package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_get_picture_type_char

public actual fun AVPictureType.getPictureTypeChar(): Char {
    return av_get_picture_type_char(value.toUInt()).toInt().toChar()
}