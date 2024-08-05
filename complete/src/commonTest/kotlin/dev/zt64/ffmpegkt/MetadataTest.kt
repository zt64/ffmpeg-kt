package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avformat.AVFormat
import kotlin.test.Test

class MetadataTest {
    @Test
    fun testMetadata() {
        AVFormat.openInput(URL).use { format ->
            if (!format.findStreamInfo()) {
                throw IllegalStateException("Failed to find stream info")
            }

            val dict = format.metadata!!

            println(dict)
        }
    }
}