package dev.zt64.ffmpegkt.postproc

import dev.zt64.ffmpegkt.FfmpegLibrary
import org.bytedeco.ffmpeg.global.postproc.*

public actual object Postproc : FfmpegLibrary {
    override fun version(): Int {
        return postproc_version()
    }

    override fun configuration(): String {
        return postproc_configuration().string
    }

    override fun license(): String {
        return postproc_license().string
    }
}