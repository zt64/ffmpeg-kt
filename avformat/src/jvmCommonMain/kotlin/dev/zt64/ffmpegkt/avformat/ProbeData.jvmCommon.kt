package dev.zt64.ffmpegkt.avformat

public actual typealias NativeAVProbeData = org.bytedeco.ffmpeg.avformat.AVProbeData

@JvmInline
public actual value class ProbeData(public actual val native: NativeAVProbeData) {
    public actual inline val filename: String
        get() = native.filename().string
    public actual inline val buf: ByteArray
        get() = native.buf().stringBytes
    public actual inline val mimeType: String
        get() = native.mime_type().string
}