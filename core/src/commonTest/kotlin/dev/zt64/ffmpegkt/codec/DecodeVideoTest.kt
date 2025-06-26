package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.VideoFrame
import dev.zt64.ffmpegkt.test.TestResources
import dev.zt64.ffmpegkt.test.TestUtil
import okio.FileSystem
import okio.SYSTEM
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalUnsignedTypes::class)
class DecodeVideoTest {
    private val outputDir = TestUtil.getOutputPath("encoded/frames")

    @Test
    fun decodeVideo() {
        val decoder = VideoDecoder(CodecID.MPEG1VIDEO)
        decoder.open()

        var frameCount = 0

        decoder.parser.parsePackets(TestResources.MPEG_1_VIDEO.readBytes()).forEach { (packet) ->
            println("Parsed packet size: ${packet.size}")

            if (packet.size <= 0) return@forEach

            decoder.decode(packet).forEach { frame ->
                saveFrame(frame, frameCount++)

                frame.close()
            }
        }

        decoder.decode(null)
        decoder.close()

        assertEquals(396, frameCount, "Expected 396 frames, but got $frameCount")
    }

    private fun saveFrame(frame: VideoFrame, frameNum: Int) {
        println("Saving frame $frameNum")

        val buf = frame.data[0].toUByteArray().asByteArray()
        val wrap = frame.linesize[0]

        FileSystem.SYSTEM.write(outputDir.resolve("./frame-$frameNum.pgm")) {
            writeUtf8("P5\n${frame.width} ${frame.height}\n255\n")

            for (i in 0 until frame.height) {
                write(buf, i * wrap, frame.width)
            }
        }
    }
}