package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.asNative
import ffmpeg.av_packet_alloc
import ffmpeg.av_packet_rescale_ts
import ffmpeg.av_packet_unref
import kotlinx.cinterop.*

public actual typealias NativeAVPacket = ffmpeg.AVPacket

public actual value class Packet(public val native: NativeAVPacket) : AutoCloseable {
    public actual constructor() : this(av_packet_alloc()!!.pointed)

    public actual inline val pts: Long
        get() = native.pts

    public actual inline val dts: Long
        get() = native.dts

    public actual inline val data: ByteArray
        get() = native.data!!.readBytes(size)

    public actual inline val size: Int
        get() = native.size

    public actual inline var streamIndex: Int
        get() = native.stream_index
        set(value) {
            native.stream_index = value
        }

    public actual inline var flags: Int
        get() = native.flags
        set(value) {
            native.flags = value
        }

    public actual inline var duration: Long
        get() = native.duration
        set(value) {
            native.duration = value
        }

    public actual inline var pos: Long
        get() = native.pos
        set(value) {
            native.pos = value
        }
    public actual inline val timeBase: Rational
        get() = Rational(native.time_base)

    public actual fun rescaleTs(src: Rational, dst: Rational) {
        av_packet_rescale_ts(native.ptr, src.asNative().readValue(), dst.asNative().readValue())
    }

    public actual fun decode(): Frame {
        TODO("Decode using streams codec")
    }

    public actual override fun close() {
        av_packet_unref(native.ptr)
    }
}