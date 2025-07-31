package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.PixelFormat

/**
 * Hardware configuration
 */
public data class HWConfig(
    val pixelFormat: PixelFormat,
    val methods: Int,
    val deviceType: AVHWDeviceType
)