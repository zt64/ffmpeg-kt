package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.Library
import dev.zt64.ffmpegkt.avutil.AVDictionary

public expect object AVFormat : Library {
    public fun networkInit(): Int

    public fun networkDeinit(): Int

    public fun openInput(
        url: String,
        format: AVInputFormat? = null,
        options: AVDictionary? = null
    ): AVFormatContext
}