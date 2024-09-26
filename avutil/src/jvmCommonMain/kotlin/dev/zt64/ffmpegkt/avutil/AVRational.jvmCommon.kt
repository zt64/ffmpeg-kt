package dev.zt64.ffmpegkt.avutil

public actual typealias AVRational = org.bytedeco.ffmpeg.avutil.AVRational

public actual inline val AVRational.num: Int
    get() = num()

public actual inline val AVRational.den: Int
    get() = den()

public actual fun AVRational(num: Int, den: Int): AVRational {
    return AVRational().apply {
        num(num)
        den(den)
    }
}