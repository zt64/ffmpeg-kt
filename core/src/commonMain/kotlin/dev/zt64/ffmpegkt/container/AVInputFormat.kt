package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avformat.AVCodecTag

internal expect class NativeAVInputFormat

public expect value class AVInputFormat internal constructor(
    @PublishedApi
    internal val native: NativeAVInputFormat
) {
    public val name: String
    public val longName: String
    public val flags: Int
    public val extensions: String?
    public val mimeType: String?
    public val codecTag: List<AVCodecTag>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun AVInputFormat.commonToString(): String {
    return "AVInputFormat(name='$name', longName='$longName', flags=$flags, extensions='$extensions', mimeType='$mimeType', " +
        "codecTag=$codecTag)"
}