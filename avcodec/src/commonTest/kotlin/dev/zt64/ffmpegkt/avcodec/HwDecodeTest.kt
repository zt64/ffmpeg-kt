package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.hw.AVHWDeviceType
import dev.zt64.ffmpegkt.avutil.hw.HWDeviceContext
import kotlin.test.Test

class HwDecodeTest {
    /**
     * Test hardware accelerated decoding
     */
    @Test
    fun decode() {
        val type = AVHWDeviceType.VAAPI
        val hwCtx = HWDeviceContext(type)
        val packet = Packet()
    }
}