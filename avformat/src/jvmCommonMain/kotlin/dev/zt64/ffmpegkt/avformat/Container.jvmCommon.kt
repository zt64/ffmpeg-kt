package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.*
import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import org.bytedeco.ffmpeg.avformat.AVFormatContext
import org.bytedeco.ffmpeg.avformat.AVProbeData
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avformat.*
import org.bytedeco.ffmpeg.global.avutil.av_dict_free
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.PointerPointer
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

public actual class InputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
    public actual inline var startTime: Long
        get() = native.start_time()
        set(value) {
            native.start_time(value)
        }
    public actual inline var duration: Long
        get() = native.duration()
        set(value) {
            native.duration(value)
        }
    public actual inline var bitRate: Long
        get() = native.bit_rate()
        set(value) {
            native.bit_rate(value)
        }

    init {
        val options: org.bytedeco.ffmpeg.avutil.AVDictionary? = null
        avformat_find_stream_info(ctx, options).checkTrue()
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

        av_seek_frame(native, streamIndex, offset.toLong(), flags).checkError()
    }

    actual override fun close() {
        // avformat_close_input(native) // TODO: Why does this segfault?
    }
}

public actual class OutputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
    private val _metadata = mutableMapOf<String, String>()

    public actual override val metadata: MutableMap<String, String> = object : MutableMap<String, String> by _metadata {
        override fun put(key: String, value: String): String? {
            val previous = _metadata.put(key, value)
            updateNativeMetadata()
            return previous
        }

        override fun putAll(from: Map<out String, String>) {
            _metadata.putAll(from)
            updateNativeMetadata()
        }

        override fun remove(key: String): String? {
            val previous = _metadata.remove(key)
            updateNativeMetadata()
            return previous
        }

        override fun clear() {
            _metadata.clear()
            updateNativeMetadata()
        }
    }

    private fun updateNativeMetadata() {
        // Free any existing metadata dictionary
        if (native.metadata() != null) {
            av_dict_free(native.metadata())
        }

        // Create a new dictionary from current metadata
        if (_metadata.isNotEmpty()) {
            val dict = _metadata.toNative()
            native.metadata(dict)
        } else {
            native.metadata(null)
        }
    }
    public actual constructor(
        format: AVOutputFormat?,
        formatName: String?,
        filename: String
    ) : this(NativeAVFormatContext2()) {
        avformat_alloc_output_context2(native, format?.native, formatName, filename).checkError()
    }

    public actual fun <T : Stream> addStream(stream: T): T {
        val avStream = avformat_new_stream(native, null)
            ?: throw IllegalStateException("Failed to create new stream")

        // Copy codec parameters from the source stream
        avcodec_parameters_copy(avStream.codecpar(), stream.native.codecpar()).checkError()

        @Suppress("UNCHECKED_CAST")
        return when (MediaType(avStream.codecpar().codec_type())) {
            MediaType.AUDIO -> AudioStream(avStream) as T
            MediaType.VIDEO -> VideoStream(avStream) as T
            else -> Stream(avStream, null) as T
        }
    }

    public actual inline fun <reified T : Stream> newStream(codec: Codec, streamIndex: Int): T {
        if (!avformat_query_codec(native.oformat(), codec.id.num, FF_COMPLIANCE_NORMAL).checkTrue()) {
            throw IllegalArgumentException("Codec ${codec.id} is not supported by the output format ${native.oformat().name()}")
        }

        val avStream = avformat_new_stream(native, codec.native)
            ?: throw IllegalStateException("Failed to create new stream")

        val codecContext = when (codec.type) {
            MediaType.AUDIO -> AudioEncoder(codec)
            MediaType.VIDEO -> VideoEncoder(codec)
            else -> CodecContext(codec)
        }

        when (codecContext) {
            is AudioCodecContext -> {
                codecContext.sampleFmt = SampleFormat(codec.native.sample_fmts().get(0))
                codecContext.bitRate = 0
                codecContext.bitRateTolerance = 32000
                codecContext.sampleRate = 48000

                avStream.time_base(codecContext.timeBase.toNative())
            }

            is VideoCodecContext -> {
                codecContext.pixFmt = PixelFormat.YUV420P
                codecContext.width = 640
                codecContext.height = 480
                codecContext.bitRate = 0
                codecContext.bitRateTolerance = 128000

                avStream.time_base(codecContext.timeBase.toNative())
            }

            else -> {
                // For other codecs, we can just use the default context
                avStream.time_base(codecContext.timeBase.toNative())
            }
        }

        if ((native.oformat().flags() and AVFMT_GLOBALHEADER) != 0) {
            codecContext.flags = codecContext.flags or AV_CODEC_FLAG_GLOBAL_HEADER
        }

        avcodec_parameters_from_context(avStream.codecpar(), codecContext.native).checkError()

        val streamObj = when (codec.type) {
            MediaType.AUDIO -> AudioStream(avStream, codecContext)
            MediaType.VIDEO -> VideoStream(avStream, codecContext)
            else -> Stream(avStream, codecContext)
        } as T

        _streams.add(streamObj)
        return streamObj
    }

    public actual fun writeHeader() {
        for (stream in streams) {
            val ctx = stream.codecContext ?: throw IllegalStateException("Stream ${stream.index} does not have a codec context")

            if (!ctx.isOpen) {
                ctx.open()
            }
        }

        if (native.pb() == null && (native.oformat().flags() and AVFMT_NOFILE) == 0) {
            val pb = PointerPointer<org.bytedeco.ffmpeg.avformat.AVIOContext>(1)

            avio_open(pb, BytePointer(native.url().string), AVIO_FLAG_WRITE).checkError()

            val ioContext = pb.get(org.bytedeco.ffmpeg.avformat.AVIOContext::class.java)
            native.pb(ioContext)
        }

        avformat_write_header(native, metadata.toNative()).checkError()
    }

    public actual fun mux(packet: Packet) {
        av_interleaved_write_frame(native, packet.native).checkError()
    }

    public actual fun mux(packets: List<Packet>) {
        packets.forEach { mux(it) }
    }

    actual override fun close() {
        av_write_trailer(native).checkError()
        if (native.pb() != null) avio_context_free(native.pb())

        // TODO: Write trailer and finalize the output container
    }
}