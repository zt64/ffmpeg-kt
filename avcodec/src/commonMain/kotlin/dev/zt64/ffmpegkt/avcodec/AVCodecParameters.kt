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

public expect class AudioCodecParameters : CodecParameters {
    public val format: AVSampleFormat
    public val channelLayout: AVChannelLayout
    public val sampleRate: Int
    public val blockAlign: Int
    public val frameSize: Int
    public val initialPadding: Int
    public val trailingPadding: Int
    public val seekPreroll: Int
}

public expect class VideoCodecParameters : CodecParameters {
    public val format: AVPixelFormat
    public val width: Int
    public val height: Int
    public val aspectRatio: AVRational
    public val framerate: AVRational
    public val fieldOrder: FieldOrder
    public val colorRange: ColorRange
    public val colorPrimaries: ColorPrimaries

    // public val colorTrc: ColorTrc
    // public val colorSpace: ColorSpace
    // public val chromaLocation: ChromaLocation

    public val videoDelay: Int
}