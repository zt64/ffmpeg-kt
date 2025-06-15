package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.Packet
import dev.zt64.ffmpegkt.avutil.Frame

public expect class InputContainer : Container {
    public var startTime: Long
    public var duration: Long
    public var bitRate: Long

    public fun demux(): List<Packet>

    public fun decode(): List<Frame>

    public fun seek(offset: Int)

    public override fun close()
}