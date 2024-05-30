package dev.zt64.ffmpegkt

/**
 * Check if error code is less than 0 and throw [FFmpegException] if true
 */
fun Int.checkError() {
    if (this < 0) throw FFmpegException(this)
}

fun Int.checkTrue(): Boolean = this >= 0