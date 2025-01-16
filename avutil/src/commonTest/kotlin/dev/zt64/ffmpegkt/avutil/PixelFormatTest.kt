package dev.zt64.ffmpegkt.avutil

import kotlin.test.Test
import kotlin.test.assertEquals

class PixelFormatTest {
    @Test
    fun testYuv420p() {
        val pixelFormat = PixelFormat("yuv420p")

        assertEquals(pixelFormat, PixelFormat.YUV420P)
    }
}