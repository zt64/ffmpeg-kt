package dev.zt64.ffmpegkt.stream

import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.codec.*

/**
 * A data stream
 *
 */
public expect open class Stream {
    /**
     * The index of this stream in the parent format
     */
    public val index: Int

    /**
     * The id of this stream
     */
    public val id: Int
    public val timeBase: Rational
    public val startTime: Long
    public val duration: Long

    /**
     * The number of frames in this stream
     */
    public val frames: Long
    public open val codecParameters: CodecParameters
    public val codecContext: CodecContext?
}

public expect class AudioStream : Stream {
    public override var codecParameters: AudioCodecParameters
}

public expect class VideoStream : Stream {
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