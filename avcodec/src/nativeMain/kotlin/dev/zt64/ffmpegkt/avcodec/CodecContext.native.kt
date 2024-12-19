package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.AVChannelLayout
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.AVMediaType
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import ffmpeg.*
import kotlinx.cinterop.*

public actual abstract class CodecContext(internal val native: AVCodecContext) : AutoCloseable {
    public constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native?.ptr)!!.pointed)

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
    public actual var timeBase: Rational
        get() = Rational(native.time_base)
        set(value) {
            value.asNative().readValue().place(native.time_base.ptr)
        }

    public actual var threadCount: Int
        get() = native.thread_count
        set(value) {
            native.thread_count = value
        }

    public actual var frameNum: Long
        get() = native.frame_num
        set(value) {
            native.frame_num = value
        }

    public actual fun open(codec: AVCodec, options: AVDictionary?) {
        avcodec_open2(native.ptr, codec.native.ptr, null).checkError()
    }

    protected actual open fun sendFrame(frame: Frame?) {
        avcodec_send_frame(native.ptr, frame?.native?.ptr).checkError()
    }

    public actual fun flushBuffers() {
        avcodec_flush_buffers(native.ptr)
    }

    public actual fun isOpen(): Boolean {
        return avcodec_is_open(native.ptr).checkTrue()
    }

    actual override fun close() {
        avcodec_free_context(cValuesOf(native.ptr))
    }

    internal companion object {
        internal fun allocContext(codec: AVCodec?): AVCodecContext {
            return avcodec_alloc_context3(codec?.native?.ptr)!!.pointed
        }
    }
}

public actual abstract class AudioCodecContext(native: AVCodecContext) : CodecContext(native) {
    public actual var sampleFmt: SampleFormat
        get() = SampleFormat(native.sample_fmt)
        set(value) {}
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
    public actual var frameSize: Int
        get() = native.frame_size
        set(value) {
            native.frame_size = value
        }
}

public actual abstract class VideoCodecContext(native: AVCodecContext) : CodecContext(native) {
    public constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native?.ptr)!!.pointed)

    public actual var pixFmt: PixelFormat
        get() = PixelFormat(native.pix_fmt)
        set(value) {
            native.pix_fmt = value.num
        }
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
    public actual var framerate: Rational
        get() = Rational(native.framerate)
        set(value) {
            value.asNative().readValue().place(native.framerate.ptr)
        }
}

public actual class AudioEncoder(native: AVCodecContext) :
    AudioCodecContext(native),
    Encoder {
    public actual constructor(codec: AVCodec?) : this(allocContext(codec))

    private val packet = Packet()

    public actual fun sendFrame(frame: AudioFrame?) {
        super.sendFrame(frame)
    }

    override fun receivePacket(): Packet? {
        val ret = avcodec_receive_packet(native.ptr, packet.native.ptr)

        return if (ret == ERROR_AGAIN || ret == ERROR_EOF) {
            null
        } else {
            ret.checkError()
            packet
        }
    }
}

public actual class AudioDecoder(native: AVCodecContext) :
    AudioCodecContext(native),
    Decoder {
    public actual constructor(codec: AVCodec?) : this(allocContext(codec))

    override fun sendPacket(packet: Packet?) {
        avcodec_send_packet(native.ptr, packet?.native?.ptr).checkError()
    }

    public actual fun receiveFrame(): AudioFrame {
        val frame = AudioFrame()
        avcodec_receive_frame(native.ptr, frame.native.ptr).checkError()
        return frame
    }
}

public actual class VideoEncoder(native: AVCodecContext) :
    VideoCodecContext(native),
    Encoder {
    public actual constructor(codec: AVCodec?) : this(allocContext(codec))

    private val packet = Packet()

    public actual fun sendFrame(frame: VideoFrame?) {
        avcodec_send_frame(native.ptr, frame?.native?.ptr).checkError()
    }

    override fun receivePacket(): Packet? {
        val ret = avcodec_receive_packet(native.ptr, packet.native.ptr)

        return if (ret == ERROR_AGAIN || ret == ERROR_EOF) {
            null
        } else {
            ret.checkError()
            packet
        }
    }
}

public actual class VideoDecoder(native: AVCodecContext) :
    VideoCodecContext(native),
    Decoder {
    public actual constructor(codec: AVCodec?) : this(allocContext(codec))

    private val frame = VideoFrame()

    override fun sendPacket(packet: Packet?) {
        avcodec_send_packet(native.ptr, packet?.native?.ptr).checkError()
    }

    public actual fun receiveFrame(): VideoFrame? {
        val ret = avcodec_receive_frame(native.ptr, frame.native.ptr)

        return if (ret == ERROR_AGAIN || ret == ERROR_EOF) {
            null
        } else {
            ret.checkError()
            frame
        }
    }
}