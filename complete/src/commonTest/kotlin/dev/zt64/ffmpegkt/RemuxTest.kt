package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avformat.AVFMT
import dev.zt64.ffmpegkt.avformat.AVFormatContext
import dev.zt64.ffmpegkt.avformat.AVIOContext
import dev.zt64.ffmpegkt.avutil.AVMediaType
import dev.zt64.ffmpegkt.avutil.AVUtil
import dev.zt64.ffmpegkt.avutil.LogLevel
import dev.zt64.ffmpegkt.avutil.util.FfmpegException
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

const val URL =
    "/mnt/Backup/code/ffmpeg-kt/complete/src/commonTest/resources/SampleVideo_1280x720_5mb.mp4"

class RemuxTest {
    @Test
    fun testRemux() = runTest {
        setProperty()
        delay(50)

        AVUtil.setLogLevel(LogLevel.VERBOSE)

        autoClose {
            val inputFormat = try {
                AVFormatContext.openInput(URL)
            } catch (e: Exception) {
                throw IllegalStateException("Failed to open input", e)
            }.using()

            if (!inputFormat.findStreamInfo()) {
                throw IllegalStateException("Failed to find stream info")
            }

            inputFormat.dumpFormat(0, URL, false)

            val OUTPUT_FILE = "output.mp4"

            val outputFormat = try {
                AVFormatContext.openOutput(OUTPUT_FILE)
            } catch (e: Exception) {
                throw IllegalStateException("Failed to allocate output context", e)
            }.using()

            val streamMapping = IntArray(inputFormat.streams.size)
            var streamIndex = 0

            inputFormat.streams.forEachIndexed { i, inStream ->
                val inCodecpar = inStream.codecParameters

                if (inCodecpar.codecType != AVMediaType.AUDIO &&
                    inCodecpar.codecType != AVMediaType.VIDEO &&
                    inCodecpar.codecType != AVMediaType.SUBTITLE
                ) {
                    streamMapping[i] = -1
                    return@forEachIndexed
                }

                streamMapping[i] = streamIndex++

                val outStream = outputFormat.newStream()
                inCodecpar.copyTo(outStream.codecParameters)
                outStream.codecParameters.codecTag = 0
            }

            outputFormat.dumpFormat(0, OUTPUT_FILE, true)

            if (outputFormat.oformat!!.flags and AVFMT.NO_FILE == 0) {
                outputFormat.pb = AVIOContext(OUTPUT_FILE, 2)
            }

            outputFormat.writeHeader()

            while (true) {
                val packet = inputFormat.readFrame() ?: break

                val inStream = inputFormat.streams[packet.streamIndex]

                if (packet.streamIndex >= streamMapping.size ||
                    streamMapping[packet.streamIndex] < 0
                ) {
                    packet.close()
                    continue
                }

                packet.streamIndex = streamMapping[packet.streamIndex]
                val outStream = outputFormat.streams[packet.streamIndex]

                packet.rescaleTs(inStream.timeBase, outStream.timeBase)
                packet.pos = -1

                try {
                    outputFormat.interleavedWriteFrame(packet)
                } catch (e: FfmpegException) {
                    throw IllegalStateException("Error muxing packet", e)
                }
            }

            outputFormat.writeTrailer()
        }
    }
}

expect fun setProperty()

class UsingScope : AutoCloseable {
    private val closeables: MutableList<AutoCloseable> = mutableListOf()

    fun <T : AutoCloseable> T.using(): T {
        closeables += this
        return this
    }

    override fun close() {
        // closeables.forEach { it.close() }
    }
}

fun autoClose(block: UsingScope.() -> Unit) {
    UsingScope().use(block)
}