package dev.zt64.ffmpegkt.codec

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
        val frameRate = 25 // 25 frames per second
        val frames = 250 // 10 seconds of video
        val width = 256
        val height = 256

        val c = VideoEncoder(
            codec = CodecID.H264,
            width = width,
            height = height,
            framerate = frameRate
        )
        c.open()

        val frame = c.createFrame()

        val buffer = Buffer()
        for (i in 0 until frames) {
            val frameData = frame.data

            val (linesize0, linesize1, linesize2) = frame.linesize

            // Y
            for (y in 0 until c.height) {
                for (x in 0 until c.width) {
                    frameData[0][y * linesize0 + x] = (x + y + i * 3).toUByte()
                }
            }

            // Cb and Cr
            for (y in 0 until c.height / 2) {
                for (x in 0 until c.width / 2) {
                    frameData[1][y * linesize1 + x] = (128 + y + i * 2).toUByte()
                    frameData[2][y * linesize2 + x] = (64 + x + i * 5).toUByte()
                }
            }
            frame.pts = i.toLong()

            println("Send frame ${frame.pts}")

            c.encode(frame).forEach { packet ->
                packet.use {
                    println("Write packet (size=${packet.size})")
                    buffer.write(packet.data)
                }
            }
        }

        frame.close()

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

        val videoStream = input.streams.video.first()
        assertEquals(CodecID.H264, videoStream.codecParameters.codecId)

        assertEquals(width, videoStream.codecParameters.width, "Width should match")
        assertEquals(height, videoStream.codecParameters.height, "Height should match")
    }
}