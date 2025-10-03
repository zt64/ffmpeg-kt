package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.audio.bytesPerSample
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
        val decoder = AudioDecoder(CodecID.MP2)
        decoder.open()

        val out = Buffer()
        decoder.parser.parsePackets(TestResources.WAV_AUDIO.readBytes()).forEach { (packet) ->
            println("Parsed packet size: ${packet.size}")

            if (packet.size <= 0) return@forEach

            decoder.decode(packet).forEach { frame ->
                val dataSize = decoder.sampleFmt.bytesPerSample
                for (i in 0..frame.nbSamples) {
                    for (ch in 0..decoder.channelLayout.nbChannels) {
                        frame.data[0]
                        // out.write(frame.data[ch].toUByteArray().toByteArray())
                    }
                }
            }
        }

        FileSystem.SYSTEM.write(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("out.raw")) {
            this.writeAll(out)
        }
    }
}