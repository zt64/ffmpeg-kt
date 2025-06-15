package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.hw.HWDeviceContext
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avfilter.*

internal actual typealias NativeAVFilterContext = org.bytedeco.ffmpeg.avfilter.AVFilterContext

@JvmInline
public actual value class AVFilterContext(
    @PublishedApi
    internal actual val native: NativeAVFilterContext
) : AutoCloseable {
    public actual val name: String
        get() = native.name().string
    public actual val width: Int
        get() = av_buffersink_get_w(native)
    public actual val height: Int
        get() = av_buffersink_get_h(native)
    public actual val filter: AVFilter
        get() = native.filter()
    public actual val graph: AVFilterGraph
        get() = TODO("Not yet implemented")
    public actual val inputPads: List<AVFilterPad>
        get() = TODO("Not yet implemented")
    public actual val inputs: List<AVFilterLink>
        get() = TODO("Not yet implemented")
    public actual val outputs: List<AVFilterLink>
        get() = TODO("Not yet implemented")
    public actual var hwDeviceCtx: HWDeviceContext?
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var extraHwFrames: Int
        get() = native.extra_hw_frames()
        set(value) {
            native.extra_hw_frames(value)
        }
    public actual var threads: Int
        get() = native.nb_threads()
        set(value) {
            native.nb_threads(value)
        }
    public actual var threadType: Int
        get() = TODO("Not yet implemented")
        set(value) {}

    public actual fun getFrame(): Frame {
        return Frame().also {
            av_buffersink_get_frame(
                native,
                it.native
            ).checkError()
        }
    }

    public actual fun getFrame(flags: Int): Frame {
        return Frame().also {
            av_buffersink_get_frame_flags(
                native,
                it.native,
                flags
            ).checkError()
        }
    }

    public actual fun getSamples(samples: Int): Frame {
        return Frame().also {
            av_buffersink_get_samples(native, it.native, samples).checkError()
        }
    }

    public actual fun setFrameSize(size: Int) {
        av_buffersink_set_frame_size(native, size)
    }

    actual override fun close() {
        avfilter_free(native)
    }
}