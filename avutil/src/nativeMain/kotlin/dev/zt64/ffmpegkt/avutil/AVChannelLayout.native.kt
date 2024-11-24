package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.av_channel_layout_copy
import kotlinx.cinterop.ptr

public actual typealias NativeAVChannelLayout = ffmpeg.AVChannelLayout

public actual value class AVChannelLayout(public val native: NativeAVChannelLayout) {
    public actual inline val order: AVChannelOrder
        get() = AVChannelOrder(native.order.value.toInt())
    public actual inline val nbChannels: Int
        get() = native.nb_channels

    public actual fun copyTo(dst: AVChannelLayout) {
        av_channel_layout_copy(dst.native.ptr, native.ptr).checkError()
    }

    public actual companion object {
        public actual val STEREO: AVChannelLayout
            get() = TODO()
    }
}