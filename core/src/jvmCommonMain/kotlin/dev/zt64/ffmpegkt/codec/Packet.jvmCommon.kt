package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.toNative
import org.bytedeco.ffmpeg.global.avcodec.*

internal actual typealias NativeAVPacket = org.bytedeco.ffmpeg.avcodec.AVPacket

@JvmInline
public actual value class Packet(public val native: NativeAVPacket) : AutoCloseable {
    public actual constructor() : this(av_packet_alloc())

    public actual constructor(data: ByteArray) : this(
        av_packet_alloc().apply {
            av_packet_from_data(this, data, data.size)
        }
    )

    public actual inline val pts: Long
        get() = native.pts()

    public actual inline val dts: Long
        get() = native.dts()

    public actual inline val data: ByteArray
        get() = List(native.size()) { native.data().position(it.toLong()).get() }.toByteArray()

    public actual inline val size: Int
        get() = native.size()

    public actual inline var streamIndex: Int
        get() = native.stream_index()
        set(value) {
            native.stream_index(value)
        }

    public actual inline var flags: Int
        get() = native.flags()
        set(value) {
            native.flags(value)
        }

    public actual inline var duration: Long
        get() = native.duration()
        set(value) {
            native.duration(value)
        }

    public actual inline var pos: Long
        get() = native.pos()
        set(value) {
            native.pos(value)
        }

    public actual inline val timeBase: Rational
        get() = Rational(native.time_base())

    public actual fun rescaleTimestamp(src: Rational, dst: Rational) {
        av_packet_rescale_ts(native, src.toNative(), dst.toNative())
    }

    public actual fun decode(): Frame {
        TODO("Decode using streams codec")
    }

    actual override fun close() {
        av_packet_unref(native)
    }
}