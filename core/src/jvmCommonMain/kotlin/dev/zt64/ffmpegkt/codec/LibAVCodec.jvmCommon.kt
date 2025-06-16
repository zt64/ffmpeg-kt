package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.FfmpegLibrary
import org.bytedeco.ffmpeg.global.avcodec.*

public actual object LibAVCodec : FfmpegLibrary {
    override fun version(): Int = avcodec_version()

    override fun configuration(): String = avcodec_configuration().string

    override fun license(): String = avcodec_license().string
}