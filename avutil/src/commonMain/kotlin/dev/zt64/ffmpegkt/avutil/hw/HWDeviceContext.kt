package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.AVDictionary

public expect class HWDeviceContext(
    deviceType: AVHWDeviceType,
    device: String? = null,
    allowSoftwareFallback: Boolean = true,
    options: AVDictionary? = null
) : AutoCloseable {
    override fun close()
}