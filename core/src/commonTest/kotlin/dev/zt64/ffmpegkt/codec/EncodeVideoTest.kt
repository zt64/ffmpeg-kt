package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Rational
import dev.zt64.ffmpegkt.avutil.VideoFrame
import dev.zt64.ffmpegkt.container.Container
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import okio.*
import kotlin.test.Test

class EncodeVideoTest {
    private val outputDir = TestUtil.getOutputPath("encoded")

    @Test
    fun encodeVideo() = runTest {
        val frameRate = 25 // 25 frames per second
        val frames = 250 // 10 seconds of video

        val c = VideoEncoder(
            codec = Codec.findEncoder(CodecID.MPEG4)!!,
            bitRate = 50000,
            width = 256,
            height = 256,
            timeBase = Rational(1, frameRate),
            framerate = Rational(frames, 1)
        )
        c.open()

        val frame = VideoFrame(
            width = c.width,
            height = c.height,
            format = c.pixFmt
        )

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

        buffer.use {
            FileSystem.SYSTEM.write(outputDir.resolve("output.mp4")) {
                writeAll(buffer)
            }
        }

        val input = Container.openInput(outputDir.resolve("output.mp4").toString())
    }
}