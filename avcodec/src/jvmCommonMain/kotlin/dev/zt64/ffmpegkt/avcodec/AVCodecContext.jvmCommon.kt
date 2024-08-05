package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avutil.av_channel_layout_copy

private actual typealias NativeAVCodecContext = org.bytedeco.ffmpeg.avcodec.AVCodecContext

@Suppress("ACTUAL_WITHOUT_EXPECT")
@JvmInline
public actual value class AVCodecContext actual constructor(
    internal val native: NativeAVCodecContext
) : AutoCloseable {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native))

    public actual var codecTag: Int
        get() = native.codec_tag()
        set(value) {
            native.codec_tag(value)
        }
    public actual var codecType: AVMediaType
        get() = AVMediaType(native.codec_type())
        set(value) {
            native.codec_type(value.num)
        }
    public actual var codecId: AVCodecID
        get() = AVCodecID(native.codec_id())
        set(value) {
            native.codec_id(value.num)
        }
    public actual var bitRate: Long
        get() = native.bit_rate()
        set(value) {
            native.bit_rate(value)
        }
    public actual var sampleRate: Int
        get() = native.sample_rate()
        set(value) {
            native.sample_rate(value)
        }

    public actual var channelLayout: AVChannelLayout
        get() = AVChannelLayout(native.ch_layout())
        set(value) {
            av_channel_layout_copy(native.ch_layout(), value.native)
        }
    public actual var width: Int
        get() = native.width()
        set(value) {
            native.width(value)
        }
    public actual var height: Int
        get() = native.height()
        set(value) {
            native.height(value)
        }
    public actual var timeBase: AVRational
        get() = AVRational(native.time_base())
        set(value) {
            native.time_base(NativeAVRational(value))
        }
    public actual var framerate: AVRational
        get() = AVRational(native.framerate())
        set(value) {
            native.framerate(NativeAVRational(value))
        }
    public actual var gopSize: Int
        get() = native.gop_size()
        set(value) {
            native.gop_size(value)
        }
    public actual var maxBFrames: Int
        get() = native.max_b_frames()
        set(value) {
            native.max_b_frames(value)
        }
    public actual val frameSize: Int
        get() = native.frame_size()
    public actual var pixFmt: AVPixelFormat
        get() = AVPixelFormat(native.pix_fmt())
        set(value) {
            native.pix_fmt(value.num)
        }
    public actual var sampleFmt: AVSampleFormat
        get() = AVSampleFormat(native.sample_fmt())
        set(value) {
            native.sample_fmt(value.num)
        }
    public actual var threadCount: Int
        get() = native.thread_count()
        set(value) {
            native.thread_count(value)
        }

    public actual fun open(codec: AVCodec, options: AVDictionary?) {
        avcodec_open2(native, codec.native, options?.let(::AVDictionaryNative)).checkError()
    }

    public actual fun sendPacket(packet: AVPacket) {
        avcodec_send_packet(native, packet.native).checkError()
    }

    public actual fun receiveFrame(): AVFrame {
        val frame = NativeAVFrame()
        avcodec_receive_frame(native, frame).checkError()
        return AVFrame(frame)
    }

    public actual fun sendFrame(frame: AVFrame?) {
        avcodec_send_frame(native, frame?.native).checkError()
    }

    public actual fun receivePacket(packet: AVPacket): AVPacket {
        avcodec_receive_packet(native, packet.native).checkError()
        return packet
    }

    public actual fun flushBuffers() {
        avcodec_flush_buffers(native)
    }

    public actual fun isOpen(): Boolean {
        return avcodec_is_open(native).checkTrue()
    }

    override fun close() {
        avcodec_free_context(native)
    }
}