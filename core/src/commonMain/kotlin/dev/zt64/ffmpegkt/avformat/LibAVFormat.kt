package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.FfmpegLibrary

public expect object LibAVFormat : FfmpegLibrary {
    public override fun version(): Int
    public override fun configuration(): String
    public override fun license(): String
    public fun networkInit(): Int
    public fun networkDeinit(): Int
}