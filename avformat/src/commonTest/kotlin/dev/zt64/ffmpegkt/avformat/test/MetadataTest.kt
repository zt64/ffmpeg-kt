package dev.zt64.ffmpegkt.avformat.test

import dev.zt64.ffmpegkt.avformat.Container
import dev.zt64.ffmpegkt.test.TestResources
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class MetadataTest {
    @Test
    fun testMetadata() = runTest {
        val container = Container.openInput(TestResources.RESOURCE_1.readBytes())

        container.use { format ->
            println("Metadata:")
            println(format.metadata!!)

            println("Chapters:")
            println(format.chapters.ifEmpty { "None" })

            println("Audio streams:")
            println(format.streams.audio.ifEmpty { "None" })

            println("Video streams:")
            println(format.streams.video.ifEmpty { "None" })
        }
    }
}