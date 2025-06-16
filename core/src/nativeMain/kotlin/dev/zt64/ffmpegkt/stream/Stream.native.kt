package dev.zt64.ffmpegkt.stream

import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.codec.AudioCodecParameters
import dev.zt64.ffmpegkt.codec.CodecParameters
import dev.zt64.ffmpegkt.codec.VideoCodecParameters
import ffmpeg.AVStream
import ffmpeg.avcodec_parameters_copy
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public actual open class Stream(public val native: AVStream) {
    public actual inline val index: Int
        get() = native.index
    public actual inline val id: Int
        get() = native.id
    public actual inline val timeBase: Rational
        get() = Rational(native.time_base)
    public actual val startTime: Long
        get() = native.start_time
    public actual val duration: Long
        get() = native.duration
    public actual val frames: Long
        get() = native.nb_frames
    public actual open val codecParameters: CodecParameters
        get() = CodecParameters(native.codecpar!!.pointed)

    override fun toString(): String = commonToString()
}

public actual class AudioStream(native: AVStream) : Stream(native) {
    public actual override var codecParameters: AudioCodecParameters
        get() = AudioCodecParameters(native.codecpar!!.pointed)
        set(value) {
            avcodec_parameters_copy(native.codecpar, value.native.ptr)
        }
}

public actual class VideoStream(native: AVStream) : Stream(native) {
    public actual override var codecParameters: VideoCodecParameters
        get() = VideoCodecParameters(native.codecpar!!.pointed)
        set(value) {
            avcodec_parameters_copy(native.codecpar, value.native.ptr)
        }
}