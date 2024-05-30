package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.Library
import dev.zt64.ffmpegkt.avutil.AVDictionary
import org.bytedeco.ffmpeg.global.avformat.*

public actual object AVFormat : Library {
    override fun version(): Int = avformat_version()

    override fun configuration(): String = avformat_configuration().string

    override fun license(): String = avformat_license().string

    public actual fun networkInit(): Int = TODO("Not yet implemented")

    public actual fun networkDeinit(): Int = TODO("Not yet implemented")

    public actual fun openInput(
        url: String,
        format: AVInputFormat?,
        options: AVDictionary?
    ): AVFormatContext {
        val formatContext = avformat_alloc_context()

        avformat_open_input(formatContext, url, format, options)

        return formatContext
    }
}