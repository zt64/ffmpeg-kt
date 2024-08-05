package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVClass

public actual class AVStream(internal val native: ffmpeg.AVStream) {
    public actual val avClass: AVClass = AVClass(native.av_class!!)
    public actual val index: Int = native.index
    public actual val id: Int = native.id
}