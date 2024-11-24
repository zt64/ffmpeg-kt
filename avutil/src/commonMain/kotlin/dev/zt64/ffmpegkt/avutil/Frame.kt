package dev.zt64.ffmpegkt.avutil

public expect class NativeAVFrame

public interface Frame : AutoCloseable {
    /**
     * The native instance of the frame.
     */
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

    /**
     * Allocates a buffer for the frame.
     *
     * @param align
     */
    public fun getBuffer(align: Int = 0)

    /**
     * Ensure that the frame data is writable, avoiding data copy if possible.
     * Do nothing if the frame is writable, allocate new buffers and copy the data if it is not.
     * Non-refcounted frames behave as non-writable, i.e. a copy is always made.
     */
    public fun makeWritable()
}

/**
 * Represents a frame of audio data.
 *
 * @property native
 */
public expect value class AudioFrame(override val native: NativeAVFrame) : Frame {
    /**
     * Create a new blank audio frame for encoding.
     */
    public constructor()

    public var nbSamples: Int
    public var format: SampleFormat
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
    public var format: PixelFormat
}

/**
 * Frame data wrapper backed by the native byte array buffer.
 *
 * 2D array of bytes, where the outer array is the planes of the frame,
 * while the inner arrays are the data of each plane.
 */
public expect class FrameData : AbstractList<FrameData.FrameDataSegment> {
    /**
     * A single plane of the frame, represented as a list of bytes.
     */
    public inner class FrameDataSegment : AbstractList<UByte> {
        public operator fun set(index: Int, value: UByte)
    }
}