package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.toNative
import org.bytedeco.ffmpeg.global.avcodec.*

public actual typealias NativeAVPacket = org.bytedeco.ffmpeg.avcodec.AVPacket

@JvmInline
public actual value class Packet(public val native: NativeAVPacket) : AutoCloseable {
    public actual constructor() : this(av_packet_alloc())

    public actual inline var streamIndex: Int
        get() = native.stream_index()
        set(value) {
            native.stream_index(value)
        }

    public actual inline var pos: Long
        get() = native.pos()
        set(value) {
            native.pos(value)
        }

    public actual inline val size: Int
        get() = native.size()

    public actual inline val data: ByteArray
        get() = List(native.size()) { native.data().position(it.toLong()).get() }.toByteArray()

    public actual inline val pts: Long
        get() = native.pts()

    public actual inline val dts: Long
        get() = native.dts()

    public actual fun rescaleTs(src: Rational, dst: Rational) {
        return av_packet_rescale_ts(native, src.toNative(), dst.toNative())
    }

    override fun close() {
        av_packet_unref(native)
    }
}