package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import org.bytedeco.ffmpeg.avcodec.AVCodecParameters

public actual open class CodecParameters(
    public val native: AVCodecParameters
) {
    public actual val codecType: AVMediaType
        get() = AVMediaType(native.codec_type())
    public actual val codecId: AVCodecID
        get() = AVCodecID(native.codec_id())
    public actual var codecTag: Int
        get() = native.codec_tag()
        set(value) {
            native.codec_tag(value)
        }
    public actual val bitRate: Long
        get() = native.bit_rate()
    public actual val bitsPerCodedSample: Int
        get() = native.bits_per_coded_sample()
    public actual val bitsPerRawSample: Int
        get() = native.bits_per_raw_sample()
    public actual val profile: Int
        get() = native.profile()
    public actual val level: Int
        get() = native.level()
}

public actual class AudioCodecParameters(
    native: AVCodecParameters
) : CodecParameters(native) {
    public actual val format: AVSampleFormat
        get() = AVSampleFormat(native.format())
    public actual val channelLayout: AVChannelLayout
        get() = AVChannelLayout(native.ch_layout())
    public actual val sampleRate: Int
        get() = native.sample_rate()
    public actual val blockAlign: Int
        get() = native.block_align()
    public actual val frameSize: Int
        get() = native.frame_size()
    public actual val initialPadding: Int
        get() = native.initial_padding()
    public actual val trailingPadding: Int
        get() = native.trailing_padding()
    public actual val seekPreroll: Int
        get() = native.seek_preroll()
}

public actual class VideoCodecParameters(
    native: AVCodecParameters
) : CodecParameters(native) {
    public actual val format: AVPixelFormat
        get() = AVPixelFormat(native.format())
    public actual val width: Int
        get() = native.width()
    public actual val height: Int
        get() = native.height()
    public actual val aspectRatio: AVRational
        get() = native.sample_aspect_ratio()
    public actual val framerate: AVRational
        get() = native.framerate()
    public actual val fieldOrder: FieldOrder
        get() = TODO("Not yet implemented")
    public actual val colorRange: ColorRange
        get() = TODO("Not yet implemented")
    public actual val colorPrimaries: ColorPrimaries
        get() = TODO("Not yet implemented")
    public actual val videoDelay: Int
        get() = native.video_delay()
}