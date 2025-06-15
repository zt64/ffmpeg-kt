package dev.zt64.ffmpegkt.avformat

public expect class NativeAVIOContext

public expect value class AVIOContext(internal val native: NativeAVIOContext) : AutoCloseable {
    public constructor(filename: String, flags: Int)
    public constructor(bytes: ByteArray)

    public override fun close()
}