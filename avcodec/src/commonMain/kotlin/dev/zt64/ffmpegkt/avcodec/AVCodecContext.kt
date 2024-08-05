package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*

public expect class NativeAVCodecContext

public expect value class AVCodecContext(internal val native: NativeAVCodecContext) :
    AutoCloseable {
    public constructor(codec: AVCodec?)

    public var codecTag: Int
    public var codecType: AVMediaType
    public var codecId: AVCodecID
    public var bitRate: Long
    public var sampleRate: Int
    public var channelLayout: AVChannelLayout
    public var width: Int
    public var height: Int
    public var timeBase: AVRational
    public var framerate: AVRational
    public var gopSize: Int
    public var maxBFrames: Int
    public val frameSize: Int
    public var pixFmt: AVPixelFormat
    public var sampleFmt: AVSampleFormat
    public var threadCount: Int

    public fun open(codec: AVCodec, options: AVDictionary? = null)
    public fun sendPacket(packet: AVPacket)
    public fun receiveFrame(): AVFrame
    public fun sendFrame(frame: AVFrame?)
    public fun receivePacket(packet: AVPacket): AVPacket
    public fun flushBuffers()
    public fun isOpen(): Boolean
}