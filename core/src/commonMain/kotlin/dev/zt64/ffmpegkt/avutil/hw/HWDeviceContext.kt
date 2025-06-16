package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.Dictionary

public expect class HWDeviceContext(
    deviceType: AVHWDeviceType,
    device: String? = null,
    allowSoftwareFallback: Boolean = true,
    options: Dictionary? = null
) : AutoCloseable {
    override fun close()
}