package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import kotlinx.coroutines.test.runTest
import okio.*
import okio.Path.Companion.toPath
import kotlin.test.Test
import kotlin.test.fail

class EncodeVideoTest {
    @Test
    fun encodeVideo() = runTest {
        val frameRate = 25
        val frames = 250 // 10 seconds of video

        val filename = "output.mp4"

        LibAVUtil.setLogLevel(LogLevel.VERBOSE)

        val codec = AVCodec.findEncoder(AVCodecID.MPEG4)!!
        val c = try {
            VideoEncoder(codec).apply {
                bitRate = 50000
                width = 256
                height = 256
                timeBase = Rational(1, frameRate)
                framerate = Rational(frames, 1)
                gopSize = 10
                maxBFrames = 1
                pixFmt = PixelFormat.YUV420P
            }
        } catch (e: Exception) {
            fail("Failed to allocate codec context", e)
        }
        c.open(codec)

        val buffer = Buffer()
        val frame = VideoFrame().apply {
            width = c.width
            height = c.height
            format = c.pixFmt
        }

        frame.getBuffer(0)

        for (i in 0 until frames) {
            frame.makeWritable()

            val frameData = frame.data

            val linesize0 = frame.linesize[0]
            val linesize1 = frame.linesize[1]
            val linesize2 = frame.linesize[2]

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
            encode(c, frame, buffer)
        }

        // Flush encoder
        encode(c, null, buffer)

        // Add sequence end code
        if (codec.id == AVCodecID.MPEG1VIDEO || codec.id == AVCodecID.MPEG2VIDEO) {
            buffer.write(byteArrayOf(0, 0, 1, 0xb7.toByte()))
        }

        c.close()
        frame.close()

        buffer.use {
            FileSystem.SYSTEM.write(filename.toPath()) {
                writeAll(buffer)
            }
        }
    }
}

private fun encode(
    c: VideoEncoder,
    frame: VideoFrame?,
    outputStream: Buffer
) {
    if (frame != null) println("Send frame ${frame.pts}")

    c.sendFrame(frame)

    while (true) {
        c.receivePacket()?.use { packet ->
            println("Write packet (size=${packet.size})")
            outputStream.write(packet.data)
        } ?: break
    }
}