package dev.zt64.ffmpegkt.avutil.audio

import dev.zt64.ffmpegkt.avutil.audio.ChannelOrder
import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.AVChannelLayout
import ffmpeg.av_channel_layout_copy
import ffmpeg.av_channel_layout_default
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr

public actual typealias NativeAVChannelLayout = AVChannelLayout

public actual value class ChannelLayout(public val native: NativeAVChannelLayout) {
    public actual inline val order: ChannelOrder
        get() = ChannelOrder(native.order.value.toInt())
    public actual inline val nbChannels: Int
        get() = native.nb_channels

    public actual constructor(channels: Int) : this(
        memScoped {
            val layout = alloc<NativeAVChannelLayout>()
            av_channel_layout_default(layout.ptr, channels)
            layout
        }
    )

    public actual fun copyTo(dst: ChannelLayout) {
        av_channel_layout_copy(dst.native.ptr, native.ptr).checkError()
    }

    public actual companion object {
        public actual val STEREO: ChannelLayout
            get() = TODO()
    }
}