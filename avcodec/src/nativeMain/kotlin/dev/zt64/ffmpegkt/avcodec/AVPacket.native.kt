package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVRational
import ffmpeg.av_packet_alloc
import ffmpeg.av_packet_rescale_ts
import ffmpeg.av_packet_unref
import kotlinx.cinterop.*

public actual typealias NativeAVPacket = ffmpeg.AVPacket

public actual value class AVPacket(
    public val native: NativeAVPacket
) : AutoCloseable {
    public actual constructor() : this(av_packet_alloc()!!.pointed)

    public actual var streamIndex: Int
        get() = native.stream_index
        set(value) {
            native.stream_index = value
        }

    public actual var pos: Long
        get() = native.pos
        set(value) {
            native.pos = value
        }

    public actual val size: Int
        get() = native.size

    public actual val data: ByteArray
        get() = native.data!!.readBytes(size)

    public actual val pts: Long
        get() = native.pts

    public actual fun rescaleTs(src: AVRational, dst: AVRational) {
        av_packet_rescale_ts(native.ptr, src.readValue(), dst.readValue())
    }

    public override fun close() {
        av_packet_unref(native.ptr)
    }
}