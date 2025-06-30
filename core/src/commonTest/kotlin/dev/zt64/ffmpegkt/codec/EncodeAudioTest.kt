package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import okio.*
import kotlin.math.PI
import kotlin.math.sin
import kotlin.test.Test

class EncodeAudioTest {
    private val outputDir = TestUtil.getOutputPath("encoded")

    @Test
    fun encodeAudio() = runTest {
        val encoder = AudioEncoder(CodecID.MP3)
        encoder.open()

        val frame = encoder.createFrame()

        val buffer = Buffer()

        /* Initialize the starting phase for the sine wave */
        var t = 0.0
        var freq = 440.0
        val length = 2 // seconds
        for (i in 0 until (encoder.sampleRate * length) / encoder.frameSize) {
            val tincr = 2 * PI * freq / encoder.sampleRate

            /* Loop over the number of samples in the frame */
            for (j in 0 until encoder.frameSize) {
                /* Generate the sine wave sample value */
                val sampleValue = (sin(t) * Int.MAX_VALUE / 10).toInt()

                /* For each channel, write the sample value to the appropriate plane */
                for (k in 0 until encoder.channelLayout.nbChannels) {
                    val samples = frame.data[k] // Get the buffer for the k-th channel
                    val index = j * 4 // 4 bytes per 32-bit sample

                    /* Write the 32-bit sample value into the plane for this channel */
                    samples[index] = (sampleValue and 0xFF).toUByte() // Least significant byte
                    samples[index + 1] = (sampleValue shr 8 and 0xFF).toUByte()
                    samples[index + 2] = (sampleValue shr 16 and 0xFF).toUByte()
                    samples[index + 3] = (sampleValue shr 24 and 0xFF).toUByte() // Most significant byte
                }

                /* Increment the phase for the next sample */
                t += tincr
                freq += 0.001
            }

            encoder.encode(frame).forEach { packet ->
                packet.use {
                    println("Write packet (size=${packet.size})")
                    buffer.write(packet.data)
                }
            }
        }

        encoder.flushBuffers()
        encoder.close()
        frame.close()

        // Write the buffer to a file
        buffer.use {
            FileSystem.SYSTEM.write(outputDir.resolve("output.mp3")) {
                writeAll(buffer)
            }
        }
    }
}