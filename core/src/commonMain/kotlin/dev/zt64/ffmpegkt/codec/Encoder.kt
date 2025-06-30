package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*

/**
 * A sealed interface representing an encoder that converts raw [Frame]s into compressed [Packet]s.
 * This provides a unified API for both audio and video encoding operations.
 */
public sealed interface Encoder {
    /**
     * Creates a new [Frame] that is correctly configured for this encoder.
     *
     * This is a convenience method to allocate a frame with the appropriate properties
     * (e.g., dimensions, sample format) required by the encoder.
     *
     * @return A new, empty [Frame] ready to be filled with data.
     */
    public fun createFrame(): Frame
}

/**
 * An audio encoder for converting raw [AudioFrame]s into compressed [Packet]s.
 *
 * This class wraps an underlying FFmpeg audio `AVCodecContext` and provides a
 * simplified API for the encoding process.
 *
 * @param codec The codec to use for encoding.
 * @param bitrate The target bitrate for the encoded audio (in bits per second). Default is 0, which means the codec's default bitrate will be used.
 * @param sampleFmt The desired audio sample format.
 * @param sampleRate The audio sample rate (in Hz).
 * @param channelLayout The layout of the audio channels.
 */
public class AudioEncoder(
    codec: Codec,
    bitrate: Long = 0,
    sampleFmt: SampleFormat = codec.sampleFormats.first(),
    sampleRate: Int = 48000,
    channelLayout: ChannelLayout = codec.channelLayouts.maxBy { it.nbChannels }
) : AudioCodecContext(codec), Encoder {
    init {
        this.bitrate = bitrate
        this.sampleFmt = sampleFmt
        this.sampleRate = sampleRate
        this.channelLayout = channelLayout
    }

    public constructor(
        codecId: CodecID,
        bitrate: Long = 0,
        sampleRate: Int = 48000
    ) : this(
        codec = Codec.findEncoder(codecId) ?: throw IllegalArgumentException("Codec not found: $codecId"),
        bitrate = bitrate,
        sampleRate = sampleRate
    )

    /**
     * Encodes a single audio [Frame] into one or more [Packet]s.
     *
     * This is a high-level convenience function that handles sending a frame and
     * receiving all resulting packets.
     *
     * @param frame The [AudioFrame] to encode. Passing `null` flushes the encoder,
     *              returning any internally buffered packets.
     * @return A list of packets encoded from the frame. The list may be empty if
     *         the encoder needs more frames to produce a packet.
     */
    public fun encode(frame: AudioFrame?): List<Packet> {
        sendFrame(frame)

        return receivePackets()
    }

    /**
     * Creates a new [AudioFrame] configured with this encoder's settings.
     *
     * @return A new, empty [AudioFrame] with the correct `nbSamples`, `format`, and `channelLayout`.
     */
    public override fun createFrame(): AudioFrame = AudioFrame(
        nbSamples = frameSize,
        format = sampleFmt,
        channelLayout = channelLayout
    )
}

/**
 * A video encoder for converting raw [VideoFrame]s into compressed [Packet]s.
 *
 * This class wraps an underlying FFmpeg video `AVCodecContext` and provides a
 * simplified API for the encoding process.
 *
 * @param codec The video [Codec] to use for encoding. It must be an encoder.
 * @constructor Creates a new video encoder with the specified codec.
 */
public class VideoEncoder(codec: Codec) : VideoCodecContext(codec), Encoder {

    /**
     * Creates and configures a new video encoder by finding a registered encoder for the given [CodecID].
     *
     * @param codec The ID of the desired video codec.
     * @param bitrate The target bitrate for the encoded video (in bits per second). Default is 0, which means the codec's default bitrate will be used.
     * @param width The width of the video frame.
     * @param height The height of the video frame.
     * @param framerate The video framerate in frames per second.
     * @param timeBase The fundamental time unit for frame timestamps.
     * @param gopSize The Group of Pictures (GOP) size, i.e., the number of frames between keyframes.
     * @param maxBFrames The maximum number of B-frames between non-B-frames.
     * @param pixFmt The pixel format of the video frames.
     * @throws IllegalArgumentException if no encoder is found for the specified ID.
     */
    public constructor(
        codec: CodecID,
        bitrate: Long = 0,
        width: Int,
        height: Int,
        framerate: Int,
        timeBase: Rational = Rational(1, framerate),
        gopSize: Int = 12,
        maxBFrames: Int = 2,
        pixFmt: PixelFormat = PixelFormat.YUV420P
    ) : this(
        codec = Codec.findEncoder(codec) ?: throw IllegalArgumentException("Codec not found: $codec"),
        bitrate = bitrate,
        width = width,
        height = height,
        framerate = Rational(framerate, 1),
        timeBase = timeBase,
        gopSize = gopSize,
        maxBFrames = maxBFrames,
        pixFmt = pixFmt
    )

    /**
     * Creates and configures a new video encoder.
     *
     * @param codec The codec to use for encoding.
     * @param bitrate The target bitrate for the encoded video (in bits per second).
     * @param width The width of the video frame.
     * @param height The height of the video frame.
     * @param framerate The video framerate as a rational number.
     * @param timeBase The fundamental time unit for frame timestamps.
     * @param gopSize The Group of Pictures (GOP) size.
     * @param maxBFrames The maximum number of B-frames.
     * @param pixFmt The pixel format of the video frames.
     */
    public constructor(
        codec: Codec,
        bitrate: Long,
        width: Int,
        height: Int,
        framerate: Rational,
        timeBase: Rational,
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
     * Other parameters will be set to default values.
     *
     * @param codec The codec to use for encoding.
     * @param bitrate The target bitrate for the encoded video (in bits per second).
     * @param width The width of the video frame.
     * @param height The height of the video frame.
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
     * Encodes a single video [Frame] into one or more [Packet]s.
     *
     * This is a high-level convenience function that handles sending a frame and
     * receiving all resulting packets.
     *
     * @param frame The [VideoFrame] to encode. Passing `null` flushes the encoder,
     *              returning any internally buffered packets.
     * @return A list of packets encoded from the frame. The list may be empty if
     *         the encoder needs more frames to produce a packet.
     */
    public fun encode(frame: VideoFrame?): List<Packet> {
        sendFrame(frame)

        return receivePackets()
    }

    /**
     * Creates a new [VideoFrame] configured with this encoder's settings.
     *
     * @return A new, empty [VideoFrame] with the correct `width`, `height`, and `format`.
     */
    public override fun createFrame(): VideoFrame = VideoFrame(
        width = width,
        height = height,
        format = pixFmt
    )
}