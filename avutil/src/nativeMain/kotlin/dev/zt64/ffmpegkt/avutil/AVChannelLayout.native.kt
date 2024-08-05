package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.av_channel_layout_copy
import kotlinx.cinterop.CPointer

@Suppress("ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT")
public actual value class AVChannelLayout(internal val native: CPointer<ffmpeg.AVChannelLayout>) {
    public actual val order: Int
        get() = TODO("Not yet implemented")
    public actual val nbChannels: Int
        get() = TODO("Not yet implemented")

    public actual fun copyTo(dst: AVChannelLayout) {
        av_channel_layout_copy(dst.native, native).checkError()
    }
}