package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.bytesPerSample
import dev.zt64.ffmpegkt.test.TestResources
import okio.Buffer
import okio.FileSystem
import okio.SYSTEM
import kotlin.test.Test

@OptIn(ExperimentalUnsignedTypes::class)
class DecodeAudioTest {
    @Test
    fun decodeAudio() {
        val id = CodecID.MP2
        val codec = Codec.findDecoder(id)!!
        val parser = CodecParserContext(id)
        val codecContext = AudioDecoder(codec)
        codecContext.open()

        val out = Buffer()
        parser.parsePackets(codecContext, TestResources.WAV_AUDIO.readBytes()).forEach { (packet) ->
            // println("Parsed packet size: ${packet.size}")

            if (packet.size <= 0) return@forEach

            codecContext.decode(packet)

            while (true) {
                val frame = codecContext.decode() ?: break

                frame.makeWritable()

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