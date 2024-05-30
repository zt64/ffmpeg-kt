package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVFrame

public expect class AVCodecContext

public expect fun AVCodecContext.sendPacket(packet: AVPacket)

public expect fun AVCodecContext.receiveFrame(): AVFrame

public expect fun AVCodecContext.sendFrame(frame: AVFrame)

public expect fun AVCodecContext.receivePacket(): AVPacket

public expect fun AVCodecContext.flushBuffers()

public expect fun AVCodecContext.isOpen(): Boolean