package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.codec.Packet

public expect class InputContainer : Container {
    public var startTime: Long
    public var duration: Long
    public var bitRate: Long

    public fun demux(): List<Packet>

    public fun decode(): List<Frame>

    public fun seek(offset: Int)

    public override fun close()
}