package dev.zt64.ffmpegkt.swresample

import org.bytedeco.ffmpeg.global.swresample.swr_init
import org.bytedeco.ffmpeg.swresample.SwrContext

public actual class SwrContext(internal val native: SwrContext) {
    public actual fun init() {
        swr_init(native)
    }
}