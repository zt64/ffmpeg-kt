package dev.zt64.ffmpegkt.postproc

import dev.zt64.ffmpegkt.Library
import org.bytedeco.ffmpeg.global.postproc.*

public actual object Postproc : Library {
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