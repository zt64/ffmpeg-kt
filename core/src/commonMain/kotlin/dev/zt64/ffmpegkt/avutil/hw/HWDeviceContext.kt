package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.codec.Codec

public expect class HWDeviceContext public constructor(
    deviceType: AVHWDeviceType,
    device: String? = null,
    allowSoftwareFallback: Boolean = false,
    options: Dictionary? = null
) : AutoCloseable {
    public val deviceType: AVHWDeviceType
    public val device: String?
    public val allowSoftwareFallback: Boolean
    public val options: Dictionary?

    public fun init(codec: Codec)

    override fun close()
}