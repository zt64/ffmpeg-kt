package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.FfmpegLibrary

public expect object LibAVFormat : FfmpegLibrary {
    public fun networkInit(): Int

    public fun networkDeinit(): Int
}