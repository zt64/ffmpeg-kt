package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avformat.Chapter
import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.avutil.toNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.stream.AudioStream
import dev.zt64.ffmpegkt.stream.Stream
import dev.zt64.ffmpegkt.stream.VideoStream
import ffmpeg.*
import kotlinx.cinterop.*

internal typealias NativeAVFormatContext2 = AVFormatContext

public actual abstract class Container(@PublishedApi internal val native: NativeAVFormatContext2) : AutoCloseable {
    public actual open val metadata: Map<String, String>
        get() = native.metadata?.let { Dictionary(it.pointed) }.orEmpty()

    @Suppress("PropertyName")
    @PublishedApi
    internal val _streams: MutableList<Stream> = List(native.nb_streams.toInt()) { i ->
        val stream = native.streams!![i]!!.pointed

        when (stream.codecpar!!.pointed.codec_type) {
            AVMEDIA_TYPE_VIDEO -> VideoStream(stream)
            AVMEDIA_TYPE_AUDIO -> AudioStream(stream)
            else -> Stream(stream)
        }
    }.toMutableList()

    public actual inline val streams: StreamContainer
        get() = StreamContainer(_streams)

    public actual inline val chapters: List<Chapter>
        get() = List(native.nb_chapters.toInt()) {
            Chapter(native.chapters!![it]!!.pointed)
        }

    public actual fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    ) {
        av_dump_format(native.ptr, index, url, if (isOutput) 1 else 0)
    }

    public actual abstract override fun close()

    public actual companion object {
        public actual fun openInput(
            url: String,
            format: AVInputFormat?,
            options: Dictionary?
        ): InputContainer {
            val formatContext = avformat_alloc_context()!!

            avformat_open_input(
                cValuesOf(formatContext),
                url,
                format?.native?.ptr,
                cValuesOf(options?.toNative()?.ptr)
            ).checkError()

            return InputContainer(formatContext.pointed)
        }

        public actual fun openInput(
            byteArray: ByteArray,
            format: AVInputFormat?,
            options: Dictionary?
        ): InputContainer {
            val formatContext = avformat_alloc_context()!!.pointed.apply {
                pb = AVIOContext(byteArray).native.ptr
            }

            formatContext.iformat = format?.native?.ptr ?: memScoped {
                val bufferPointer = byteArray.asUByteArray().refTo(0).getPointer(this)

                val probeData = alloc<AVProbeData> {
                    buf_size = byteArray.size.coerceAtMost(4096)
                    filename = "stream".cstr.ptr
                    buf = bufferPointer
                }

                av_probe_input_format(probeData.ptr, 1)
                    ?: av_probe_input_format(probeData.ptr, 0)
                    ?: throw IllegalArgumentException("Could not determine input format")
            }

            avformat_open_input(
                cValuesOf(formatContext.ptr),
                null,
                formatContext.iformat,
                cValuesOf(options?.toNative()?.ptr)
            ).checkError()

            return InputContainer(formatContext)
        }

        public actual fun openOutput(
            format: AVOutputFormat?,
            formatName: String?,
            filename: String
        ): OutputContainer {
            return OutputContainer(filename, format, formatName)
        }

        public actual fun openOutput(filename: String): OutputContainer {
            return OutputContainer(filename)
        }
    }
}