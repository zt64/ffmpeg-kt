package dev.zt64.ffmpegkt.stream

import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.codec.*

/**
 * A data stream within a media container.
 *
 * Represents a single, continuous stream of data (e.g., video or audio) within a multimedia file.
 */
public expect open class Stream {
    /**
     * The index of this stream in the parent format
     */
    public val index: Int

    /**
     * The id of this stream. This is a unique identifier within the container.
     */
    public val id: Int

    /**
     * The time base of the stream, which is the fundamental unit of time (in seconds) for this stream's timestamps.
     */
    public var timeBase: Rational

    /**
     * The start time of the stream
     */
    public val startTime: Long

    /**
     * The duration of the stream
     */
    public val duration: Long

    /**
     * The number of frames in this stream
     */
    public val frames: Long

    /**
     * The codec parameters for this stream.
     */
    public open val codecParameters: CodecParameters

    /**
     * The codec context for this stream. This is only available after a codec has been opened for the stream.
     */
    public val codecContext: CodecContext?
}

/**
 * An audio stream.
 */
public expect class AudioStream : Stream {
    /**
     * The audio-specific codec parameters for this stream.
     */
    public override var codecParameters: AudioCodecParameters
}

/**
 * A video stream.
 */
public expect class VideoStream : Stream {
    /**
     * The video-specific codec parameters for this stream.
     */
    public override var codecParameters: VideoCodecParameters
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun Stream.commonToString(): String {
    return "Stream(" +
        "index=$index, " +
        "id=$id, " +
        "timeBase=$timeBase, " +
        "startTime=$startTime, " +
        "duration=$duration, " +
        "frames=$frames, " +
        "codecParameters=$codecParameters" +
        ")"
}