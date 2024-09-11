package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.FfmpegLibrary
import ffmpeg.*
import kotlinx.cinterop.toKString

public actual object LibAVFormat : FfmpegLibrary {
    override fun version(): Int = avformat_version().toInt()

    override fun configuration(): String {
        return avformat_configuration()?.toKString().orEmpty()
    }

    override fun license(): String {
        return avformat_license()?.toKString().orEmpty()
    }

    public actual fun networkInit(): Int = avformat_network_init()

    public actual fun networkDeinit(): Int = avformat_network_deinit()
}