package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.Rational
import ffmpeg.AVChapter

public actual typealias NativeAVChapter = AVChapter

public actual value class Chapter(
    @PublishedApi
    internal val native: NativeAVChapter
) {
    public actual inline val id: Int
        get() = native.id.toInt()
    public actual val start: Long
        get() = native.start
    public actual val end: Long
        get() = native.end
    public actual val timeBase: Rational
        get() = Rational(native.time_base)
    public actual val metadata: AVDictionary
        get() = TODO()
}