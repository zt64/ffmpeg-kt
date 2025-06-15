package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.FfmpegLibrary
import ffmpeg.avfilter_configuration
import ffmpeg.avfilter_license
import ffmpeg.avfilter_version
import kotlinx.cinterop.toKString

public actual object LibAVFilter : FfmpegLibrary {
    actual override fun version(): Int = avfilter_version().toInt()
    actual override fun configuration(): String = avfilter_configuration()!!.toKString()
    actual override fun license(): String = avfilter_license()!!.toKString()
}