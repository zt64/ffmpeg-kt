package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.Rational

internal expect class NativeAVPacket

public expect fun rescale(value: Long, source: Rational, destination: Rational): Long

/**
 * A packet of compressed data, typically output from an encoder or read from a media container.
 *
 * @param pts The presentation timestamp (PTS) of the packet in `timeBase` units.
 * @param dts The decompression timestamp (DTS) of the packet in `timeBase` units.
 * @param duration The duration of the packet's content in `timeBase` units.
 * @param streamIndex The index of the stream to which this packet belongs within a media container.
 * @param size The size of the packet's data in bytes.
 * @param data The raw compressed data contained within this packet.
 */
public class Packet(
    pts: Long = 0x80000000L,
    dts: Long = 0x80000000L,
    duration: Long = 0L,
    public val streamIndex: Int = -1,
    public val size: Int = 0,
    public val data: ByteArray = byteArrayOf()
) {
    /**
     * The time base in which the packet's timestamps (PTS, DTS, duration) are expressed.
     * This is typically set to the time base of the stream to which this packet belongs.
     */
    public var pts: Long = pts
        private set

    /**
     * The decompression timestamp (DTS) of the packet in `timeBase` units.
     */
    public var dts: Long = dts
        private set

    /**
     * The duration of the packet's content in `timeBase` units.
     */
    public var duration: Long = duration
        private set

    public fun rescaleTimestamp(source: Rational, destination: Rational) {
        pts = rescale(pts, source, destination)
        dts = rescale(dts, source, destination)
        duration = rescale(duration, source, destination)
    }

    public fun decode(): Frame {
        // This is a placeholder for the actual decoding logic.
        // In a real implementation, this would find the appropriate decoder
        // for the stream and decode the packet into a Frame.
        throw NotImplementedError("Decoding not implemented")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Packet

        if (pts != other.pts) return false
        if (dts != other.dts) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pts.hashCode()
        result = 31 * result + dts.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}