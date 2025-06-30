package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.toImage
import dev.zt64.ffmpegkt.test.TestResources
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.Test

class FrameTest {
    private val outputDir = TestUtil.getOutputPath("frames")

    @Test
    fun decodeVideoFrames() = runTest {
        val decoder = VideoDecoder(CodecID.MPEG1VIDEO)
        decoder.open()

        decoder.parser.parsePackets(TestResources.MPEG_1_VIDEO.readBytes()).forEach { (packet) ->
            println("Parsed packet size: ${packet.size}")
            if (packet.size <= 0) return@forEach

            decoder.decode(packet).forEach { frame ->
                println("Saving frame ${decoder.frameNum}")

                ImageIO.write(frame.toImage(), "png", File(outputDir.toFile(), "frame_${decoder.frameNum}.png"))

                frame.close()
            }
        }

        decoder.decode(null)
        decoder.close()
    }
}