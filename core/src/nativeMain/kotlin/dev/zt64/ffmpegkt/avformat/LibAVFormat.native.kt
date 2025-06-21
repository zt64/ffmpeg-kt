package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.FfmpegLibrary
import ffmpeg.*
import kotlinx.cinterop.toKString

public actual object LibAVFormat : FfmpegLibrary {
    public actual override fun version(): Int = avformat_version().toInt()

    public actual override fun configuration(): String {
        return avformat_configuration()?.toKString().orEmpty()
    }

    public actual override fun license(): String {
        return avformat_license()?.toKString().orEmpty()
    }

    public actual fun networkInit(): Int = avformat_network_init()
    public actual fun networkDeinit(): Int = avformat_network_deinit()
}