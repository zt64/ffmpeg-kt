package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*
import ffmpeg.AVCodecParameters

public actual open class CodecParameters(public val native: AVCodecParameters) {
    public actual inline val codecType: MediaType
        get() = MediaType(native.codec_type)
    public actual inline val codecId: CodecID
        get() = CodecID(native.codec_id.toInt())
    public actual var codecTag: Int
        get() = native.codec_tag.toInt()
        set(value) {
            native.codec_tag = value.toUInt()
        }
    public actual inline val bitRate: Long
        get() = native.bit_rate
    public actual inline val bitsPerCodedSample: Int
        get() = native.bits_per_coded_sample
    public actual inline val bitsPerRawSample: Int
        get() = native.bits_per_raw_sample
    public actual inline val profile: Int
        get() = native.profile
    public actual inline val level: Int
        get() = native.level

    override fun toString(): String = commonToString()
}

public actual class AudioCodecParameters(native: AVCodecParameters) : CodecParameters(native) {
    public actual inline val format: SampleFormat
        get() = SampleFormat(native.format)
    public actual inline val channelLayout: ChannelLayout
        get() = ChannelLayout(native.ch_layout)
    public actual inline val sampleRate: Int
        get() = native.sample_rate
    public actual inline val blockAlign: Int
        get() = native.block_align
    public actual inline val frameSize: Int
        get() = native.frame_size
    public actual inline val initialPadding: Int
        get() = native.initial_padding
    public actual inline val trailingPadding: Int
        get() = native.trailing_padding
    public actual inline val seekPreroll: Int
        get() = native.seek_preroll

    override fun toString(): String = commonToString()
}

public actual class VideoCodecParameters(native: AVCodecParameters) : CodecParameters(native) {
    public actual inline val format: PixelFormat
        get() = PixelFormat(native.format)
    public actual inline val width: Int
        get() = native.width
    public actual inline val height: Int
        get() = native.height
    public actual inline val aspectRatio: Rational
        get() = Rational(native.sample_aspect_ratio)
    public actual inline val framerate: Rational
        get() = Rational(native.framerate)
    public actual val fieldOrder: FieldOrder
        get() = FieldOrder.entries[native.field_order.ordinal]
    public actual val colorRange: ColorRange
        get() = ColorRange.entries[native.color_range.toInt()]
    public actual val colorPrimaries: ColorPrimaries
        get() = ColorPrimaries.entries[native.color_primaries.toInt()]
    public actual val videoDelay: Int
        get() = native.video_delay

    override fun toString(): String = commonToString()
}