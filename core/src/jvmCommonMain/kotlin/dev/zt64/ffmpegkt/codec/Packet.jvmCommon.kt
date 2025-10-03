package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.toNative
import org.bytedeco.ffmpeg.global.avcodec.av_new_packet
import org.bytedeco.ffmpeg.global.avcodec.av_packet_alloc
import org.bytedeco.ffmpeg.global.avutil.av_rescale_q

internal actual typealias NativeAVPacket = org.bytedeco.ffmpeg.avcodec.AVPacket

internal actual fun rescale(value: Long, source: Rational, destination: Rational): Long {
    return av_rescale_q(value, source.toNative(), destination.toNative())
}

internal fun Packet(native: NativeAVPacket): Packet {
    val size = native.size()
    val byteArray = if (size > 0) {
        ByteArray(size).also { native.data().get(it) }
    } else {
        ByteArray(0)
    }
    return Packet(
        pts = native.pts(),
        dts = native.dts(),
        duration = native.duration(),
        streamIndex = native.stream_index(),
        size = size,
        data = byteArray
    )
}

internal fun Packet.toNative(): NativeAVPacket {
    val packet = av_packet_alloc()
    av_new_packet(packet, data.size)
    return packet.apply {
        if (this@toNative.data.isNotEmpty()) {
            data().put(data, 0, data.size)
        }
        pts(this@toNative.pts)
        dts(this@toNative.dts)
        duration(this@toNative.duration)
        stream_index(this@toNative.streamIndex)
    }
}