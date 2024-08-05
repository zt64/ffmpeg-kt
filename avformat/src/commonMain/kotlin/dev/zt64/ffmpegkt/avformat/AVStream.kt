package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodecParameters
import dev.zt64.ffmpegkt.avutil.AVClass
import dev.zt64.ffmpegkt.avutil.AVRational

public expect class AVStream {
    public val avClass: AVClass
    public val index: Int
    public val id: Int
    public val codecParameters: AVCodecParameters
    public val timeBase: AVRational
}