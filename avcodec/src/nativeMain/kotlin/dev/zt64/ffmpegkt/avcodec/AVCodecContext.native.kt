package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVFrame
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import ffmpeg.*
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr

public actual typealias AVCodecContext = ffmpeg.AVCodecContext

public actual fun AVCodecContext.sendPacket(packet: AVPacket) {
    avcodec_send_packet(ptr, packet.ptr).checkError()
}

public actual fun AVCodecContext.receiveFrame(): AVFrame {
    return memScoped {
        val frame = alloc<AVFrame>()

        avcodec_receive_frame(ptr, frame.ptr).checkError()

        frame
    }
}

public actual fun AVCodecContext.sendFrame(frame: AVFrame) {
    avcodec_send_frame(ptr, frame.ptr).checkError()
}

public actual fun AVCodecContext.receivePacket(): AVPacket {
    return memScoped {
        val packet = alloc<AVPacket>()

        avcodec_receive_packet(ptr, packet.ptr).checkError()

        packet
    }
}

public actual fun AVCodecContext.flushBuffers() {
    return avcodec_flush_buffers(ptr)
}

public actual fun AVCodecContext.isOpen(): Boolean {
    return avcodec_is_open(ptr).checkTrue()
}