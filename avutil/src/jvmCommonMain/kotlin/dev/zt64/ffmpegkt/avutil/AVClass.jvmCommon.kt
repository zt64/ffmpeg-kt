package dev.zt64.ffmpegkt.avutil

public actual typealias NativeAVClass = org.bytedeco.ffmpeg.avutil.AVClass

@JvmInline
public actual value class AVClass(@PublishedApi internal val native: NativeAVClass) {
    public actual inline val className: String
        get() = native.class_name().string
    public actual inline val version: Int
        get() = native.version()
}