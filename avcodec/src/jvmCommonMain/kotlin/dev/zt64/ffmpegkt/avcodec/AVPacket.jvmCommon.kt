package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVRational
import dev.zt64.ffmpegkt.avutil.NativeAVRational
import org.bytedeco.ffmpeg.global.avcodec.*

public actual typealias NativeAVPacket = org.bytedeco.ffmpeg.avcodec.AVPacket

@JvmInline
public actual value class AVPacket(public val native: NativeAVPacket) : AutoCloseable {
    public actual constructor() : this(av_packet_alloc())

    public actual var streamIndex: Int
        get() = native.stream_index()
        set(value) {
            native.stream_index(value)
        }

    public actual var pos: Long
        get() = native.pos()
        set(value) {
            native.pos(value)
        }

    public actual val size: Int
        get() = native.size()

    public actual val data: ByteArray
        get() = List(native.size()) { native.data().position(it.toLong()).get() }.toByteArray()

    public actual val pts: Long
        get() = native.pts()

    public actual fun rescaleTs(src: AVRational, dst: AVRational) {
        return av_packet_rescale_ts(native, NativeAVRational(src), NativeAVRational(dst))
    }

    override fun close() {
        av_packet_unref(native)
    }
}