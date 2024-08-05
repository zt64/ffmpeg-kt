package dev.zt64.ffmpegkt.avutil

internal typealias NativeAVRational = org.bytedeco.ffmpeg.avutil.AVRational

public fun AVRational(native: NativeAVRational): AVRational {
    return AVRational(native.num(), native.den())
}

public fun NativeAVRational(rational: AVRational): NativeAVRational {
    return NativeAVRational(rational.first, rational.second)
}

public fun NativeAVRational(num: Int, den: Int): NativeAVRational {
    return NativeAVRational().apply {
        num(num)
        den(den)
    }
}