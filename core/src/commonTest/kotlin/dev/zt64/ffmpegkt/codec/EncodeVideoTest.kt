package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.PixelFormat
import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.VideoFrame
import kotlinx.coroutines.test.runTest
import okio.*
import okio.Path.Companion.toPath
import kotlin.test.Test

class EncodeVideoTest {
    private val outputDir = "./build/test-output/encoded".toPath().apply {
        FileSystem.SYSTEM.deleteRecursively(this) // Clean up any previous test runs
        FileSystem.SYSTEM.createDirectories(this)
    }

    @Test
    fun encodeVideo() = runTest {
        val frameRate = 25 // 25 frames per second
        val frames = 250 // 10 seconds of video

        val codec = Codec.findEncoder(CodecID.MPEG4)!!

        val c = VideoEncoder(
            codec = codec,
            bitRate = 50000,
            width = 256,
            height = 256,
            timeBase = Rational(1, frameRate),
            framerate = Rational(frames, 1),
            gopSize = 10,
            maxBFrames = 1,
            pixFmt = PixelFormat.YUV420P
        )
        c.open()

        val buffer = Buffer()
        val frame = VideoFrame(
            width = c.width,
            height = c.height,
            format = c.pixFmt
        )

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

        buffer.use {
            FileSystem.SYSTEM.write(outputDir.resolve("output.mp4")) {
                writeAll(buffer)
            }
        }
    }
}