@file:Suppress("NOTHING_TO_INLINE")

package dev.zt64.ffmpegkt.avutil.util

/**
 * Check if error code is less than 0 and throw [FfmpegException] if true
 */
public inline fun Int.checkError(): Int {
    return if (this < 0) throw FfmpegException(this) else this
}

public inline fun Int.checkTrue(): Boolean = this >= 0