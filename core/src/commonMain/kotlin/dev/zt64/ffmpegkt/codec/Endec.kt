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