package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.FfmpegLibrary
import org.bytedeco.ffmpeg.global.avformat.*

public actual object LibAVFormat : FfmpegLibrary {
    public actual override fun version(): Int = avformat_version()
    public actual override fun configuration(): String = avformat_configuration().string
    public actual override fun license(): String = avformat_license().string
    public actual fun networkInit(): Int = avformat_network_init()
    public actual fun networkDeinit(): Int = avformat_network_deinit()
}