package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AudioFrame
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import kotlin.test.Test
import kotlin.test.fail

class DecodeAudioTest {
    @Test
    fun decodeAudio() {
        decodeAudio("test.mp3")
    }

    private fun decodeAudio(file: String) {
        val pkt = AVPacket()
        val codec = AVCodec.findEncoder(AVCodecID.MP2)!!
        val parser = AVCodecParserContext(AVCodecID.MP2)
        val codecContext = AudioCodecContext(codec)

        codecContext.open(codec)

        val source = try {
            FileSystem.SYSTEM.source(file.toPath())
        } catch (e: Exception) {
            fail("Failed to open file: $file", e)
        }

        val buffer = source.buffer()

        buffer

        while (!buffer.exhausted()) {
            val frame = AudioFrame()
            val h = parser.parse(
                codecContext,
                ByteArray(0),
                IntArray(0),
                ByteArray(0)
            )
        }
    }
}