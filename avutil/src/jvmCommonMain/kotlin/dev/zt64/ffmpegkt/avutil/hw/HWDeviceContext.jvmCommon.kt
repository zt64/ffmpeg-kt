package dev.zt64.ffmpegkt.avutil.hw

import dev.zt64.ffmpegkt.avutil.toNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.avutil.AVBufferRef
import org.bytedeco.ffmpeg.global.avutil

public actual class HWDeviceContext actual constructor(
    deviceType: AVHWDeviceType,
    device: String?,
    allowSoftwareFallback: Boolean,
    options: dev.zt64.ffmpegkt.avutil.AVDictionary?
) : AutoCloseable {
    private val ptr = AVBufferRef()

    init {
        avutil.av_hwdevice_ctx_create(ptr, deviceType.num, device, options?.toNative(), 0).checkError()
    }

    public actual override fun close() {
        avutil.av_buffer_unref(ptr)
    }
}