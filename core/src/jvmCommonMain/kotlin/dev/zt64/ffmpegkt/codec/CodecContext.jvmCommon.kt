package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avutil.av_channel_layout_copy

private typealias NativeCodecContext = org.bytedeco.ffmpeg.avcodec.AVCodecContext

public actual open class CodecContext protected constructor(
    public val native: NativeCodecContext,
    internal actual val codec: Codec
) : AutoCloseable {
    protected val packet: Packet = Packet() // Shared packet for encoders

    public actual constructor(codec: Codec) : this(avcodec_alloc_context3(codec.native), codec)

    public actual inline var codecTag: Int
        get() = native.codec_tag()
        set(value) {
            native.codec_tag(value)
        }

    public actual inline var codecType: MediaType
        get() = MediaType(native.codec_type())
        set(value) {
            native.codec_type(value.num)
        }

    public actual inline var codecId: CodecID
        get() = CodecID(native.codec_id())
        set(value) {
            native.codec_id(value.num)
        }

    public actual inline var bitRate: Long
        get() = native.bit_rate()
        set(value) {
            native.bit_rate(value)
        }

    public actual inline var bitRateTolerance: Int
        get() = native.bit_rate_tolerance()
        set(value) {
            native.bit_rate_tolerance(value)
        }

    public actual inline var flags: Int
        get() = native.flags()
        set(value) {
            native.flags(value)
        }

    public actual inline var timeBase: Rational
        get() = Rational(native.time_base())
        set(value) {
            native.time_base(value.toNative())
        }

    public actual inline var threadCount: Int
        get() = native.thread_count()
        set(value) {
            native.thread_count(value)
        }

    public actual inline var frameNum: Long
        get() = native.frame_num()
        set(value) {
            native.frame_num(value)
        }

    public actual inline val isOpen: Boolean
        get() = avcodec_is_open(native).checkTrue()

    public actual fun open(options: Dictionary?) {
        avcodec_open2(native, codec.native, options?.toNative()).checkError()
    }

    public actual fun flushBuffers() {
        avcodec_flush_buffers(native)
    }

    protected actual fun sendFrame(frame: Frame?) {
        avcodec_send_frame(native, frame?.native).checkError()
    }

    protected fun receivePacket(): Packet? {
        val pkt = av_packet_alloc()
        return when (val ret = avcodec_receive_packet(native, pkt)) {
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

    public actual override fun close() {
        avcodec_free_context(native)
    }
}

public actual sealed class AudioCodecContext protected constructor(codec: Codec) :
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

public actual sealed class VideoCodecContext protected constructor(codec: Codec) :
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

public actual class AudioEncoder actual constructor(codec: Codec) : AudioCodecContext(codec), Encoder {
    public actual constructor(
        codec: Codec,
        bitRate: Long,
        sampleFmt: SampleFormat,
        sampleRate: Int,
        channelLayout: ChannelLayout
    ) : this(codec) {
        this.bitRate = bitRate
        this.sampleFmt = sampleFmt
        this.sampleRate = sampleRate
        this.channelLayout = channelLayout
    }

    public actual fun encode(frame: AudioFrame?): List<Packet> {
        super.sendFrame(frame)

        return buildList {
            while (true) {
                add(receivePacket() ?: break)
            }
        }
    }

    public actual override fun encode(): Packet? = receivePacket()
}

public actual class AudioDecoder actual constructor(codec: Codec) : AudioCodecContext(codec), Decoder {
    public actual override fun decode(packet: Packet?) {
        sendPacket(packet)
    }

    private val frame = AudioFrame()
    public actual fun decode(): AudioFrame? {
        return when (val ret = avcodec_receive_frame(native, frame.native)) {
            ERROR_AGAIN, ERROR_EOF -> null

            else -> {
                ret.checkError()
                frame
            }
        }
    }
}

public actual class VideoEncoder actual constructor(codec: Codec) : VideoCodecContext(codec), Encoder {
    public actual constructor(
        codec: Codec,
        bitRate: Long,
        width: Int,
        height: Int,
        timeBase: Rational,
        framerate: Rational,
        gopSize: Int,
        maxBFrames: Int,
        pixFmt: PixelFormat
    ) : this(codec) {
        this.bitRate = bitRate
        this.width = width
        this.height = height
        this.timeBase = timeBase
        this.framerate = framerate
        this.gopSize = gopSize
        this.maxBFrames = maxBFrames
        this.pixFmt = pixFmt
    }

    public actual constructor(
        codec: Codec,
        bitRate: Long,
        width: Int,
        height: Int
    ) : this(codec) {
        this.bitRate = bitRate
        this.width = width
        this.height = height
    }

    public actual fun encode(frame: VideoFrame?): List<Packet> {
        super.sendFrame(frame)

        return buildList {
            while (true) {
                add(receivePacket() ?: break)
            }
        }
    }

    public actual override fun encode(): Packet? = receivePacket()
}

public actual class VideoDecoder actual constructor(codec: Codec) : VideoCodecContext(codec), Decoder {
    private val frame = VideoFrame()

    public actual override fun decode(packet: Packet?) {
        sendPacket(packet)
    }

    public actual fun decode(): VideoFrame? {
        return when (val ret = avcodec_receive_frame(native, frame.native)) {
            ERROR_AGAIN, ERROR_EOF -> null

            else -> {
                ret.checkError()
                frame
            }
        }
    }
}