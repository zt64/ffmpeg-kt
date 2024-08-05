package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVRational
import kotlin.jvm.JvmInline

public expect class NativeAVPacket

@JvmInline
public expect value class AVPacket(internal val native: NativeAVPacket) : AutoCloseable {
    public constructor()

    public var streamIndex: Int
    public var pos: Long
    public val size: Int
    public val data: ByteArray
    public val pts: Long

    public fun rescaleTs(src: AVRational, dst: AVRational)
}