package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.AVChannelLayout
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.AVMediaType
import dev.zt64.ffmpegkt.avutil.AVPixelFormat
import dev.zt64.ffmpegkt.avutil.AVRational
import dev.zt64.ffmpegkt.avutil.AVSampleFormat
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import ffmpeg.*
import kotlinx.cinterop.*

public actual abstract class CodecContext(
    @PublishedApi
    internal val native: NativeCodecContext
) : AutoCloseable {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native?.ptr)!!.pointed)

    public actual var codecTag: Int
        get() = native.codec_tag.toInt()
        set(value) {
            native.codec_tag = value.toUInt()
        }
    public actual var codecType: AVMediaType
        get() = AVMediaType(native.codec_type)
        set(value) {
            native.codec_type = value.num
        }
    public actual var codecId: AVCodecID
        get() = AVCodecID(native.codec_id.toInt())
        set(value) {
            native.codec_id = value.num.toUInt()
        }
    public actual var bitRate: Long
        get() = native.bit_rate
        set(value) {
            native.bit_rate = value
        }
    public actual var flags: Int
        get() = native.flags
        set(value) {
            native.flags = value
        }
    public actual var timeBase: AVRational
        get() = native.time_base
        set(value) {
            value.readValue().place(native.time_base.ptr)
        }
    public actual var framerate: AVRational
        get() = native.framerate
        set(value) {
            value.readValue().place(native.framerate.ptr)
        }
    public actual var gopSize: Int
        get() = native.gop_size
        set(value) {
            native.gop_size = value
        }
    public actual var maxBFrames: Int
        get() = native.max_b_frames
        set(value) {
            native.max_b_frames = value
        }
    public actual var mbDecision: Int
        get() = native.mb_decision
        set(value) {
            native.mb_decision = value
        }
    public actual inline val frameSize: Int
        get() = native.frame_size
    public actual var pixFmt: AVPixelFormat
        get() = AVPixelFormat(native.pix_fmt)
        set(value) {
            native.pix_fmt = value.num
        }
    public actual var sampleFmt: AVSampleFormat
        get() = AVSampleFormat(native.sample_fmt)
        set(value) {
            native.sample_fmt = value.num
        }
    public actual var threadCount: Int
        get() = native.thread_count
        set(value) {
            native.thread_count = value
        }

    private val packet: AVPacket = AVPacket()

    public actual fun open(codec: AVCodec, options: AVDictionary?) {
        avcodec_open2(native.ptr, codec.native.ptr, null).checkError()
    }

    public actual fun sendPacket(packet: AVPacket) {
        avcodec_send_packet(native.ptr, packet.native.ptr).checkError()
    }

    public actual fun receivePacket(): AVPacket {
        avcodec_receive_packet(native.ptr, packet.native.ptr).checkError()
        return packet
    }

    public actual abstract fun receiveFrame(): Frame

    public actual fun flushBuffers() {
        avcodec_flush_buffers(native.ptr)
    }

    public actual fun isOpen(): Boolean {
        return avcodec_is_open(native.ptr).checkTrue()
    }

    actual override fun close() {
        avcodec_free_context(cValuesOf(native.ptr))
    }
}

public actual class AudioCodecContext(
    native: NativeCodecContext
) : CodecContext(native) {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native?.ptr)!!.pointed)

    public actual var sampleRate: Int
        get() = native.sample_rate
        set(value) {
            native.sample_rate = value
        }
    public actual var channelLayout: AVChannelLayout
        get() = AVChannelLayout(native.ch_layout)
        set(value) {
            av_channel_layout_copy(native.ch_layout.ptr, value.native.ptr)
        }

    public actual override fun receiveFrame(): AudioFrame {
        val frame = AudioFrame()
        avcodec_receive_frame(native.ptr, frame.native.ptr).checkError()
        return frame
    }

    public actual fun sendFrame(frame: AudioFrame?) {
        avcodec_send_frame(native.ptr, frame?.native?.ptr).checkError()
    }
}

public actual class VideoCodecContext(
    native: NativeCodecContext
) : CodecContext(native) {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native?.ptr)!!.pointed)

    public actual var width: Int
        get() = native.width
        set(value) {
            native.width = value
        }
    public actual var height: Int
        get() = native.height
        set(value) {
            native.height = value
        }

    public actual override fun receiveFrame(): VideoFrame {
        val frame = VideoFrame()
        avcodec_receive_frame(native.ptr, frame.native.ptr).checkError()
        return frame
    }

    public actual fun sendFrame(frame: VideoFrame?) {
        avcodec_send_frame(native.ptr, frame?.native?.ptr).checkError()
    }
}