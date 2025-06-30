package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.codec.Codec
import dev.zt64.ffmpegkt.codec.CodecID
import dev.zt64.ffmpegkt.codec.VideoEncoder
import dev.zt64.ffmpegkt.container.Container
import dev.zt64.ffmpegkt.stream.VideoStream
import dev.zt64.ffmpegkt.test.TestResources
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import kotlin.test.*

class ContainerTest {
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
        }

        Container.openInput(path).use { input ->
            val metadata = input.metadata

            values.forEach { (key, value) ->
                assertEquals(value, metadata[key])
            }
        }
    }

    @Test
    fun testAddStreams() = runTest {
        val path = "./build/test-output/streams-test.mp4"

        Container.openOutput(path).use { output ->
            // TODO: Simplify the stream/encoder creation process
            val videoStream = output.newStream<VideoStream>(Codec.findEncoder(CodecID.H264)!!)

            val frameRate = 25 // 25 frames per second
            val durationSeconds = 10
            val frames = frameRate * durationSeconds
            val width = 256
            val height = 256

            val c = VideoEncoder(
                codec = CodecID.H264,
                width = width,
                height = height,
                framerate = frameRate
            )
            c.open()

            videoStream.timeBase = c.timeBase
            val frame = c.createFrame()

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
                        packet.rescaleTimestamp(c.timeBase, videoStream.timeBase)

                        println("Write packet (size=${packet.size})")
                        output.mux(packet)
                    }
                }
            }

            frame.close()

            c.encode(null).forEach { packet ->
                packet.use {
                    packet.rescaleTimestamp(c.timeBase, videoStream.timeBase)
                    output.mux(packet)
                }
            }
            c.close()
        }

        Container.openInput(path).use { input ->
            assertEquals(1, input.streams.size, "Expected 1 stream in the container")
            val videoStream = input.streams.video.firstOrNull()
                ?: fail("video stream should be null at this point")
            assertEquals(CodecID.H264, videoStream.codecParameters.codecId, "Expected H.264 codec")
            assertEquals(256, videoStream.codecParameters.width, "Expected video width to be 256")
            assertEquals(256, videoStream.codecParameters.height, "Expected video height to be 256")
        }
    }

    // Should test that closing a container without writing the header throws an exception
    @Test
    fun testImproperClose() {
        val path = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("invalid_close_test.mp4").toString()

        assertFails {
            Container.openOutput(path).use { output ->
                // Do not write header, just close immediately
            }
        }
    }

    @Test
    fun testOpenInputFromByteArray() = runTest {
        val byteArray = TestResources.RESOURCE_1.readBytes()

        Container.openInput(byteArray).use { input ->
            assertTrue(input.streams.audio.isNotEmpty(), "Expected at least one audio stream")
            assertTrue(input.streams.video.isNotEmpty(), "Expected at least one video stream")
        }
    }
}