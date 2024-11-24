package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avutil.AV_CHANNEL_LAYOUT_STEREO
import org.bytedeco.ffmpeg.global.avutil.av_channel_layout_copy

internal actual typealias NativeAVChannelLayout = org.bytedeco.ffmpeg.avutil.AVChannelLayout

@JvmInline
public actual value class AVChannelLayout(public val native: NativeAVChannelLayout) {
    public actual inline val order: AVChannelOrder
        get() = AVChannelOrder(native.order())

    public actual inline val nbChannels: Int
        get() = native.nb_channels()

    public actual fun copyTo(dst: AVChannelLayout) {
        av_channel_layout_copy(dst.native, native).checkError()
    }

    public actual companion object {
        public actual val STEREO: AVChannelLayout = AVChannelLayout(AV_CHANNEL_LAYOUT_STEREO)
    }
}