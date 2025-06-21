package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.test.TestResources
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.SYSTEM
import kotlin.test.Test
import kotlin.test.fail

@OptIn(ExperimentalUnsignedTypes::class)
class DecodeVideoTest {
    private val outputDir = TestUtil.getOutputPath("encoded/frames")

    @Test
    fun decodeVideo() {
        val codecId = CodecID.MPEG1VIDEO
        val codec = Codec.findDecoder(codecId)!!

        val parser = CodecParserContext(codecId)
        val codecContext = VideoDecoder(codec)
        codecContext.open()

        parser.parsePackets(codecContext, TestResources.MPEG_1_VIDEO.readBytes()).forEach { (packet) ->
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

                    FileSystem.SYSTEM.write(outputDir.resolve("./frame-${codecContext.frameNum}.pgm")) {
                        writeUtf8("P5\n${frame.width} ${frame.height}\n255\n")

                        for (i in 0 until frame.height) {
                            write(buf, i * wrap, frame.width)
                        }
                    }
                }
            }
        }

        codecContext.decode(null)
        codecContext.close()
        parser.close()
    }
}