package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*

/**
 * Base class for audio codec contexts, containing common properties for audio encoding and decoding.
 *
 * @property sampleFmt The sample format.
 * @property sampleRate The sample rate.
 * @property channelLayout The channel layout.
 * @property frameSize The number of samples per channel in an audio frame.
 */
public expect sealed class AudioCodecContext(codec: Codec) : CodecContext {
    public var sampleFmt: SampleFormat
    public var sampleRate: Int
    public var channelLayout: ChannelLayout
    public var frameSize: Int

    public override fun decode(): AudioFrame?
}

/**
 * Base class for video codec contexts, containing common properties for video encoding and decoding.
 *
 * @property pixFmt The pixel format.
 * @property width The video width.
 * @property height The video height.
 * @property gopSize The size of the group of pictures.
 * @property maxBFrames The maximum number of B-frames between non-B-frames.
 * @property mbDecision The macroblock decision algorithm to use.
 * @property framerate The frame rate of the video.
 */
public expect sealed class VideoCodecContext(codec: Codec) : CodecContext {
    public var pixFmt: PixelFormat
    public var width: Int
    public var height: Int
    public var gopSize: Int
    public var maxBFrames: Int
    public var mbDecision: Int

    /**
     * The frame rate of the codec context.
     */
    public var framerate: Rational

    public override fun decode(): VideoFrame?
}

/**
 * An audio encoder for converting [AudioFrame]s into [Packet]s.
 *
 * @constructor Create a new audio encoder with the given codec.
 * @param codec The codec to use for encoding.
 */
public class AudioEncoder(codec: Codec) : AudioCodecContext(codec), Encoder {
    /**
     * Creates and configures a new audio encoder.
     *
     * @param codec The codec to use for encoding.
     * @param bitRate The target bitrate.
     * @param sampleFmt The sample format.
     * @param sampleRate The sample rate.
     * @param channelLayout The channel layout.
     */
    public constructor(
        codec: Codec,
        bitRate: Long,
        sampleFmt: SampleFormat,
        sampleRate: Int,
        channelLayout: ChannelLayout
    ) : this(codec) {
        this.bitRate = bitRate
        this.sampleFmt = sampleFmt
        this.sampleRate = sampleRate
        this.channelLayout = channelLayout
    }

    /**
     * Encodes an audio frame.
     *
     * @param frame The [AudioFrame] to encode. If null, the encoder is flushed, and any delayed packets are returned.
     * @return A list of [Packet]s containing the encoded data.
     */
    public fun encode(frame: AudioFrame?): List<Packet> {
        sendFrame(frame)

        return buildList {
            while (true) {
                add(receivePacket() ?: break)
            }
        }
    }

    public override fun encode(): Packet? = receivePacket()
}

/**
 * An audio decoder for converting [Packet]s into [AudioFrame]s.
 *
 * @constructor Create a new audio decoder with the given codec.
 * @param codec The codec to use for decoding.
 */
public class AudioDecoder(codec: Codec) : AudioCodecContext(codec), Decoder {
    /**
     * A parser for handling raw codec data.
     * It is created on-demand and its lifecycle is tied to this decoder.
     */
    public override val parser: CodecParserContext by lazy { CodecParserContext(this) }

    /**
     * Decodes a packet into a list of audio frames.
     *
     * @param packet The [Packet] to decode. If null, the decoder is flushed, and any delayed frames are returned.
     * @return A list of decoded [AudioFrame]s.
     */
    public override fun decode(packet: Packet?): List<AudioFrame> {
        sendPacket(packet)

        return buildList {
            while (true) {
                val frame = decode() ?: break
                add(frame)
            }
        }
    }
}

/**
 * A video encoder for converting [VideoFrame]s into [Packet]s.
 *
 * @constructor Create a new video encoder with the given codec.
 * @param codec The codec to use for encoding.
 */
public class VideoEncoder(codec: Codec) : VideoCodecContext(codec), Encoder {
    /**
     * Creates and configures a new video encoder.
     *
     * @param codec The codec to use for encoding.
     * @param bitRate The target bitrate.
     * @param width The video width.
     * @param height The video height.
     * @param timeBase The fundamental time unit.
     * @param framerate The video framerate.
     * @param gopSize The group of pictures size.
     * @param maxBFrames The maximum number of B-frames.
     * @param pixFmt The pixel format.
     */
    public constructor(
        codec: Codec,
        bitRate: Long,
        width: Int,
        height: Int,
        timeBase: Rational,
        framerate: Rational,
        gopSize: Int = 12,
        maxBFrames: Int = 2,
        pixFmt: PixelFormat = PixelFormat.YUV420P
    ) : this(codec) {
        this.bitRate = bitRate
        this.width = width
        this.height = height
        this.timeBase = timeBase
        this.framerate = framerate
        this.gopSize = gopSize
        this.maxBFrames = maxBFrames
        this.pixFmt = pixFmt
    }

    /**
     * Creates and configures a new video encoder with minimal parameters.
     *
     * @param codec The codec to use for encoding.
     * @param bitRate The target bitrate.
     * @param width The video width.
     * @param height The video height.
     */
    public constructor(
        codec: Codec,
        bitRate: Long,
        width: Int,
        height: Int
    ) : this(codec) {
        this.bitRate = bitRate
        this.width = width
        this.height = height
    }

    /**
     * Encodes a video frame.
     *
     * @param frame The [VideoFrame] to encode. If null, the encoder is flushed, and any delayed packets are returned.
     * @return A list of [Packet]s containing the encoded data.
     */
    public fun encode(frame: VideoFrame?): List<Packet> {
        sendFrame(frame)

        return buildList {
            while (true) {
                add(receivePacket() ?: break)
            }
        }
    }

    public override fun encode(): Packet? = receivePacket()
}

/**
 * A video decoder for converting [Packet]s into [VideoFrame]s.
 *
 * @constructor Create a new video decoder with the given codec.
 * @param codec The codec to use for decoding.
 */
public class VideoDecoder(codec: Codec) : VideoCodecContext(codec), Decoder {
    /**
     * A parser for handling raw codec data.
     * It is created on-demand and its lifecycle is tied to this decoder.
     */
    public override val parser: CodecParserContext by lazy { CodecParserContext(this) }

    /**
     * Decodes a packet into a list of video frames.
     *
     * @param packet The [Packet] to decode. If null, the decoder is flushed, and any delayed frames are returned.
     * @return A list of decoded [VideoFrame]s.
     */
    public override fun decode(packet: Packet?): List<VideoFrame> {
        sendPacket(packet)

        return buildList {
            while (true) {
                val frame = decode() ?: break
                add(frame)
            }
        }
    }
}