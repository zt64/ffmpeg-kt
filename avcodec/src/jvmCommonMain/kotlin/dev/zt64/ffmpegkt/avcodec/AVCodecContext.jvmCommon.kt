package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVFrame
import dev.zt64.ffmpegkt.checkError
import dev.zt64.ffmpegkt.checkTrue
import org.bytedeco.ffmpeg.global.avcodec.*

public actual typealias AVCodecContext = org.bytedeco.ffmpeg.avcodec.AVCodecContext

public actual fun AVCodecContext.sendPacket(packet: AVPacket) {
    return avcodec_send_packet(this, packet).checkError()
}

public actual fun AVCodecContext.receiveFrame(): AVFrame {
    val frame = AVFrame()
    avcodec_receive_frame(this, frame).checkError()
    return frame
}

public actual fun AVCodecContext.sendFrame(frame: AVFrame) {
    return avcodec_send_frame(this, frame).checkError()
}

public actual fun AVCodecContext.receivePacket(): AVPacket {
    val packet = AVPacket()
    avcodec_receive_packet(this, packet).checkError()
    return packet
}

public actual fun AVCodecContext.flushBuffers() {
    return avcodec_flush_buffers(this)
}

public actual fun AVCodecContext.isOpen(): Boolean {
    return avcodec_is_open(this).checkTrue()
}