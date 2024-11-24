package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avutil.av_channel_layout_copy

private typealias NativeCodecContext = org.bytedeco.ffmpeg.avcodec.AVCodecContext

public actual abstract class CodecContext(internal val native: NativeCodecContext) : AutoCloseable {
    public constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native))

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
    public actual var flags: Int
        get() = native.flags()
        set(value) {
            native.flags(value)
        }
    public actual var timeBase: Rational
        get() = native.time_base()
        set(value) {
            native.time_base(value)
        }
    public actual var threadCount: Int
        get() = native.thread_count()
        set(value) {
            native.thread_count(value)
        }

    public actual fun open(codec: AVCodec, options: AVDictionary?) {
        avcodec_open2(native, codec.native, options?.let(::AVDictionaryNative)).checkError()
    }

    public actual fun flushBuffers() {
        avcodec_flush_buffers(native)
    }

    public actual fun isOpen(): Boolean {
        return avcodec_is_open(native).checkTrue()
    }

    protected actual open fun sendFrame(frame: Frame?) {
        avcodec_send_frame(native, frame?.native).checkError()
    }

    actual override fun close() {
        avcodec_free_context(native)
    }
}

public actual abstract class AudioCodecContext(native: NativeCodecContext) : CodecContext(native) {
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
    public actual var channelLayout: AVChannelLayout
        get() = AVChannelLayout(native.ch_layout())
        set(value) {
            av_channel_layout_copy(native.ch_layout(), value.native)
        }
    public actual var frameSize: Int
        get() = native.frame_size()
        set(value) {
            native.frame_size(value)
        }
}

public actual abstract class VideoCodecContext(native: NativeCodecContext) : CodecContext(native) {
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
        get() = native.framerate()
        set(value) {
            native.framerate(value)
        }
}

public actual class AudioEncoder(native: NativeCodecContext) :
    AudioCodecContext(native),
    Encoder {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native))

    private val packet = AVPacket()

    public actual fun sendFrame(frame: AudioFrame?) {
        super.sendFrame(frame)
    }

    override fun receivePacket(): AVPacket? {
        val ret = avcodec_receive_packet(native, packet.native)

        return if (ret == ERROR_AGAIN || ret == ERROR_EOF) {
            null
        } else {
            ret.checkError()
            packet
        }
    }
}

public actual class AudioDecoder(native: NativeCodecContext) :
    AudioCodecContext(native),
    Decoder {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native))

    override fun sendPacket(packet: AVPacket?) {
        avcodec_send_packet(native, packet?.native).checkError()
    }

    public actual fun receiveFrame(): AudioFrame {
        val frame = AudioFrame()
        avcodec_receive_frame(native, frame.native).checkError()
        return frame
    }
}

public actual class VideoEncoder(native: NativeCodecContext) :
    VideoCodecContext(native),
    Encoder {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native))

    private val packet = AVPacket()

    public actual fun sendFrame(frame: VideoFrame?) {
        avcodec_send_frame(native, frame?.native).checkError()
    }

    override fun receivePacket(): AVPacket? {
        val ret = avcodec_receive_packet(native, packet.native)

        return if (ret == ERROR_AGAIN || ret == ERROR_EOF) {
            null
        } else {
            ret.checkError()
            packet
        }
    }
}

public actual class VideoDecoder(native: NativeCodecContext) :
    VideoCodecContext(native),
    Decoder {
    public actual constructor(codec: AVCodec?) : this(avcodec_alloc_context3(codec?.native))

    private val frame = VideoFrame()

    override fun sendPacket(packet: AVPacket?) {
        avcodec_send_packet(native, packet?.native).checkError()
    }

    public actual fun receiveFrame(): VideoFrame? {
        val ret = avcodec_receive_frame(native, frame.native)

        return if (ret == ERROR_AGAIN || ret == ERROR_EOF) {
            null
        } else {
            ret.checkError()
            frame
        }
    }
}