package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*

internal const val ERROR_EOF = -541478725
internal const val ERROR_AGAIN = -11

/**
 * Base class for all encoders and decoders. Contains common properties and methods.
 */
public expect sealed class CodecContext : AutoCloseable {
    internal val codec: AVCodec

    public var codecTag: Int
    public var codecType: MediaType
    public var codecId: AVCodecID
    public var bitRate: Long

    public var flags: Int
    public var timeBase: Rational

    /**
     * Thread count is used to decide how many independent tasks should be passed to execute()
     * - encoding: Set by user.
     * - decoding: Set by user.
     */
    public var threadCount: Int

    /**
     * Frame counter
     */
    public var frameNum: Long

    /**
     * Open the codec context with the given codec and options. You **must** call this method before using the codec context.
     * After opening the codec context, you can start sending packets to the codec context and receive frames from it.
     *
     * @param codec The codec to open the context with.
     * @param options
     */
    public fun open(options: AVDictionary? = null)

    /**
     * Flush the buffers of the codec context.
     */
    public fun flushBuffers()

    /**
     * Indicates whether the codec context is open.
     *
     * @return `true` if the codec context is open, `false` otherwise.
     */
    public fun isOpen(): Boolean

    protected fun sendFrame(frame: Frame?)

    override fun close()
}

/**
 * Base class for audio encoders and decoders.
 */
public expect sealed class AudioCodecContext : CodecContext {
    public var sampleFmt: SampleFormat
    public var sampleRate: Int
    public var channelLayout: ChannelLayout
    public var frameSize: Int
}

/**
 * Base class for video encoders and decoders.
 */
public expect sealed class VideoCodecContext : CodecContext {
    public var pixFmt: PixelFormat
    public var width: Int
    public var height: Int
    public var gopSize: Int
    public var maxBFrames: Int
    public var mbDecision: Int
    public var framerate: Rational
}

public interface Encoder {
    /**
     * Receive a new encoded packet of data
     * @return the packet or null if no more data
     */
    public fun encode(): Packet?
}

public interface Decoder {
    public fun decode(packet: Packet?)
}

/**
 * An audio encoder.
 *
 * @constructor Create a new audio encoder with the given codec.
 *
 * @param codec
 */
public expect class AudioEncoder(codec: AVCodec) :
    AudioCodecContext,
    Encoder {
    public fun encode(frame: AudioFrame?)
}

/**
 * An audio decoder.
 *
 * @constructor Create a new audio decoder with the given codec.
 *
 * @param codec
 */
public expect class AudioDecoder(codec: AVCodec) :
    AudioCodecContext,
    Decoder {
    public fun decode(): AudioFrame
}

/**
 * A video encoder.
 *
 * @constructor Create a new video encoder with the given codec.
 *
 * @param codec
 */
public expect class VideoEncoder(codec: AVCodec) :
    VideoCodecContext,
    Encoder {
    public fun encode(frame: VideoFrame?): List<Packet>
}

/**
 * A video decoder.
 *
 * @constructor Create a new video decoder with the given codec.
 *
 * @param codec
 */
public expect class VideoDecoder(codec: AVCodec) :
    VideoCodecContext,
    Decoder {
    /**
     * Receive a new decoded frame of video data.
     *
     * NOTE: This frame should not be modified or closed by the user.
     */
    public fun decode(): VideoFrame?
}