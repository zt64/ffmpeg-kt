package dev.zt64.ffmpegkt.avutil

public expect class NativeAVClass

public expect value class AVClass internal constructor(internal val native: NativeAVClass) {
    public val className: String
    public val version: Int
}