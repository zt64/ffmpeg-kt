package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.AVRational

public data class AVChapter(
    val id: Int,
    val timeBase: AVRational,
    val start: Long,
    val end: Long,
    val metadata: AVDictionary
)