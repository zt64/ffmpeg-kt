package dev.zt64.ffmpegkt.avcodec

import com.goncalossilva.resources.Resource
import okio.Buffer
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.test.Test
import kotlin.test.fail

class DecodeVideoTest {
    @Test
    fun decodeVideo() {
        // First get the ID
        val codecId = AVCodecID.MPEG1VIDEO
        val codec = AVCodec.findDecoder(codecId)!!

        val parser = CodecParserContext(codecId)
        val codecContext = VideoDecoder(codec)

        codecContext.open(codec)

        val buf = Buffer()
        buf.write(Resource("src/commonTest/resources/sample_640x360.mpeg").readBytes())

        var data = ByteArray(4096 + 64)

        var eof = false
        while (!eof) {
            val buffer = Buffer()
            var dataSize = buf.read(buffer, byteCount = 4096)

            eof = dataSize == -1L
            data = buffer.readByteArray() + ByteArray(64)

            while (dataSize > 0 || eof) {
                val parsedPacket = parser.parse(codecContext, data, dataSize.toInt())
                val packet = parsedPacket.packet

                data += ByteArray(parsedPacket.bytesRead)
                dataSize -= parsedPacket.bytesRead

                when {
                    packet.size > 0 -> codecContext.decode(packet)
                    eof -> break
                }
            }
        }

        // flush
        codecContext.decode(null)

        codecContext.close()
        parser.close()
        buf.close()
    }

    private fun VideoDecoder.decode(packet: Packet?) {
        try {
            sendPacket(packet)
        } catch (e: Exception) {
            fail("Error sending a packet for decoding", e)
        }

        FileSystem.SYSTEM.createDirectory("./frames".toPath())

        while (true) {
            val frame = receiveFrame() ?: break

            println("saving frame $frameNum")

            val buf = frame.data[0].toUByteArray().asByteArray()
            val wrap = frame.linesize[0]

            FileSystem.SYSTEM.write("./frames/frame-$frameNum.pgm".toPath()) {
                writeUtf8("P5\n${frame.width} ${frame.height}\n255\n")

                for (i in 0 until frame.height) {
                    write(buf, i * wrap, frame.width)
                }
            }
        }
    }
}