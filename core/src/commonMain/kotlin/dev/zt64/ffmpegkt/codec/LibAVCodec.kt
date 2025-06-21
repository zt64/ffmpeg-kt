package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.FfmpegLibrary

public expect object LibAVCodec : FfmpegLibrary {
    public override fun version(): Int
    public override fun configuration(): String
    public override fun license(): String
}