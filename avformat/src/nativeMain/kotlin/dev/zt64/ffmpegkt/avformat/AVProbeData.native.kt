package dev.zt64.ffmpegkt.avformat

public actual typealias NativeAVProbeData = ffmpeg.AVProbeData

public actual value class ProbeData actual constructor(public actual val native: NativeAVProbeData) {
    public actual val filename: String
        get() = TODO("Not yet implemented")
    public actual val buf: ByteArray
        get() = TODO("Not yet implemented")
    public actual val mimeType: String
        get() = TODO("Not yet implemented")
}