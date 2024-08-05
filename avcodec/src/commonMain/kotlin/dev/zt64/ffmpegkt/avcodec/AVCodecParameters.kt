package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVMediaType

public expect class AVCodecParameters {
    public val codecType: AVMediaType
    public val codecId: AVCodecID
    public var codecTag: Int

    public fun copyTo(target: AVCodecParameters)
}