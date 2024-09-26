package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avformat.AVFMT
import dev.zt64.ffmpegkt.avformat.AVFormatContext
import dev.zt64.ffmpegkt.avformat.AVIOContext
import dev.zt64.ffmpegkt.avutil.AVMediaType
import dev.zt64.ffmpegkt.avutil.LibAVUtil
import dev.zt64.ffmpegkt.avutil.LogLevel
import dev.zt64.ffmpegkt.avutil.util.FfmpegException
import dev.zt64.ffmpegkt.test.TestResources
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class RemuxTest {
    /**
     * Remuxes a video file to another container format.
     * This is a simple example of using the libavformat API to remux a media file from one container format to another.
     *
     */
    @Test
    fun testRemux() = runTest {
        delay(50)

        LibAVUtil.setLogLevel(LogLevel.VERBOSE)

        autoClose {
            val inputFormat = try {
                AVFormatContext.openInput(TestResources.RESOURCE_1.readBytes())
            } catch (e: Exception) {
                throw IllegalStateException("Failed to open input", e)
            }.using()

            if (!inputFormat.findStreamInfo()) {
                throw IllegalStateException("Failed to find stream info")
            }

            inputFormat.dumpFormat(0, "", false)

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
                outStream.codecParameters = inCodecpar
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