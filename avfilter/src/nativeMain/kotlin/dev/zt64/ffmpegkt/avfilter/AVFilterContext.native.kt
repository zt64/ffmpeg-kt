package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.avutil.Frame
import kotlinx.cinterop.ptr

internal actual typealias NativeAVFilterContext = ffmpeg.AVFilterContext

public actual value class AVFilterContext actual constructor(
    @PublishedApi
    internal actual val native: NativeAVFilterContext
) : AutoCloseable {
    public actual val width: Int
        get() = ffmpeg.av_buffersink_get_w(native.ptr)
    public actual val height: Int
        get() = ffmpeg.av_buffersink_get_h(native.ptr)

    public actual fun getFrame(flags: Int): Frame {
        TODO("Not yet implemented")
    }

    actual override fun close() {
        ffmpeg.avfilter_free(native.ptr)
    }

    public actual fun getFrame(): Frame {
        TODO("Not yet implemented")
    }

    public actual fun getSamples(samples: Int): Frame {
        TODO("Not yet implemented")
    }

    public actual fun setFrameSize(size: Int) {
    }
}