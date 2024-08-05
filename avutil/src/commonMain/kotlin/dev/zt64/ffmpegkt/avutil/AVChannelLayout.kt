package dev.zt64.ffmpegkt.avutil

import kotlin.jvm.JvmInline

public expect class NativeAVChannelLayout

@JvmInline
public expect value class AVChannelLayout(internal val native: NativeAVChannelLayout) {
    public val order: Int
    public val nbChannels: Int

    public fun copyTo(dst: AVChannelLayout)
}