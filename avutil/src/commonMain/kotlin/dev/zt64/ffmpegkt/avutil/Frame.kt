package dev.zt64.ffmpegkt.avutil

public expect class NativeAVFrame

public interface Frame : AutoCloseable {
    public val native: NativeAVFrame

    /**
     * The number of planes in the frame.
     */
    public val linesize: IntArray

    /**
     * The data of the frame.
     *
     * The outer array represents the planes of the frame, while the inner arrays represent the data of each plane.
     */
    public val data: FrameData

    /**
     * The presentation timestamp of the frame.
     */
    public var pts: Long

    public fun getBuffer(align: Int = 0)
    public fun makeWritable()
}

/**
 * Represents a frame of audio data.
 *
 * @property native
 */
public expect value class AudioFrame(override val native: NativeAVFrame) : Frame {
    public constructor()

    public var nbSamples: Int
    public var format: AVSampleFormat
    public var channelLayout: AVChannelLayout
    public var sampleRate: Int
}

/**
 * Represents a frame of video data.
 *
 * @property native
 */
public expect value class VideoFrame(override val native: NativeAVFrame) : Frame {
    public constructor()

    public var width: Int
    public var height: Int
    public var format: AVPixelFormat
}

/**
 * Frame data.
 *
 * 2D array of bytes, where the outer array is the planes of the frame, while the inner arrays is the data of each plane.
 *
 */
public expect class FrameData : AbstractList<FrameData.FrameDataSegment> {
    /**
     * A plane of the frame.
     */
    public inner class FrameDataSegment {
        public operator fun set(index: Int, value: UByte)
        public operator fun get(index: Int): UByte
    }
}