package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.avutil.toNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.avformat.AVFormatContext
import org.bytedeco.ffmpeg.avformat.AVProbeData
import org.bytedeco.ffmpeg.global.avformat.*
import org.bytedeco.javacpp.BytePointer
import java.nio.file.Path
import kotlin.io.path.absolutePathString

internal typealias NativeAVFormatContext2 = AVFormatContext

public actual abstract class Container(@PublishedApi internal val native: NativeAVFormatContext2) : AutoCloseable {
    public actual open val metadata: Map<String, String>
        get() = native.metadata()?.let { Dictionary(it) }.orEmpty()

    @Suppress("PropertyName")
    @PublishedApi
    internal val _streams: MutableList<Stream> = mutableListOf()

    public actual inline val streams: StreamContainer
        get() = StreamContainer(_streams)

    public actual inline val chapters: List<Chapter>
        get() = List(native.nb_chapters()) {
            Chapter(native.chapters(it))
        }

    public actual fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    ) {
        av_dump_format(native, index, url, if (isOutput) 1 else 0)
    }

    public actual abstract override fun close()

    public actual companion object {
        public actual fun openInput(
            url: String,
            format: AVInputFormat?,
            options: Dictionary?
        ): InputContainer {
            val formatContext = avformat_alloc_context()

            avformat_open_input(
                formatContext,
                url,
                format?.native,
                options?.toNative()
            ).checkError()

            return InputContainer(formatContext)
        }

        public actual fun openInput(
            byteArray: ByteArray,
            format: AVInputFormat?,
            options: Dictionary?
        ): InputContainer {
            val formatContext = avformat_alloc_context()

            val bufferPointer = BytePointer(*byteArray)
            val bufferSize = byteArray.size
            val avioCtx = AVIOContext(byteArray)
            formatContext.pb(avioCtx.native)

            // Probe the input format if not provided
            if (format == null) {
                val probeData = AVProbeData().apply {
                    buf_size(bufferSize.coerceAtMost(4096))
                    filename(BytePointer("stream"))
                    buf(bufferPointer)
                }

                var inputFormat = av_probe_input_format(probeData, 1)
                if (inputFormat == null) {
                    inputFormat = av_probe_input_format(probeData, 0)
                }

                if (inputFormat == null) {
                    throw IllegalArgumentException("Could not determine input format")
                }

                // inputFormat.flags(inputFormat.flags() or AVFMT_NOFILE)
                formatContext.iformat(inputFormat)
            } else {
                formatContext.iformat(format.native)
            }

            avformat_open_input(formatContext, null as String?, formatContext.iformat(), options?.toNative()).checkError()

            return InputContainer(formatContext)
        }

        public fun openInput(
            path: Path,
            format: AVInputFormat? = null,
            options: Dictionary? = null
        ): InputContainer {
            return openInput(path.absolutePathString(), format, options)
        }

        public actual fun openOutput(
            format: AVOutputFormat?,
            formatName: String?,
            filename: String
        ): OutputContainer {
            return OutputContainer(format, formatName, filename)
        }

        public actual fun openOutput(filename: String): OutputContainer {
            return OutputContainer(null, null, filename)
        }

        public fun openOutput(path: Path): OutputContainer {
            return openOutput(path.absolutePathString())
        }
    }
}