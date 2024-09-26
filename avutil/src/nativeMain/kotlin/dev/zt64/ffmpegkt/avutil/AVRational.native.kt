@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER") // shhh compiler

package dev.zt64.ffmpegkt.avutil

import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap

public actual typealias AVRational = ffmpeg.AVRational

public actual inline val AVRational.num: Int
    get() = num

public actual inline val AVRational.den: Int
    get() = den

public actual fun AVRational(num: Int, den: Int): AVRational {
    return nativeHeap.alloc<AVRational> {
        this.num = num
        this.den = den
    }
}