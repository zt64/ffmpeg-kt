package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVClass
import org.bytedeco.ffmpeg.avformat.AVFormatContext

internal typealias NativeAVStreamGroup = AVFormatContext

public actual class AVStreamGroup(internal val native: AVStreamNative) {
    public actual val avClass: AVClass
        get() = TODO("Not yet implemented")
    public actual val index: UInt
        get() = TODO("Not yet implemented")
    public actual val id: Long
        get() = TODO("Not yet implemented")
    public actual val type: AVStreamGroupParamsType
        get() = TODO("Not yet implemented")
    public actual val streams: List<AVStream>
        get() = TODO("Not yet implemented")
    public actual val disposition: Int
        get() = TODO("Not yet implemented")
}