package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.FfmpegLibrary
import org.bytedeco.ffmpeg.global.avcodec.*

public actual object LibAVCodec : FfmpegLibrary {
    public actual override fun version(): Int = avcodec_version()
    public actual override fun configuration(): String = avcodec_configuration().string
    public actual override fun license(): String = avcodec_license().string
}