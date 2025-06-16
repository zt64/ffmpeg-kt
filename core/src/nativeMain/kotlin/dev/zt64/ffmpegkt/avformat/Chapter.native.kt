package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.avutil.Rational
import ffmpeg.AVChapter

public actual typealias NativeAVChapter = AVChapter

public actual value class Chapter(
    @PublishedApi
    internal val native: NativeAVChapter
) {
    public actual inline val id: Int
        get() = native.id.toInt()
    public actual inline val start: Long
        get() = native.start
    public actual inline val end: Long
        get() = native.end
    public actual inline val timeBase: Rational
        get() = Rational(native.time_base)
    public actual inline val metadata: Dictionary
        get() = TODO()
}