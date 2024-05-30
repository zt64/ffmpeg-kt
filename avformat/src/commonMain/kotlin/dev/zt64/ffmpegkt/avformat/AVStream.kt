package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVClass

public data class AVStream(
    val avClass: AVClass,
    val index: Int,
    val id: Int
)