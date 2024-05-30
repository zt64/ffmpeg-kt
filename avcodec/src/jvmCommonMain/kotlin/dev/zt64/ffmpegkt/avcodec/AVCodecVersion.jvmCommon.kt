package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.Library
import org.bytedeco.ffmpeg.global.avcodec.*

public actual object AVCodecVersion : Library {
    override fun version(): Int = avcodec_version()

    override fun configuration(): String = avcodec_configuration().string

    override fun license(): String = avcodec_license().string
}