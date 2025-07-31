package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.asNative
import ffmpeg.*
import kotlinx.cinterop.*
import platform.posix.memcpy

public actual typealias NativeAVPacket = AVPacket

public actual fun rescale(
    value: Long,
    source: Rational,
    destination: Rational
): Long {
    return av_rescale_q(value, source.asNative().readValue(), destination.asNative().readValue())
}

internal fun Packet(native: NativeAVPacket): Packet {
    val size = native.size
    val byteArray = ByteArray(size)
    if (size > 0) {
        memcpy(byteArray.refTo(0), native.data!!, size.toULong())
    }
    return Packet(
        pts = native.pts,
        dts = native.dts,
        duration = native.duration,
        streamIndex = native.stream_index,
        size = size,
        data = byteArray
    )
}

internal fun Packet.toNative(): NativeAVPacket {
    val packet = av_packet_alloc()!!.pointed
    if (data.isNotEmpty()) {
        // Create a packet that references the data, then make it writable to copy the data.
        av_packet_from_data(packet.ptr, data.asUByteArray().refTo(0), data.size)
        av_packet_make_writable(packet.ptr)
    }
    return packet.apply {
        pts = this@toNative.pts
        dts = this@toNative.dts
        duration = this@toNative.duration
        stream_index = this@toNative.streamIndex
    }
}