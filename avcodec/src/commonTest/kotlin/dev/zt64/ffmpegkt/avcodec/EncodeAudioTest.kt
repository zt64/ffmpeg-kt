package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVSampleFormat
import dev.zt64.ffmpegkt.avutil.AudioFrame
import okio.*
import kotlin.math.PI
import kotlin.math.sin
import kotlin.test.Test

class EncodeAudioTest {
    @Test
    fun encodeAudio() {
        val codec = AVCodec.findEncoder(AVCodecID.MP3)!!
        val codecContext = AudioEncoder(codec).apply {
            bitRate = 192000 // 192 kbps
            sampleFmt = AVSampleFormat.S32P // 32-bit signed planar
            sampleRate = codec.supportedSampleRates.max()
            channelLayout = codec.channelLayouts.maxBy { it.nbChannels }
        }

        codecContext.open(codec)

        val frame = AudioFrame().apply {
            nbSamples = codecContext.frameSize
            format = codecContext.sampleFmt
            channelLayout = codecContext.channelLayout
        }

        frame.getBuffer()

        val buffer = Buffer()

        /* Initialize the starting phase for the sine wave */
        var t = 0.0
        /* Calculate the phase increment for generating a 440 Hz sine wave. */
        val tincr = 2 * PI * 440.0 / codecContext.sampleRate
        for (i in 0 until 200) {
            frame.makeWritable()

            /* Loop over the number of samples in the frame */
            for (j in 0 until codecContext.frameSize) {
                /* Generate the sine wave sample value */
                val sampleValue = (sin(t) * Int.MAX_VALUE / 10).toInt()

                /* For each channel, write the sample value to the appropriate plane */
                for (k in 0 until codecContext.channelLayout.nbChannels) {
                    val samples = frame.data[k] // Get the buffer for the k-th channel
                    val index = j * 4 // 4 bytes per 32-bit sample

                    /* Write the 32-bit sample value into the plane for this channel */
                    samples[index] = (sampleValue and 0xFF).toUByte() // Least significant byte
                    samples[index + 1] = (sampleValue shr 8 and 0xFF).toUByte() // Next byte
                    samples[index + 2] = (sampleValue shr 16 and 0xFF).toUByte() // Next byte
                    samples[index + 3] = (sampleValue shr 24 and 0xFF).toUByte() // Most significant byte
                }

                /* Increment the phase for the next sample */
                t += tincr
            }

            encode(codecContext, frame, buffer)
        }
        encode(codecContext, null, buffer)

        // Write the buffer to a file
        buffer.use {
            FileSystem.SYSTEM.write(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("output.mp3")) {
                writeAll(buffer)
            }
        }

        // Close all resources
        frame.close()
        codecContext.close()
    }
}

private fun encode(
    c: AudioEncoder,
    frame: AudioFrame?,
    outputStream: Buffer
) {
    c.sendFrame(frame)

    while (true) {
        c.receivePacket()?.use { packet ->
            println("Write packet (size=${packet.size})")
            outputStream.write(packet.data)
        } ?: break
    }
}