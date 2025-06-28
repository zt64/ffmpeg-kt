package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.codec.Packet

public expect class InputContainer : Container {
    public val startTime: Long
    public val duration: Long
    public val bitRate: Long

    public fun demux(): List<Packet>

    public fun decode(): List<Frame>

    public fun seek(offset: Int)

    public override fun close()
}