package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.VideoFrame
import dev.zt64.ffmpegkt.test.TestResources
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.SYSTEM
import kotlin.test.Test

@OptIn(ExperimentalUnsignedTypes::class)
class DecodeVideoTest {
    private val outputDir = TestUtil.getOutputPath("encoded/frames")

    @Test
    fun decodeVideo() = runTest {
        val codecId = CodecID.MPEG1VIDEO
        val codec = Codec.findDecoder(codecId)!!

        val codecContext = VideoDecoder(codec)
        codecContext.open()

        var frameCount = 0

        codecContext.parser.parsePackets(TestResources.MPEG_1_VIDEO.readBytes()).forEach { (packet) ->
            println("Parsed packet size: ${packet.size}")

            if (packet.size <= 0) return@forEach

            codecContext.decode(packet).forEach { frame ->
                saveFrame(frame, frameCount++)

                frame.close()
            }
        }

        codecContext.decode(null)
        codecContext.close()
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