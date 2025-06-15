package dev.zt64.ffmpegkt.avformat

internal actual typealias NativeAVInputFormat = org.bytedeco.ffmpeg.avformat.AVInputFormat

@JvmInline
public actual value class AVInputFormat(public val native: NativeAVInputFormat) {
    public actual inline val name: String
        get() = native.name().string
    public actual inline val longName: String
        get() = native.long_name().string
    public actual inline val mimeType: String?
        get() = native.mime_type()?.string
    public actual inline val extensions: String?
        get() = native.extensions()?.string
    public actual inline val flags: Int
        get() = native.flags()
    public actual inline val codecTag: List<AVCodecTag>
        get() = emptyList()

    override fun toString(): String = commonToString()
}