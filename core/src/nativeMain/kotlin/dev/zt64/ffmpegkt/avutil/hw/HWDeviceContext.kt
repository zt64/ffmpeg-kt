package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.avutil.toNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.codec.Codec
import ffmpeg.*
import kotlinx.cinterop.*

public actual class HWDeviceContext actual constructor(
    public actual val deviceType: AVHWDeviceType,
    public actual val device: String?,
    public actual val allowSoftwareFallback: Boolean,
    public actual val options: Dictionary?
) : AutoCloseable {
    public var native: AVBufferRef? = null
        private set
    public var config: HWConfig? = null
        private set

    public actual fun init(codec: Codec) {
        val config = codec.hardwareConfigs.find { config ->
            (config.methods.toUInt() and AV_CODEC_HW_CONFIG_METHOD_HW_DEVICE_CTX) != 0u && config.deviceType.num == deviceType.num
        } ?: throw IllegalArgumentException(
            "No suitable hardware configuration found for codec ${codec.name} and device type $deviceType"
        )

        this.config = config

        native = nativeHeap.alloc<AVBufferRef>()
        av_hwdevice_ctx_create(
            device_ctx = cValuesOf(native!!.ptr),
            type = deviceType.num.toUInt(),
            device = device,
            opts = options?.toNative()?.ptr,
            flags = 0
        ).checkError()
    }

    public actual override fun close() {
        av_buffer_unref(cValuesOf(native!!.ptr))
    }
}