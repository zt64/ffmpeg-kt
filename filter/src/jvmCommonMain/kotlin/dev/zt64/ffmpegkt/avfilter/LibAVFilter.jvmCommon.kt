package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.FfmpegLibrary
import org.bytedeco.ffmpeg.global.avfilter.*

public actual object LibAVFilter : FfmpegLibrary {
    actual override fun version(): Int = avfilter_version()

    actual override fun configuration(): String = avfilter_configuration().string

    actual override fun license(): String = avfilter_license().string
}