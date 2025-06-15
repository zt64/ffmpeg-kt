package dev.zt64.ffmpegkt.avformat.test

import dev.zt64.ffmpegkt.avcodec.Codec
import dev.zt64.ffmpegkt.avcodec.CodecID
import dev.zt64.ffmpegkt.avformat.Container
import dev.zt64.ffmpegkt.avformat.VideoStream
import dev.zt64.ffmpegkt.test.TestResources
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MetadataTest {
    @Test
    fun testMetadata() = runTest {
        val container = Container.openInput(TestResources.RESOURCE_1.readBytes())

        container.use { format ->
            println("Metadata:")
            println(format.metadata)

            println("Chapters:")
            println(format.chapters.ifEmpty { "None" })

            println("Audio streams:")
            println(format.streams.audio.ifEmpty { "None" })

            println("Video streams:")
            println(format.streams.video.ifEmpty { "None" })
        }
    }

    @Test
    fun testMetadataWrite() = runTest {
        val path = "./build/test-output/metadata_test.mp4"

        val values = mapOf(
            "title" to "Test Metadata",
            "artist" to "FFmpegKT"
        )

        Container.openOutput(path).use { output ->
            output.metadata.putAll(values)
            output.newStream<VideoStream>(Codec.findEncoder(CodecID.MPEG4)!!)
            output.writeHeader()
        }

        Container.openInput(path).use { input ->
            val metadata = input.metadata

            values.forEach { (key, value) ->
                assertEquals(value, metadata[key])
            }
        }
    }
}