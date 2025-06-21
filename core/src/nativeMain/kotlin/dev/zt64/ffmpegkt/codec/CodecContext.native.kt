package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import ffmpeg.*
import kotlinx.cinterop.*

public actual abstract class CodecContext protected constructor(
    internal val native: AVCodecContext,
    internal actual val codec: Codec
) : AutoCloseable {
    public actual constructor(codec: Codec) : this(allocContext(codec), codec)

    public actual var codecTag: Int
        get() = native.codec_tag.toInt()
        set(value) {
            native.codec_tag = value.toUInt()
        }
    public actual var codecType: MediaType
        get() = MediaType(native.codec_type)
        set(value) {
            native.codec_type = value.num
        }
    public actual var codecId: CodecID
        get() = CodecID(native.codec_id.toInt())
        set(value) {
            native.codec_id = value.num.toUInt()
        }
    public actual var bitRate: Long
        get() = native.bit_rate
        set(value) {
            native.bit_rate = value
        }
    public actual var bitRateTolerance: Int
        get() = native.bit_rate_tolerance
        set(value) {
            native.bit_rate_tolerance = value
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

    public actual val isOpen: Boolean
        get() = avcodec_is_open(native.ptr).checkTrue()

    public actual fun open(options: Dictionary?) {
        avcodec_open2(native.ptr, codec.native.ptr, null).checkError()
    }

    public actual fun flushBuffers() {
        avcodec_flush_buffers(native.ptr)
    }

    public actual fun sendFrame(frame: Frame?) {
        avcodec_send_frame(native.ptr, frame?.native?.ptr).checkError()
    }

    public actual fun sendPacket(packet: Packet?) {
        avcodec_send_packet(native.ptr, packet?.native?.ptr).checkError()
    }

    public actual fun receivePacket(): Packet? {
        val pkt = av_packet_alloc()!!
        return when (val ret = avcodec_receive_packet(native.ptr, pkt)) {
            ERROR_AGAIN, ERROR_EOF -> null

            else -> {
                ret.checkError()
                Packet(pkt.pointed)
            }
        }
    }

    public actual abstract fun decode(): Frame?

    actual override fun close() {
        avcodec_free_context(cValuesOf(native.ptr))
    }

    internal companion object {
        internal fun allocContext(codec: Codec?): AVCodecContext {
            return avcodec_alloc_context3(codec?.native?.ptr)!!.pointed
        }
    }
}

public actual sealed class AudioCodecContext protected actual constructor(codec: Codec) : CodecContext(codec) {
    public actual var sampleFmt: SampleFormat
        get() = SampleFormat(native.sample_fmt)
        set(value) {
            native.sample_fmt = value.num
        }
    public actual var sampleRate: Int
        get() = native.sample_rate
        set(value) {
            native.sample_rate = value
        }
    public actual var channelLayout: ChannelLayout
        get() = ChannelLayout(native.ch_layout)
        set(value) {
            av_channel_layout_copy(native.ch_layout.ptr, value.native.ptr)
        }
    public actual var frameSize: Int
        get() = native.frame_size
        set(value) {
            native.frame_size = value
        }

    public actual override fun decode(): AudioFrame? {
        val frame = AudioFrame()
        return when (val ret = avcodec_receive_frame(native.ptr, frame.native.ptr)) {
            ERROR_AGAIN, ERROR_EOF -> null

            else -> {
                ret.checkError()
                frame
            }
        }
    }
}

public actual sealed class VideoCodecContext protected actual constructor(codec: Codec) :
    CodecContext(codec) {

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

    public actual override fun decode(): VideoFrame? {
        val frame = VideoFrame()
        return when (val ret = avcodec_receive_frame(native.ptr, frame.native.ptr)) {
            ERROR_AGAIN, ERROR_EOF -> null

            else -> {
                ret.checkError()
                frame
            }
        }
    }
}