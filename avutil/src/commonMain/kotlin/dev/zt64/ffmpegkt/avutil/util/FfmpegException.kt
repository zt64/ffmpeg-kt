package dev.zt64.ffmpegkt.avutil.util

import dev.zt64.ffmpegkt.avutil.LibAVUtil

public class FfmpegException(public val code: Int) : Exception() {
    override val message: String = "$code: ${LibAVUtil.errorToString(code)}"
}