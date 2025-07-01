package dev.zt64.ffmpegkt.avutil

internal expect class NativeAVFrame

public expect open class Frame : AutoCloseable {
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

    public var timeBase: Rational

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
    final override fun close()
}

/**
 * Represents a frame of audio data.
 *
 * @property native
 */
public expect class AudioFrame(
    nbSamples: Int,
    format: SampleFormat,
    channelLayout: ChannelLayout
) : Frame {
    public constructor()

    public var nbSamples: Int
    public var format: SampleFormat
    public var channelLayout: ChannelLayout
    public var sampleRate: Int
}

/**
 * Represents a frame of video data.
 *
 * @property native
 */
public expect class VideoFrame() : Frame {
    public constructor(
        width: Int,
        height: Int,
        format: PixelFormat
    )

    public inline var width: Int
    public inline var height: Int
    public inline var format: PixelFormat
}

/**
 * Frame data wrapper backed by the native byte array buffer.
 *
 * 2D array of bytes, where the outer array is the planes of the frame,
 * while the inner arrays are the data of each plane.
 */
public expect class FrameData : AbstractList<FrameData.FrameDataSegment> {
    public override val size: Int
    public override fun get(index: Int): FrameDataSegment
    public operator fun set(index: Int, value: ByteArray)

    /**
     * A single plane of the frame, represented as a list of bytes.
     */
    public class FrameDataSegment : AbstractList<UByte> {
        public override val size: Int
        public override fun get(index: Int): UByte
        public operator fun set(index: Int, value: UByte)

        public fun put(bytes: ByteArray, offset: Int = 0, length: Int = bytes.size)
    }
}