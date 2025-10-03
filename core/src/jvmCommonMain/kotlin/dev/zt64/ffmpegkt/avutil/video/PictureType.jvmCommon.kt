package dev.zt64.ffmpegkt.avutil.video

import org.bytedeco.ffmpeg.global.avutil.av_get_picture_type_char

public actual fun PictureType.getPictureTypeChar(): Char {
    return av_get_picture_type_char(value).toInt().toChar()
}