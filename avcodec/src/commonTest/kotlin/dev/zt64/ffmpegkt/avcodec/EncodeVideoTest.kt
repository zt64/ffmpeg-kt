package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import kotlinx.coroutines.test.runTest
import okio.*
import okio.Path.Companion.toPath
import kotlin.test.Test

expect fun setup()

class EncodeVideoTest {
    @Test
    fun encodeVideo() = runTest {
        val frameRate = 25
        val frames = 250 // 10 seconds of video

        setup()

        val filename = "output.mp4"

        AVUtil.setLogLevel(40)

        val codec = AVCodec.findEncoder(AVCodecID.MPEG4)!!
        val c = AVCodecContext(codec).apply {
            bitRate = 50000
            width = 256
            height = 256
            timeBase = AVRational(1, frameRate)
            framerate = AVRational(frames, 1)
            gopSize = 10
            maxBFrames = 1
            pixFmt = AVPixelFormat.YUV420P
        }
        val pkt = AVPacket()

        c.open(codec)

        val buffer = Buffer()
        val frame = AVFrame().apply {
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
                    frameData[0][y * linesize0 + x] = (x + y + i * 3).toByte()
                }
            }

            // Cb and Cr
            for (y in 0 until c.height / 2) {
                for (x in 0 until c.width / 2) {
                    frameData[1][y * linesize1 + x] = (128 + y + i * 2).toByte()
                    frameData[2][y * linesize2 + x] = (64 + x + i * 5).toByte()
                }
            }
            frame.pts = i.toLong()
            encode(c, frame, pkt, buffer)
        }

        // Flush encoder
        encode(c, null, pkt, buffer)

        // Add sequence end code
        if (codec.id == AVCodecID.MPEG1VIDEO || codec.id == AVCodecID.MPEG2VIDEO) {
            buffer.write(byteArrayOf(0, 0, 1, 0xb7.toByte()))
        }

        c.close()
        frame.close()
        pkt.close()

        buffer.use {
            FileSystem.SYSTEM.write(filename.toPath()) {
                writeAll(buffer)
            }
        }
    }
}

private fun encode(
    c: AVCodecContext,
    frame: AVFrame?,
    pkt: AVPacket,
    outputStream: Buffer
) {
    if (frame != null) println("Send frame ${frame.pts}")

    c.sendFrame(frame)

    while (true) {
        try {
            c.receivePacket(pkt)
        } catch (e: Exception) {
            break
        }

        println("Write packet ${pkt.pts} (size=${pkt.size})")
        outputStream.write(pkt.data)
        pkt.close()
    }
}