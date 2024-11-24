package dev.zt64.ffmpegkt.avformat

public expect class NativeAVProbeData

public expect value class ProbeData(public val native: NativeAVProbeData) {
    public val filename: String
    public val buf: ByteArray
    public val mimeType: String
}