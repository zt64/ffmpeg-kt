package dev.zt64.ffmpegkt.avutil

import ffmpeg.AVRational
import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap

public fun Rational(native: AVRational): Rational {
    return Rational(native.num, native.den)
}

public fun Rational.asNative(): AVRational {
    return nativeHeap.alloc<AVRational> {
        num = this@asNative.num
        den = this@asNative.den
    }
}