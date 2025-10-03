package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.FrameUtil.generateFrames
import dev.zt64.ffmpegkt.FrameUtil.generateSineWave
import dev.zt64.ffmpegkt.avutil.audio.ChannelLayout
import dev.zt64.ffmpegkt.codec.*
import dev.zt64.ffmpegkt.container.Container
import dev.zt64.ffmpegkt.stream.AudioStream
import dev.zt64.ffmpegkt.stream.VideoStream
import dev.zt64.ffmpegkt.test.TestResources
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import kotlin.test.*

class ContainerTest {
    @Test
    fun testMetadataWrite() = runTest {
        val path = TestUtil.resolvePath("metadata-test.mp4")

        val values = mapOf(
            "title" to "Test Metadata",
            "artist" to "FFmpegKT"
        )

        Container.openOutput(path).use { output ->
            output.metadata.putAll(values)
            val stream = output.newStream<VideoStream>(Codec.findEncoder(CodecID.MPEG4)!!)
            output.mux(Packet(), stream)
        }

        Container.openInput(path).use { input ->
            val metadata = input.metadata

            values.forEach { (key, value) ->
                assertEquals(value, metadata[key])
            }
        }
    }

    // Tests the generation of video and audio streams in a container
    @Test
    fun testGenerateStreams() = runTest {
        val path = TestUtil.resolvePath("streams-test.mp4")

        val frameRate = 25 // 25 frames per second
        val durationSeconds = 60
        val frames = frameRate * durationSeconds
        val width = 256
        val height = 256

        Container.openOutput(path).use { output ->
            // TODO: This kinda sucks, figure out a better way to handle this
            val videoCodec = Codec.findEncoder(CodecID.H264)!!
            val videoStream = output.newStream<VideoStream>(videoCodec)
            val audioCodec = Codec.findEncoder(CodecID.MP3)!!
            val audioStream = output.newStream<AudioStream>(audioCodec)

            val c = VideoEncoder(
                codec = videoCodec,
                width = width,
                height = height,
                framerate = frameRate
            )
            c.open()

            videoStream.timeBase = c.timeBase

            val audioEncoder = AudioEncoder(
                codec = audioCodec,
                sampleRate = 44100,
                channelLayout = ChannelLayout.STEREO,
                bitrate = 128_000
            )
            audioEncoder.open()

            c.generateFrames(frames) { packet ->
                println("Write packet (size=${packet.size})")
                output.mux(packet, videoStream)
            }
            c.encode(null).forEach { packet -> output.mux(packet, videoStream) }
            c.close()

            audioEncoder.generateSineWave(durationSeconds) { packet ->
                println("Write audio packet (size=${packet.size})")
                output.mux(packet, audioStream)
            }
            audioEncoder.encode(null).forEach { packet -> output.mux(packet, audioStream) }
            audioEncoder.close()
        }

        Container.openInput(path).use { input ->
            assertEquals(2, input.streams.size, "Expected 2 streams in the container")
            val videoStream = input.streams.video.firstOrNull() ?: fail("video stream should be null at this point")
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