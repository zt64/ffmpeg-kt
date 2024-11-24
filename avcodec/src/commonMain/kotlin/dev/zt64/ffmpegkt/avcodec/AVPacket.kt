package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.Rational

public expect class NativeAVPacket

/**
 * A packet of compressed data to be written to a container format
 */
public expect value class AVPacket(internal val native: NativeAVPacket) : AutoCloseable {
    /**
     * Create an empty packet
     */
    public constructor()

    /**
     * The index of the stream which this packet belongs to
     */
    public var streamIndex: Int
    public var pos: Long
    public val size: Int

    /**
     * The data contained within this packet
     */
    public val data: ByteArray

    /**
     * The presentation timestamp
     */
    public val pts: Long
    public val dts: Long

    public fun rescaleTs(src: Rational, dst: Rational)
}