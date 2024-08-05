package dev.zt64.ffmpegkt.postproc

import dev.zt64.ffmpegkt.FfmpegLibrary
import ffmpeg.postproc_configuration
import ffmpeg.postproc_license
import ffmpeg.postproc_version
import kotlinx.cinterop.toKString

public actual object Postproc : FfmpegLibrary {
    override fun version(): Int {
        return postproc_version().toInt()
    }

    override fun configuration(): String {
        return postproc_configuration()?.toKString().orEmpty()
    }

    override fun license(): String {
        return postproc_license()?.toKString().orEmpty()
    }
}