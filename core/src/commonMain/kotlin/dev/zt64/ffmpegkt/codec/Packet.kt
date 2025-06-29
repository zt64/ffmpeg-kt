package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.Rational

internal expect class NativeAVPacket

/**
 * A packet of compressed data, typically output from an encoder or read from a media container.
 *
 * This class is a wrapper around a native FFmpeg `AVPacket` and holds compressed data
 * for a single frame of a specific stream (e.g., video or audio). It is a resource
 * that must be closed to release underlying native memory.
 *
 * @property native The underlying native packet object.
 */
public expect value class Packet internal constructor(internal val native: NativeAVPacket) : AutoCloseable {
    /**
     * Creates an empty packet.
     * This is useful for allocating a packet that will be filled by a function like `av_read_frame`.
     */
    public constructor()

    /**
     * Creates a packet and initializes it with the given data.
     *
     * @param data The raw data to populate the packet with.
     */
    public constructor(data: ByteArray)

    /**
     * The presentation timestamp (PTS) of the packet in `timeBase` units.
     * This is the time at which the decoded frame should be presented to the user.
     */
    public val pts: Long

    /**
     * The decompression timestamp (DTS) of the packet in `timeBase` units.
     * This is the time at which the packet should be decoded.
     */
    public val dts: Long

    /**
     * The raw compressed data contained within this packet.
     */
    public val data: ByteArray

    /**
     * The size of the packet's data in bytes.
     */
    public val size: Int

    /**
     * The index of the stream to which this packet belongs within a media container.
     */
    public var streamIndex: Int

    /**
     * A collection of flags associated with the packet (e.g., indicating a keyframe).
     */
    public var flags: Int

    /**
     * The duration of the packet's content in `timeBase` units.
     */
    public var duration: Long

    /**
     * The byte position of this packet within the stream, or -1 if unknown.
     */
    public var pos: Long

    /**
     * The time base in which the packet's timestamps (PTS, DTS, duration) are expressed.
     */
    public val timeBase: Rational

    /**
     * Rescales the packet's timestamps (PTS, DTS, duration) from a source time base to a destination time base.
     *
     * @param src The source time base to convert from.
     * @param dst The destination time base to convert to.
     */
    public fun rescaleTimestamp(src: Rational, dst: Rational)

    /**
     * Decodes this packet into a raw [Frame].
     *
     * This is a high-level convenience function that finds the appropriate decoder
     * for the packet's stream and performs the decoding.
     *
     * @return The decoded [Frame].
     */
    public fun decode(): Frame

    /**
     * Releases the native resources associated with this packet.
     */
    override fun close()
}