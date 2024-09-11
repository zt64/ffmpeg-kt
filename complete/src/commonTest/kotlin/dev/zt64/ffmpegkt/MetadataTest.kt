package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avformat.AVFormatContext
import kotlin.test.Test

class MetadataTest {
    @Test
    fun testMetadata() {
        AVFormatContext.openInput(URL).use { format ->
            if (!format.findStreamInfo()) {
                throw IllegalStateException("Failed to find stream info")
            }

            val dict = format.metadata!!

            println(dict)
        }
    }
}