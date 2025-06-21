package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*

internal const val ERROR_EOF = -541478725
internal const val ERROR_AGAIN = -11

/**
 * Base class for all encoders and decoders. Contains common properties and methods.
 */
public expect open class CodecContext(codec: Codec) : AutoCloseable {
    internal val codec: Codec

    public var codecTag: Int
    public var codecType: MediaType
    public var codecId: CodecID
    public var bitRate: Long
    public var bitRateTolerance: Int

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
     * Indicates whether the codec context is open.
     *
     * @return `true` if the codec context is open, `false` otherwise.
     */
    public val isOpen: Boolean

    /**
     * Open the codec context with the given codec and options. You **must** call this method before using the codec context.
     * After opening the codec context, you can start sending packets to the codec context and receive frames from it.
     *
     * @param codec The codec to open the context with.
     * @param options
     */
    public fun open(options: Dictionary? = null)

    /**
     * Flush the buffers of the codec context.
     */
    public fun flushBuffers()

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
    /**
     * Decode a packet into a list of frames.
     *
     * @param packet The packet to decode.
     * @return A list of frames decoded from the packet. If the packet is null, it indicates the end of the stream.
     */
    public fun decode(packet: Packet?): List<Frame>
}

/**
 * An audio encoder.
 *
 * @constructor Create a new audio encoder with the given codec.
 *
 * @param codec
 */
public expect class AudioEncoder(codec: Codec) : AudioCodecContext, Encoder {
    public constructor(codec: Codec, bitRate: Long, sampleFmt: SampleFormat, sampleRate: Int, channelLayout: ChannelLayout)

    public fun encode(frame: AudioFrame?): List<Packet>
    public override fun encode(): Packet?
}

/**
 * An audio decoder.
 *
 * @constructor Create a new audio decoder with the given codec.
 *
 * @param codec
 */
public expect class AudioDecoder(codec: Codec) : AudioCodecContext, Decoder {
    public fun decode(): AudioFrame?
    public override fun decode(packet: Packet?): List<AudioFrame>
}

/**
 * A video encoder used for encoding [VideoFrame]s into [Packet]s.
 *
 * @constructor Create a new video encoder with the given codec.
 *
 * @param codec
 */
public expect class VideoEncoder(codec: Codec) : VideoCodecContext, Encoder {
    public constructor(
        codec: Codec,
        bitRate: Long,
        width: Int,
        height: Int
    )

    public constructor(
        codec: Codec,
        bitRate: Long,
        width: Int,
        height: Int,
        timeBase: Rational,
        framerate: Rational,
        gopSize: Int = 25,
        maxBFrames: Int = 2,
        pixFmt: PixelFormat = PixelFormat.YUV420P
    )

    public fun encode(frame: VideoFrame?): List<Packet>
    public override fun encode(): Packet?
}

/**
 * A video decoder.
 *
 * @constructor Create a new video decoder with the given codec.
 *
 * @param codec
 */
public expect class VideoDecoder(codec: Codec) : VideoCodecContext, Decoder {
    /**
     * Receive a new decoded frame of video data.
     *
     * NOTE: This frame should not be modified or closed by the user.
     */
    public fun decode(): VideoFrame?
    public override fun decode(packet: Packet?): List<VideoFrame>
}