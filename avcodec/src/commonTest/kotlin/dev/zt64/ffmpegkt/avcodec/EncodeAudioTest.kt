package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import okio.*
import okio.Path.Companion.toPath
import kotlin.math.PI
import kotlin.math.sin
import kotlin.test.Test

class EncodeAudioTest {
    @Test
    fun encodeAudio() {
        AVCodecParserContext(AVCodecID.MP3).use {
            println("Codec: ${it.frameOffset}")
        }
        val codec = AVCodec.findEncoder(AVCodecID.MP3)!!
        val codecContext = AVCodecContext(codec).apply {
            bitRate = 64000
            sampleFmt = AVSampleFormat.S16
            sampleRate = selectSampleRate(codec)
        }

        codecContext.channelLayout = selectChannelLayout(codec)
        codecContext.open(codec)

        val packet = AVPacket()
        val frame = AVFrame().apply {
            nbSamples = codecContext.frameSize
            format = AVPixelFormat(codecContext.sampleFmt.num)
        }
        codecContext.channelLayout.copyTo(frame.channelLayout)

        frame.getBuffer()

        val buffer = Buffer()

        val tincr = 2 * PI * 4.0 / codecContext.sampleRate
        var t = 0.0
        for (i in 0 until 200) {
            frame.makeWritable()

            val samples = frame.data[0]

            for (j in 0 until codecContext.frameSize) {
                samples[2 * j] = (sin(t) * 10000).toInt().toByte()
                for (k in 1 until codecContext.channelLayout.nbChannels) {
                    samples[2 * j + 1] = samples[2 * j]
                }
                t += tincr
            }

            encode(codecContext, frame, packet, buffer)
        }

        encode(codecContext, null, packet, buffer)

        buffer.use {
            FileSystem.SYSTEM.write("output.mp3".toPath()) {
                writeAll(buffer)
            }
        }

        frame.close()
        packet.close()
        codecContext.close()
    }
}

fun checkSampleFmt(codec: AVCodec, sampleFmt: AVSampleFormat): Boolean {
    return sampleFmt in codec.sampleFormats
}

/* just pick the highest supported samplerate */
fun selectSampleRate(codec: AVCodec): Int {
    return codec.supportedSampleRates.max()
}

/* select layout with the highest channel count */
fun selectChannelLayout(codec: AVCodec): AVChannelLayout {
    return codec.channelLayouts.maxBy(AVChannelLayout::nbChannels)
}

private fun encode(
    c: AVCodecContext,
    frame: AVFrame?,
    pkt: AVPacket,
    outputStream: Buffer
) {
    c.sendFrame(frame)

    while (true) {
        try {
            c.receivePacket(pkt)
        } catch (e: Exception) {
            break
        }

        println("Write packet ${pkt.pts} (size=${pkt.size})")
        outputStream.write(pkt.data)
        pkt.close()
    }
}