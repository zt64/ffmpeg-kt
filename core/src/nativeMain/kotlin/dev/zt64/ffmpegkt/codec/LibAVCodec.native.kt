package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.FfmpegLibrary
import ffmpeg.avcodec_configuration
import ffmpeg.avcodec_license
import ffmpeg.avcodec_version
import kotlinx.cinterop.toKString

public actual object LibAVCodec : FfmpegLibrary {
    public actual override fun version(): Int = avcodec_version().toInt()
    public actual override fun configuration(): String = avcodec_configuration()?.toKString().orEmpty()
    public actual override fun license(): String = avcodec_license()?.toKString().orEmpty()
}