package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avutil.PixelFormat
import kotlin.test.Test
import kotlin.test.assertEquals

class PixelFormatTest {
    @Test
    fun testYuv420p() {
        val pixelFormat = PixelFormat("yuv420p")

        assertEquals(pixelFormat, PixelFormat.Companion.YUV420P)
    }
}