package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.Library
import ffmpeg.avcodec_configuration
import ffmpeg.avcodec_license
import ffmpeg.avcodec_version
import kotlinx.cinterop.toKString

public actual object AVCodecVersion : Library {
    override fun version(): Int {
        return avcodec_version().toInt()
    }

    override fun configuration(): String {
        return avcodec_configuration()?.toKString().orEmpty()
    }

    override fun license(): String {
        return avcodec_license()?.toKString().orEmpty()
    }
}