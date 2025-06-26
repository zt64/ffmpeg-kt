package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avformat.AVCodecTag
import ffmpeg.AVInputFormat
import kotlinx.cinterop.toKString

internal actual typealias NativeAVInputFormat = AVInputFormat

public actual value class AVInputFormat(
    @PublishedApi
    internal val native: NativeAVInputFormat
) {
    public actual inline val name: String
        get() = native.name!!.toKString()
    public actual inline val longName: String
        get() = native.long_name!!.toKString()
    public actual inline val mimeType: String?
        get() = native.mime_type?.toKString()
    public actual inline val extensions: String?
        get() = native.extensions?.toKString()
    public actual inline val flags: Int
        get() = native.flags
    public actual inline val codecTag: List<AVCodecTag>
        get() = emptyList()

    override fun toString(): String = commonToString()
}