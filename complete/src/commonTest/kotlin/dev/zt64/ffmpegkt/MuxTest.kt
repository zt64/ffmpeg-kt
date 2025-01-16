package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avcodec.*
import dev.zt64.ffmpegkt.avformat.*
import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.swresample.SwrContext
import dev.zt64.ffmpegkt.swscale.SwsContext
import kotlin.test.Test
import kotlin.test.fail

class OutputStream {
    var str: Stream? = null
    var enc: CodecContext? = null
    var nextPts: Long? = null
    var frame: Frame? = null
    var tmpFrame: Frame? = null
    var tmpPkt: Packet? = null
    var t: Float? = null
    var tincr: Float? = null
    var tincr2: Float? = null
    var swsCtx: SwsContext? = null
    var swrCtx: SwrContext? = null
}

class MuxTest {
    /**
     * Mux an audio and video file into a single format
     *
     */
    @Test
    fun mux() {
        val stream = OutputStream()
        val output = AVFormatContext.openOutput("output.mp4")
        val fmt = output.oformat!!

        val videoCodec = AVCodec.findEncoder(AVCodecID.H264)!!
        val audioCodec = AVCodec.findEncoder(AVCodecID.AAC)!!

        if (fmt.videoCodec != AVCodecID.NONE) {
            output.addStream(stream, videoCodec, AVCodecID.H264)
        }

        if (fmt.audioCodec != AVCodecID.NONE) {
            output.addStream(stream, audioCodec, AVCodecID.AAC)
        }

        val options: AVDictionary = mutableMapOf()

        output.dumpFormat(0, "output.mp4", true)

        output.openAudioCodec(audioCodec, options)
        output.openVideoCodec(videoCodec, options)
        if (fmt.flags and 0x0001 == 0) {
            output.pb = AVIOContext("output.mp4", 2)
        }

        output.writeHeader()

        output.writeTrailer()
    }

    fun AVFormatContext.addStream(
        outputStream: OutputStream,
        codec: AVCodec,
        codecId: AVCodecID
    ) {
        val codec = AVCodec.findEncoder(codecId)
            ?: error("Codec not found for ${codec.name}")

        outputStream.tmpPkt = Packet()
        outputStream.str = newStream()
        outputStream.str!!.id = streams.size - 1

        val c = VideoEncoder(codec)
        outputStream.enc = c

        val encoder = when (codec.type) {
            MediaType.VIDEO -> {
                val c = VideoEncoder(codec)

                c.codecId = codecId
                c.bitRate = 400000
                c.width = 352
                c.height = 288

                outputStream.str!!.timeBase = Rational(1, 25)
                c.timeBase = outputStream.str!!.timeBase

                c.gopSize = 12
                c.pixFmt = PixelFormat.YUV420P

                if (c.codecId == AVCodecID.MPEG2VIDEO) {
                    c.maxBFrames = 2
                }

                if (c.codecId == AVCodecID.MPEG1VIDEO) {
                    c.mbDecision = 2
                }

                c
            }

            MediaType.AUDIO -> {
                val c = AudioEncoder(codec)

                c.sampleFmt = codec.sampleFormats.getOrNull(0) ?: SampleFormat.FLTP
                c.bitRate = 64000
                c.sampleRate = 44100

                c.sampleRate = codec.supportedSampleRates.getOrNull(0) ?: 44100
                for (sampleRate in codec.supportedSampleRates) {
                    if (sampleRate == 44100) {
                        c.sampleRate = 44100
                        break
                    }
                }

                c.channelLayout = ChannelLayout.STEREO
                outputStream.str!!.timeBase = Rational(1, c.sampleRate)

                c
            }

            else -> fail("Unexpected type ${codec.type}")
        }

        if (oformat!!.flags and 0x0040 == 1) {
            c.flags = c.flags or 1 shl 22
        }
    }
}