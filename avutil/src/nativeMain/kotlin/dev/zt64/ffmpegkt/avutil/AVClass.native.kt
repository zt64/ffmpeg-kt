package dev.zt64.ffmpegkt.avutil

import kotlinx.cinterop.toKString

public actual typealias NativeAVClass = ffmpeg.AVClass

public actual value class AVClass(internal val native: NativeAVClass) {
    public actual val className: String
        get() = native.class_name!!.toKString()
    public actual val version: Int
        get() = native.version
}