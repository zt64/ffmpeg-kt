package dev.zt64.ffmpegkt.avutil.hw

import kotlin.jvm.JvmInline

@JvmInline
public value class AVHWDeviceType @PublishedApi internal constructor(
    @PublishedApi
    internal val num: Int
) {
    public companion object {
        public val NONE: AVHWDeviceType = AVHWDeviceType(0)
        public val VDPAU: AVHWDeviceType = AVHWDeviceType(1)
        public val CUDA: AVHWDeviceType = AVHWDeviceType(2)
        public val VAAPI: AVHWDeviceType = AVHWDeviceType(3)
        public val DXVA2: AVHWDeviceType = AVHWDeviceType(4)
        public val QSV: AVHWDeviceType = AVHWDeviceType(5)
        public val VIDEOTOOLBOX: AVHWDeviceType = AVHWDeviceType(6)
        public val D3D11VA: AVHWDeviceType = AVHWDeviceType(7)
        public val DRM: AVHWDeviceType = AVHWDeviceType(8)
        public val OPENCL: AVHWDeviceType = AVHWDeviceType(9)
        public val MEDIACODEC: AVHWDeviceType = AVHWDeviceType(10)
        public val VULKAN: AVHWDeviceType = AVHWDeviceType(11)
        public val D3D12VA: AVHWDeviceType = AVHWDeviceType(12)

        public val all: Array<AVHWDeviceType> by lazy {
            arrayOf(
                NONE,
                VDPAU,
                CUDA,
                VAAPI,
                DXVA2,
                QSV,
                VIDEOTOOLBOX,
                D3D11VA,
                DRM,
                OPENCL,
                MEDIACODEC,
                VULKAN,
                D3D12VA
            )
        }
    }
}

public expect val AVHWDeviceType.name: String?

public expect fun AVHWDeviceType.Companion.findTypeByName(name: String): AVHWDeviceType?