package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.Library
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.checkError
import ffmpeg.*
import kotlinx.cinterop.*

public actual object AVFormat : Library {
    override fun version(): Int = avformat_version().toInt()

    override fun configuration(): String {
        return avformat_configuration()?.toKString().orEmpty()
    }

    override fun license(): String {
        return avformat_license()?.toKString().orEmpty()
    }

    public actual fun networkInit(): Int = avformat_network_init()

    public actual fun networkDeinit(): Int = avformat_network_deinit()

    public actual fun openInput(
        url: String,
        format: AVInputFormat?,
        options: AVDictionary?
    ): AVFormatContext {
        val formatContext = avformat_alloc_context()
            ?: error("avformat_alloc_context returned null")

        memScoped {
            avformat_open_input(cValuesOf(formatContext), url, format?.ptr, cValuesOf(options))
                .checkError()
        }

        return formatContext.pointed
    }
}