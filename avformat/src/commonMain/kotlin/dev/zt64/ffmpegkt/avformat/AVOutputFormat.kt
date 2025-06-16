package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.CodecID

internal expect class NativeAVOutputFormat

public expect value class AVOutputFormat internal constructor(internal val native: NativeAVOutputFormat) {
    public val name: String
    public val longName: String
    public val mimeType: String?
    public val extensions: String?
    public val audioCodec: CodecID
    public val videoCodec: CodecID
    public val subtitleCodec: CodecID
    public val flags: Int
    public val codecTag: List<AVCodecTag>
}

internal inline fun AVOutputFormat.commonToString(): String {
    return "AVOutputFormat(name='$name', longName='$longName', mimeType='$mimeType', extensions='$extensions', audioCodec=$audioCodec, videoCodec=$videoCodec, subtitleCodec=$subtitleCodec, flags=$flags, codecTag=$codecTag)"
}