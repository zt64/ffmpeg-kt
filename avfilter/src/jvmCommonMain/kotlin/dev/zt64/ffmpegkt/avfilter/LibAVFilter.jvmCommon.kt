package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.FfmpegLibrary
import org.bytedeco.ffmpeg.global.avfilter.*

public actual object LibAVFilter : FfmpegLibrary {
    override fun version(): Int = avfilter_version()

    override fun configuration(): String = avfilter_configuration().string

    override fun license(): String = avfilter_license().string
}