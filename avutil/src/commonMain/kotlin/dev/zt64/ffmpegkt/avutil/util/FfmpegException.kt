package dev.zt64.ffmpegkt.avutil.util

import dev.zt64.ffmpegkt.avutil.AVUtil

public class FfmpegException(public val code: Int) : Exception() {
    override val message: String = "$code: ${AVUtil.errorToString(code)}"
}