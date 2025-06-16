package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*

public expect open class CodecParameters {
    public val codecType: MediaType
    public val codecId: CodecID
    public var codecTag: Int

    // public val codedSideData: AVPacketSideData

    public val bitRate: Long
    public val bitsPerCodedSample: Int
    public val bitsPerRawSample: Int
    public val profile: Int
    public val level: Int
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun CodecParameters.commonToString(): String {
    return "CodecParameters(" +
        "bitRate=$bitRate, " +
        "codecType=$codecType, " +
        "codecId=$codecId, " +
        "codecTag=$codecTag, " +
        "bitsPerCodedSample=$bitsPerCodedSample, " +
        "bitsPerRawSample=$bitsPerRawSample, " +
        "profile=$profile, " +
        "level=$level" +
        ")"
}

public expect class AudioCodecParameters : CodecParameters {
    public val format: SampleFormat
    public val channelLayout: ChannelLayout
    public val sampleRate: Int
    public val blockAlign: Int
    public val frameSize: Int
    public val initialPadding: Int
    public val trailingPadding: Int
    public val seekPreroll: Int
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun AudioCodecParameters.commonToString(): String {
    return "AudioCodecParameters(" +
        "bitRate=$bitRate, " +
        "codecType=$codecType, " +
        "codecId=$codecId, " +
        "codecTag=$codecTag, " +
        "bitsPerCodedSample=$bitsPerCodedSample, " +
        "bitsPerRawSample=$bitsPerRawSample, " +
        "profile=$profile, " +
        "level=$level, " +
        "format=$format, " +
        "channelLayout=$channelLayout, " +
        "sampleRate=$sampleRate, " +
        "blockAlign=$blockAlign, " +
        "frameSize=$frameSize, " +
        "initialPadding=$initialPadding, " +
        "trailingPadding=$trailingPadding, " +
        "seekPreroll=$seekPreroll" +
        ")"
}

public expect class VideoCodecParameters : CodecParameters {
    public val format: PixelFormat
    public val width: Int
    public val height: Int
    public val aspectRatio: Rational
    public val framerate: Rational
    public val fieldOrder: FieldOrder
    public val colorRange: ColorRange
    public val colorPrimaries: ColorPrimaries

    // public val colorTrc: ColorTrc
    // public val colorSpace: ColorSpace
    // public val chromaLocation: ChromaLocation

    public val videoDelay: Int
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun VideoCodecParameters.commonToString(): String {
    return "VideoCodecParameters(" +
        "bitRate=$bitRate, " +
        "codecType=$codecType, " +
        "codecId=$codecId, " +
        "codecTag=$codecTag, " +
        "bitsPerCodedSample=$bitsPerCodedSample, " +
        "bitsPerRawSample=$bitsPerRawSample, " +
        "profile=$profile, " +
        "level=$level, " +
        "format=$format, " +
        "width=$width, " +
        "height=$height, " +
        "aspectRatio=$aspectRatio, " +
        "framerate=$framerate, " +
        "fieldOrder=$fieldOrder, " +
        "colorRange=$colorRange, " +
        "colorPrimaries=$colorPrimaries, " +
        "videoDelay=$videoDelay" +
        ")"
}