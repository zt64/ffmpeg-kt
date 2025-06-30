package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.avutil.AVChannelLayout
import org.bytedeco.ffmpeg.global.avutil.*

internal actual typealias NativeAVChannelLayout = AVChannelLayout

@JvmInline
public actual value class ChannelLayout(public val native: NativeAVChannelLayout) {
    public actual inline val order: AVChannelOrder
        get() = AVChannelOrder(native.order())

    public actual inline val nbChannels: Int
        get() = native.nb_channels()

    public actual constructor(channels: Int) : this(
        AVChannelLayout().apply {
            av_channel_layout_default(this, channels)
        }
    )

    public actual fun copyTo(dst: ChannelLayout) {
        av_channel_layout_copy(dst.native, native).checkError()
    }

    public actual companion object {
        public actual val STEREO: ChannelLayout = ChannelLayout(AV_CHANNEL_LAYOUT_STEREO)
    }
}