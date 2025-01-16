package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avutil.av_channel_layout_copy

private typealias NativeCodecContext = org.bytedeco.ffmpeg.avcodec.AVCodecContext

public actual sealed class CodecContext protected constructor(internal val native: NativeCodecContext, internal actual val codec: AVCodec) :
    AutoCloseable {
    protected val packet: Packet = Packet() // Shared packet for encoders

    public actual var codecTag: Int
        get() = native.codec_tag()
        set(value) {
            native.codec_tag(value)
        }

    public actual var codecType: MediaType
        get() = MediaType(native.codec_type())
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

    public actual var flags: Int
        get() = native.flags()
        set(value) {
            native.flags(value)
        }

    public actual var timeBase: Rational
        get() = Rational(native.time_base())
        set(value) {
            native.time_base(value.toNative())
        }

    public actual var threadCount: Int
        get() = native.thread_count()
        set(value) {
            native.thread_count(value)
        }

    public actual var frameNum: Long
        get() = native.frame_num()
        set(value) {
            native.frame_num(value)
        }

    public actual fun open(options: AVDictionary?) {
        avcodec_open2(native, codec.native, options?.toNative()).checkError()
    }

    public actual fun flushBuffers() {
        avcodec_flush_buffers(native)
    }

    public actual fun isOpen(): Boolean = avcodec_is_open(native).checkTrue()

    protected actual fun sendFrame(frame: Frame?) {
        avcodec_send_frame(native, frame?.native).checkError()
    }

    protected fun receivePacket(): Packet? {
        val pkt = av_packet_alloc()
        val ret = avcodec_receive_packet(native, pkt)
        return when (ret) {
            ERROR_AGAIN, ERROR_EOF -> null

            else -> {
                ret.checkError()
                Packet(pkt)
            }
        }
    }

    protected fun sendPacket(packet: Packet?) {
        avcodec_send_packet(native, packet?.native).checkError()
    }

    actual override fun close() {
        avcodec_free_context(native)
    }
}

public actual sealed class AudioCodecContext protected constructor(codec: AVCodec) :
    CodecContext(avcodec_alloc_context3(codec.native), codec) {

    public actual var sampleFmt: SampleFormat
        get() = SampleFormat(native.sample_fmt())
        set(value) {
            native.sample_fmt(value.num)
        }

    public actual var sampleRate: Int
        get() = native.sample_rate()
        set(value) {
            native.sample_rate(value)
        }

    public actual var channelLayout: ChannelLayout
        get() = ChannelLayout(native.ch_layout())
        set(value) {
            av_channel_layout_copy(native.ch_layout(), value.native)
        }

    public actual var frameSize: Int
        get() = native.frame_size()
        set(value) {
            native.frame_size(value)
        }
}

public actual sealed class VideoCodecContext protected constructor(codec: AVCodec) :
    CodecContext(avcodec_alloc_context3(codec.native), codec) {

    public actual var pixFmt: PixelFormat
        get() = PixelFormat(native.pix_fmt())
        set(value) {
            native.pix_fmt(value.num)
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

    public actual var mbDecision: Int
        get() = native.mb_decision()
        set(value) {
            native.mb_decision(value)
        }

    public actual var framerate: Rational
        get() = Rational(native.framerate())
        set(value) {
            native.framerate(value.toNative())
        }
}

public actual class AudioEncoder actual constructor(codec: AVCodec) :
    AudioCodecContext(codec),
    Encoder {
    public actual fun encode(frame: AudioFrame?) {
        super.sendFrame(frame)
    }

    override fun encode(): Packet? = receivePacket()
}

public actual class AudioDecoder actual constructor(codec: AVCodec) :
    AudioCodecContext(codec),
    Decoder {
    override fun decode(packet: Packet?) {
        sendPacket(packet)
    }

    public actual fun decode(): AudioFrame {
        val frame = AudioFrame()
        avcodec_receive_frame(native, frame.native).checkError()
        return frame
    }
}

public actual class VideoEncoder actual constructor(codec: AVCodec) :
    VideoCodecContext(codec),
    Encoder {
    public actual fun encode(frame: VideoFrame?): List<Packet> {
        super.sendFrame(frame)

        return buildList {
            while (true) {
                add(receivePacket() ?: break)
            }
        }
    }

    override fun encode(): Packet? = receivePacket()
}

public actual class VideoDecoder actual constructor(codec: AVCodec) :
    VideoCodecContext(codec),
    Decoder {
    private val frame = VideoFrame()

    override fun decode(packet: Packet?) {
        sendPacket(packet)
    }

    public actual fun decode(): VideoFrame? {
        val ret = avcodec_receive_frame(native, frame.native)
        return when (ret) {
            ERROR_AGAIN, ERROR_EOF -> null

            else -> {
                ret.checkError()
                frame
            }
        }
    }
}