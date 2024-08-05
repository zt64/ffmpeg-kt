package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodecID
import dev.zt64.ffmpegkt.avutil.AVClass

public expect class AVOutputFormat {
    public val name: String
    public val longName: String
    public val mimeType: String
    public val extensions: String
    public val audioCodec: AVCodecID
    public val videoCodec: AVCodecID
    public val subtitleCodec: AVCodecID
    public val flags: Int
    public val codecTag: List<AVCodecTag>
    public val privClass: AVClass
}