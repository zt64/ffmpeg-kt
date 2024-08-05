package dev.zt64.ffmpegkt.avutil

import kotlin.jvm.JvmInline

public expect class NativeAVFrame

/**
 * Represents a frame of video data.
 *
 * @property native
 */
@JvmInline
public expect value class AVFrame(internal val native: NativeAVFrame) : AutoCloseable {
    public constructor()

    /**
     * The number of planes in the frame.
     */
    public val linesize: IntArray

    /**
     * The data of the frame.
     */
    public val data: AVFrameData

    /**
     * The width of the frame.
     */
    public var width: Int
    public var height: Int
    public var nbSamples: Int
    public var format: AVPixelFormat
    public var channelLayout: AVChannelLayout
    public var pts: Long

    /**
     * Get a buffer for the frame data.
     *
     * @param align The buffer alignment.
     */
    public fun getBuffer(align: Int = 0)

    /**
     * Make the frame writable.
     *
     */
    public fun makeWritable()
}

public expect class AVFrameData {
    public operator fun get(index: Int): AVFrameDataSegment

    public inner class AVFrameDataSegment {
        public operator fun get(index: Int): Byte
        public operator fun set(index: Int, value: Byte)
    }
}