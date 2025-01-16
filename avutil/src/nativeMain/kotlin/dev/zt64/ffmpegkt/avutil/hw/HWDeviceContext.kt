package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.AVDictionary

public actual class HWDeviceContext actual constructor(
    deviceType: AVHWDeviceType,
    device: String?,
    allowSoftwareFallback: Boolean,
    options: AVDictionary?
) : AutoCloseable {
    public actual override fun close() {
        // ffmpeg.av_hwdevice_ctx_free()
    }
}