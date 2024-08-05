package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avutil.av_channel_layout_copy

public actual typealias NativeAVChannelLayout = org.bytedeco.ffmpeg.avutil.AVChannelLayout

@JvmInline
public actual value class AVChannelLayout(public val native: NativeAVChannelLayout) {
    public actual val order: Int
        get() = native.order()

    public actual val nbChannels: Int
        get() = native.nb_channels()

    public actual fun copyTo(dst: AVChannelLayout) {
        av_channel_layout_copy(dst.native, native).checkError()
    }
}