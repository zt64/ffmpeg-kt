package dev.zt64.ffmpegkt.avutil

internal typealias AVClassNative = org.bytedeco.ffmpeg.avutil.AVClass

public actual class AVClass(internal val native: AVClassNative) {
    public actual val className: String = native.class_name().string
    public actual val version: Int = native.version()

    override fun toString(): String {
        return "AVClass(className='$className', version=$version)"
    }
}