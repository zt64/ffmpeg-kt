package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*

public expect open class CodecParameters {
    public val codecType: AVMediaType
    public val codecId: AVCodecID
    public var codecTag: Int

    // public val codedSideData: AVPacketSideData

    public val bitRate: Long
    public val bitsPerCodedSample: Int
    public val bitsPerRawSample: Int
    public val profile: Int
    public val level: Int
}

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
    public val channelLayout: AVChannelLayout
    public val sampleRate: Int
    public val blockAlign: Int
    public val frameSize: Int
    public val initialPadding: Int
    public val trailingPadding: Int
    public val seekPreroll: Int
}

internal inline fun AudioCodecParameters.commonToString(): String {
    return "AudioCodecParameters(" +
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

internal inline fun VideoCodecParameters.commonToString(): String {
    return "VideoCodecParameters(" +
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