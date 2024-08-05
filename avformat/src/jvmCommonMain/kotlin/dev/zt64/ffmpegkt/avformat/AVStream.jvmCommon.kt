package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodecParameters
import dev.zt64.ffmpegkt.avutil.AVClass
import dev.zt64.ffmpegkt.avutil.AVRational
import org.bytedeco.ffmpeg.avformat.AVStream

internal typealias AVStreamNative = AVStream

public actual class AVStream(internal val native: AVStreamNative) {
    public actual val avClass: AVClass = AVClass(native.av_class())
    public actual val index: Int = native.index()
    public actual val id: Int = native.id()
    public actual val codecParameters: AVCodecParameters = AVCodecParameters(native.codecpar())
    public actual val timeBase: AVRational = AVRational(native.time_base())

    override fun toString(): String {
        return "AVStream(avClass=$avClass, index=$index, id=$id)"
    }
}