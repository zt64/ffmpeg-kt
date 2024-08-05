package dev.zt64.ffmpegkt.avformat

import kotlin.jvm.JvmInline

public expect class NativeAVIOContext

@JvmInline
public expect value class AVIOContext(internal val native: NativeAVIOContext) : AutoCloseable {
    public constructor(filename: String, flags: Int)
}