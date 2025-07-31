package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.avutil.toNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.codec.Codec
import org.bytedeco.ffmpeg.avutil.AVBufferRef
import org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_HW_CONFIG_METHOD_HW_DEVICE_CTX
import org.bytedeco.ffmpeg.global.avutil.av_buffer_unref
import org.bytedeco.ffmpeg.global.avutil.av_hwdevice_ctx_create
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.PointerPointer

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
            (config.methods and AV_CODEC_HW_CONFIG_METHOD_HW_DEVICE_CTX) != 0 && config.deviceType.num == deviceType.num
        } ?: throw IllegalArgumentException(
            "No suitable hardware configuration found for codec ${codec.name} and device type $deviceType"
        )

        this.config = config

        val hwDeviceCtxPtr = PointerPointer<AVBufferRef>(1L)
        val devicePtr = device?.let { BytePointer(it) }
        av_hwdevice_ctx_create(hwDeviceCtxPtr, deviceType.num, devicePtr, options?.toNative(), 0).checkError()
        native = hwDeviceCtxPtr.get(AVBufferRef::class.java)
        hwDeviceCtxPtr.deallocate()
        devicePtr?.deallocate()
    }

    public actual override fun close() {
        av_buffer_unref(native)
    }
}