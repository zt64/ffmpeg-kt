package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.Rational

internal expect class NativeAVPacket

/**
 * A packet of compressed data to be written to a container format
 */
public expect value class Packet internal constructor(internal val native: NativeAVPacket) : AutoCloseable {
    /**
     * Create an empty packet
     */
    public constructor()

    /**
     * The presentation timestamp
     */
    public val pts: Long
    public val dts: Long

    /**
     * The data contained within this packet
     */
    public val data: ByteArray

    public val size: Int

    /**
     * The index of the stream which this packet belongs to
     */
    public var streamIndex: Int
    public var flags: Int
    public var duration: Long
    public var pos: Long
    public val timeBase: Rational

    public fun rescaleTs(src: Rational, dst: Rational)

    public fun decode(): Frame

    override fun close()
}