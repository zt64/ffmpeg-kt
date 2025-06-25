package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*

public sealed interface Encoder {
    /**
     * Receive a new encoded packet of data
     * @return the packet or null if no more data
     */
    public fun encode(): Packet?
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
     * @param bitrate The target bitrate.
     * @param sampleFmt The sample format.
     * @param sampleRate The sample rate.
     * @param channelLayout The channel layout.
     */
    public constructor(
        codec: Codec,
        bitrate: Long,
        sampleFmt: SampleFormat,
        sampleRate: Int,
        channelLayout: ChannelLayout
    ) : this(codec) {
        this.bitrate = bitrate
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
     * @param bitrate The target bitrate.
     * @param width The video width.
     * @param height The video height.
     * @param timeBase The fundamental time unit.
     * @param framerate The video framerate.
     * @param gopSize The group of pictures size.
     * @param maxBFrames The maximum number of B-frames.
     * @param pixFmt The pixel format.
     */
    public constructor(
        codec: CodecID,
        bitrate: Long,
        width: Int,
        height: Int,
        timeBase: Rational,
        framerate: Rational,
        gopSize: Int = 12,
        maxBFrames: Int = 2,
        pixFmt: PixelFormat = PixelFormat.YUV420P
    ) : this(
        codec = Codec.findEncoder(codec) ?: throw IllegalArgumentException("Codec not found: $codec"),
        bitrate = bitrate,
        width = width,
        height = height,
        timeBase = timeBase,
        framerate = framerate,
        gopSize = gopSize,
        maxBFrames = maxBFrames,
        pixFmt = pixFmt
    )

    /**
     * Creates and configures a new video encoder.
     *
     * @param codec The codec to use for encoding.
     * @param bitrate The target bitrate.
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
        bitrate: Long,
        width: Int,
        height: Int,
        timeBase: Rational,
        framerate: Rational,
        gopSize: Int = 12,
        maxBFrames: Int = 2,
        pixFmt: PixelFormat = PixelFormat.YUV420P
    ) : this(codec) {
        this.bitrate = bitrate
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
     * @param bitrate The target bitrate.
     * @param width The video width.
     * @param height The video height.
     */
    public constructor(
        codec: Codec,
        bitrate: Long,
        width: Int,
        height: Int
    ) : this(codec) {
        this.bitrate = bitrate
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