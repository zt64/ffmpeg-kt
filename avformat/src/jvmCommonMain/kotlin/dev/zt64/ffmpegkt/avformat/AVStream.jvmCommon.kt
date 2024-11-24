package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AudioCodecParameters
import dev.zt64.ffmpegkt.avcodec.CodecParameters
import dev.zt64.ffmpegkt.avcodec.VideoCodecParameters
import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.Rational
import org.bytedeco.ffmpeg.avformat.AVStream
import org.bytedeco.ffmpeg.global.avcodec.avcodec_parameters_copy

public actual open class Stream(public val native: AVStream) {
    public actual inline val index: Int
        get() = native.index()
    public actual inline val id: Int
        get() = native.id()
    public actual inline val timeBase: Rational
        get() = native.time_base()
    public actual val startTime: Long
        get() = native.start_time()
    public actual val duration: Long
        get() = native.duration()
    public actual val frames: Long
        get() = native.nb_frames()
    public actual open val codecParameters: CodecParameters
        get() = CodecParameters(native.codecpar())

    override fun toString(): String {
        return "Stream(" +
                "native=$native, " +
                "index=$index, " +
                "id=$id, " +
                "timeBase=$timeBase, " +
                "startTime=$startTime, " +
                "duration=$duration, " +
                "frames=$frames, " +
                "codecParameters=$codecParameters" +
                ")"
    }

}

public actual class AudioStream(native: AVStream) : Stream(native) {
    public actual override var codecParameters: AudioCodecParameters
        get() = AudioCodecParameters(native.codecpar())
        set(value) {
            avcodec_parameters_copy(native.codecpar(), value.native)
        }
}

public actual class VideoStream(native: AVStream) : Stream(native) {
    public actual override var codecParameters: VideoCodecParameters
        get() = VideoCodecParameters(native.codecpar())
        set(value) {
            avcodec_parameters_copy(native.codecpar(), value.native)
        }
}