package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.stream.AudioStream
import dev.zt64.ffmpegkt.stream.Stream
import dev.zt64.ffmpegkt.stream.VideoStream
import ffmpeg.AVDictionary
import ffmpeg.AVFormatContext
import kotlinx.cinterop.pointed
import kotlinx.cinterop.reinterpret

internal typealias NativeAVFormatContext2 = AVFormatContext

public actual abstract class Container(@PublishedApi internal val native: NativeAVFormatContext2) : AutoCloseable {
    public actual inline val metadata: Map<String, String>?
        get() = native.metadata?.let { Dictionary(it.reinterpret<AVDictionary>().pointed) }
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