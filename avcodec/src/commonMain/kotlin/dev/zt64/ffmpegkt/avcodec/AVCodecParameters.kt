package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVMediaType

public data class AVCodecParameters(
    val codecType: AVMediaType,
    val codecId: AVCodecID,
    val codecTag: Int
)