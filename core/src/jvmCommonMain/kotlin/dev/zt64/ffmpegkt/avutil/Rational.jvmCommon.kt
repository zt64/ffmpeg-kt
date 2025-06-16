package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.avutil.AVRational
import org.bytedeco.ffmpeg.global.avutil

public fun Rational(native: AVRational): Rational {
    return Rational(native.num(), native.den())
}

public fun Rational.toNative(): AVRational = avutil.av_make_q(num, den)