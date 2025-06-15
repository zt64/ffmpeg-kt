package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.Dictionary

public actual class HWDeviceContext actual constructor(
    deviceType: AVHWDeviceType,
    device: String?,
    allowSoftwareFallback: Boolean,
    options: Dictionary?
) : AutoCloseable {
    public actual override fun close() {
        // ffmpeg.av_hwdevice_ctx_free()
    }
}