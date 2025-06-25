package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.bytesPerSample
import dev.zt64.ffmpegkt.test.TestResources
import kotlinx.coroutines.test.runTest
import okio.Buffer
import okio.FileSystem
import okio.SYSTEM
import kotlin.test.Test

@OptIn(ExperimentalUnsignedTypes::class)
class DecodeAudioTest {
    @Test
    fun decodeAudio() = runTest {
        val codec = Codec.findDecoder(CodecID.MP2)!!
        val codecContext = AudioDecoder(codec)
        codecContext.open()

        val out = Buffer()
        codecContext.parser.parsePackets(TestResources.WAV_AUDIO.readBytes()).forEach { (packet) ->
            println("Parsed packet size: ${packet.size}")

            codecContext.decode(packet)

            while (true) {
                val frame = codecContext.decode() ?: break

                // println("Saving frame ${codecContext.frameNum}")

                val dataSize = codecContext.sampleFmt.bytesPerSample
                for (i in 0..frame.nbSamples) {
                    for (ch in 0..codecContext.channelLayout.nbChannels) {
                        frame.data[0]
                        // out.write(frame.data[ch].toUByteArray().toByteArray())
                    }
                }

                // FileSystem.SYSTEM.write("./frames/frame-${codecContext.frameNum}.pgm".toPath()) {
                //     writeUtf8("P5\n${frame.width} ${frame.height}\n255\n")
                //
                //     for (i in 0 until frame.height) {
                //         write(buf, i * wrap, frame.width)
                //     }
                // }
            }
        }

        FileSystem.SYSTEM.write(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("out.raw")) {
            this.writeAll(out)
        }
    }
}