package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.toImage
import dev.zt64.ffmpegkt.test.TestResources
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.Test
import kotlin.test.fail

class FrameTest {
    private val outputDir = TestUtil.getOutputPath("frames")

    @Test
    fun encodeVideo() = runTest {
        val codecId = CodecID.MPEG1VIDEO
        val codec = Codec.findDecoder(codecId)!!

        val parser = CodecParserContext(codecId)
        val codecContext = VideoDecoder(codec)
        codecContext.open()

        parser.parsePackets(codecContext, TestResources.MPEG_1_VIDEO.readBytes()).collect { (packet) ->
            println("Parsed packet size: ${packet.size}")
            if (packet.size > 0) {
                try {
                    codecContext.decode(packet)
                } catch (e: Exception) {
                    fail("Error sending a packet for decoding", e)
                }

                while (true) {
                    val frame = codecContext.decode() ?: break

                    println("Saving frame ${codecContext.frameNum}")

                    val buf = frame.data[0].toUByteArray().asByteArray()
                    val wrap = frame.linesize[0]

                    ImageIO.write(frame.toImage(), "png", File(outputDir.toFile(), "frame_${codecContext.frameNum}.png"))
                }
            }
        }

        codecContext.decode(null)
        codecContext.close()
        parser.close()
    }
}