package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avformat.AVFormat
import dev.zt64.ffmpegkt.avformat.findStreamInfo
import dev.zt64.ffmpegkt.avformat.metadata
import dev.zt64.ffmpegkt.avutil.*
import kotlin.test.Test

class MetadataTest {
    @Test
    fun testMetadata() {
        val format = AVFormat.openInput(
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        )

        if (!format.findStreamInfo()) {
            throw IllegalStateException("Failed to find stream info")
        }

        @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
        val dict = format.metadata!!

        var tag: AVDictionaryEntry? = null
        while (true) {
            tag = dict.iterate(tag)
            if (tag == null) break
            println("${tag.tkey} = ${tag.tvalue}")
        }
    }
}