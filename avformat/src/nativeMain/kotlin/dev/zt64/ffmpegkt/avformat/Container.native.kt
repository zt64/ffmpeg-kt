package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.Packet
import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import ffmpeg.av_read_frame
import ffmpeg.av_seek_frame
import ffmpeg.avformat_alloc_output_context2
import ffmpeg.avformat_find_stream_info
import ffmpeg.avio_context_free
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret

internal typealias NativeAVFormatContext2 = ffmpeg.AVFormatContext

public actual abstract class Container(@PublishedApi internal val native: NativeAVFormatContext2) : AutoCloseable {
    public actual inline val metadata: Map<String, String>?
        get() = native.metadata?.let { Dictionary(it.reinterpret<ffmpeg.AVDictionary>().pointed) }
    public actual inline val streams: StreamContainer
        get() {
            val streams = List(native.nb_streams.toInt()) {
                val stream = native.streams!![it]
                when (MediaType(stream!!.pointed!!.codecpar!!.codec_type)) {
                    MediaType.AUDIO -> AudioStream(stream)
                    MediaType.VIDEO -> VideoStream(stream)
                    else -> Stream(stream)
                }
            }
            return StreamContainer(streams)
        }
}
// public actual abstract class Container(@PublishedApi internal val native: NativeAVFormatContext2) : AutoCloseable {
//     public actual inline val metadata: Map<String, String>?
//         get() = native.metadata?.let { AVDictionary(it.reinterpret<ffmpeg.AVDictionary>().pointed) }
//
//     public actual inline val streams: StreamContainer
//         get() {
//             val streams = List(native.nb_streams.toInt()) {
//                 val stream = native.streams
//
//                 when (MediaType(stream.codecpar!!.codec_type)) {
//                     MediaType.AUDIO -> AudioStream(stream)
//                     MediaType.VIDEO -> VideoStream(stream)
//                     else -> Stream(stream)
//                 }
//             }
//
//             return StreamContainer(streams)
//         }
//
//     public actual inline val chapters: List<Chapter>
//         get() = List(native.nb_chapters.toInt()) {
//             Chapter(native.chapters(it))
//         }
//
//     public actual fun dumpFormat(
//         index: Int,
//         url: String,
//         isOutput: Boolean
//     ) {
//         av_dump_format(native.ptr, index, url, if (isOutput) 1 else 0)
//     }
//
//     public actual abstract override fun close()
//
//     public actual companion object {
//         public actual fun openInput(
//             url: String,
//             format: AVInputFormat?,
//             options: AVDictionary?
//         ): InputContainer {
//             val formatContext = avformat_alloc_context()
//
//             avformat_open_input(
//                 formatContext,
//                 url,
//                 format?.native,
//                 options?.toNative()
//             ).checkError()
//
//             return InputContainer(formatContext)
//         }
//
//         public actual fun openInput(
//             byteArray: ByteArray,
//             format: AVInputFormat?,
//             options: AVDictionary?
//         ): InputContainer {
//             val formatContext = avformat_alloc_context()
//
//             val bufferPointer = BytePointer(*byteArray)
//             val bufferSize = byteArray.size
//             val avioCtx = AVIOContext(byteArray)
//             formatContext.pb(avioCtx.native)
//
//             // Probe the input format if not provided
//             if (format == null) {
//                 val probeData = AVProbeData().apply {
//                     buf_size(bufferSize.coerceAtMost(4096))
//                     filename(BytePointer("stream"))
//                     buf(bufferPointer)
//                 }
//
//                 var inputFormat = av_probe_input_format(probeData, 1)
//                 if (inputFormat == null) {
//                     inputFormat = av_probe_input_format(probeData, 0)
//                 }
//
//                 if (inputFormat == null) {
//                     throw IllegalArgumentException("Could not determine input format")
//                 }
//
//                 // inputFormat.flags(inputFormat.flags() or AVFMT_NOFILE)
//                 formatContext.iformat = inputFormat
//             } else {
//                 formatContext.iformat = format.native
//             }
//
//             avformat_open_input(formatContext, null as String?, formatContext.iformat, options?.toNative()).checkError()
//
//             return InputContainer(formatContext)
//         }
//
//         public actual fun openOutput(
//             format: AVOutputFormat?,
//             formatName: String?,
//             filename: String
//         ): OutputContainer {
//             return OutputContainer(format, formatName, filename)
//         }
//
//         public actual fun openOutput(filename: String): OutputContainer {
//             return OutputContainer(null, null, filename)
//         }
//     }
// }

public actual class InputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
    public actual inline var startTime: Long
        get() = native.start_time
        set(value) {
            native.start_time = value
        }
    public actual inline var duration: Long
        get() = native.duration
        set(value) {
            native.duration = value
        }
    public actual inline var bitRate: Long
        get() = native.bit_rate
        set(value) {
            native.bit_rate = value
        }

    init {
        val options: Dictionary? = null
        avformat_find_stream_info(ctx, options?.toNative()).checkTrue()
    }

    public actual fun demux(): List<Packet> {
        val packets = mutableListOf<Packet>()
        while (true) {
            val packet = try {
                Packet().apply { av_read_frame(this@InputContainer.native, native) }
            } catch (e: Exception) {
                break
            }

            packets += packet
        }
        return packets
    }

    public actual fun decode(): List<Frame> {
        return demux().map { packet -> packet.decode() }
    }

    public actual fun seek(offset: Int) {
        val streamIndex: Int = -1 // TODO: Expose stream index as parameter?
        val flags = AVSEEK_FLAG_ANY or AVSEEK_FLAG_BACKWARD

        av_seek_frame(native.ptr, streamIndex, offset.toLong(), flags).checkError()
    }

    actual override fun close() {
        // avformat_close_input(native) // TODO: Why does this segfault?
    }
}

public actual class OutputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
    public actual constructor(
        format: AVOutputFormat?,
        formatName: String?,
        filename: String
    ) : this(NativeAVFormatContext2()) {
        avformat_alloc_output_context2(native, format?.native, formatName, filename).checkError()
    }

    public actual fun addStream(stream: Stream) {
    }

    actual override fun close() {
        if (native.pb != null) avio_context_free(native.pb)

        TODO("Not yet implemented")
    }
}