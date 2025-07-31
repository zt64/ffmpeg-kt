package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.FrameUtil.generateFrames
import dev.zt64.ffmpegkt.container.Container
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import okio.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EncodeVideoTest {
    private val outputDir = TestUtil.getOutputPath("encoded")

    @Test
    fun encodeVideo() = runTest {
        val frameRate = 25
        val frames = 250
        val width = 256
        val height = 256

        val c = VideoEncoder(
            codec = CodecID.H264,
            width = width,
            height = height,
            framerate = frameRate
        )
        c.open()

        val buffer = Buffer()

        c.generateFrames(frames) { packet ->
            println("Write packet (size=${packet.size})")
            buffer.write(packet.data)
        }

        // Flush encoder
        c.encode(null)
        c.close()

        assertTrue(buffer.size > 0, "Encoded buffer should not be empty")

        val outputFile = outputDir.resolve("encoded-video.mp4")
        buffer.use {
            FileSystem.SYSTEM.write(outputFile) {
                writeAll(buffer)
            }
        }

        val input = Container.openInput(outputFile.toString())
        assertEquals(1, input.streams.size)

        val videoStream = input.streams.video.single()
        assertEquals(CodecID.H264, videoStream.codecParameters.codecId)
        assertEquals(width, videoStream.codecParameters.width, "Width should match")
        assertEquals(height, videoStream.codecParameters.height, "Height should match")
    }
}