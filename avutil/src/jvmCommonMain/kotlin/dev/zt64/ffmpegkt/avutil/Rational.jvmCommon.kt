package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.avutil.AVRational

public fun Rational(native: AVRational): Rational {
    return Rational(native.num(), native.den())
}