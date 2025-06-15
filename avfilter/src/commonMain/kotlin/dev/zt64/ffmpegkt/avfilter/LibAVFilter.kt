package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.FfmpegLibrary

public expect object LibAVFilter : FfmpegLibrary {
    override fun version(): Int
    override fun configuration(): String
    override fun license(): String
}