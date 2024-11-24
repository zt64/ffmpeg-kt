package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.Rational

public expect class NativeAVChapter

/**
 * A chapter
 *
 * @property id a
 * @property start
 * @property end
 * @property timeBase
 * @property metadata
 */
public expect value class Chapter(internal val native: NativeAVChapter) {
    public val id: Int
    public val start: Long
    public val end: Long
    public val timeBase: Rational
    public val metadata: AVDictionary
}