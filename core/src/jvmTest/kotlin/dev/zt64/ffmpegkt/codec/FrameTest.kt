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
    fun decodeVideoFrames() = runTest {
        val decoder = VideoDecoder(CodecID.MPEG1VIDEO)
        decoder.open()

        decoder.parser.parsePackets(TestResources.MPEG_1_VIDEO.readBytes()).forEach { (packet) ->
            println("Parsed packet size: ${packet.size}")
            if (packet.size > 0) {
                try {
                    decoder.decode(packet)
                } catch (e: Exception) {
                    fail("Error sending a packet for decoding", e)
                }

                while (true) {
                    val frame = decoder.decode() ?: break

                    println("Saving frame ${decoder.frameNum}")

                    ImageIO.write(frame.toImage(), "png", File(outputDir.toFile(), "frame_${decoder.frameNum}.png"))
                }
            }
        }

        decoder.decode(null)
        decoder.close()
    }
}