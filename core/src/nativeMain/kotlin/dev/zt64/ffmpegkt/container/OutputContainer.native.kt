package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.audio.SampleFormat
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import dev.zt64.ffmpegkt.avutil.video.PixelFormat
import dev.zt64.ffmpegkt.codec.*
import dev.zt64.ffmpegkt.stream.AudioStream
import dev.zt64.ffmpegkt.stream.Stream
import dev.zt64.ffmpegkt.stream.VideoStream
import ffmpeg.*
import kotlinx.cinterop.*

public actual class OutputContainer(native: NativeAVFormatContext2) : Container(native) {
    private var started: Boolean = false
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
        if (native.metadata != null) av_dict_free(cValuesOf(native.metadata))

        native.metadata = _metadata.takeIf { it.isNotEmpty() }?.toNative()?.ptr
    }

    public actual constructor(
        filename: String,
        format: OutputFormat?,
        formatName: String?
    ) : this(
        memScoped {
            val ptr = alloc<CPointerVar<AVFormatContext>>()
            avformat_alloc_output_context2(
                ctx = ptr.ptr,
                oformat = format?.native?.ptr,
                format_name = formatName,
                filename = filename
            ).checkError()
            ptr.value!!.pointed
        }
    )

    public actual fun <T : Stream> addStream(stream: T): T {
        val avStream = avformat_new_stream(native.ptr, null)?.pointed
            ?: throw IllegalStateException("Failed to create new stream")

        // Copy codec parameters from the source stream
        avcodec_parameters_copy(avStream.codecpar, stream.native.codecpar).checkError()

        @Suppress("UNCHECKED_CAST")
        return when (MediaType(avStream.codecpar!!.pointed.codec_type)) {
            MediaType.AUDIO -> AudioStream(avStream) as T
            MediaType.VIDEO -> VideoStream(avStream) as T
            else -> Stream(avStream, null) as T
        }
    }

    public actual inline fun <reified T : Stream> newStream(codec: Codec, streamIndex: Int): T {
        if (!avformat_query_codec(native.oformat, codec.id.num.toUInt(), FF_COMPLIANCE_NORMAL).checkTrue()) {
            throw IllegalArgumentException("Codec ${codec.id} is not supported by the output format ${native.oformat?.pointed?.name}")
        }

        val avStream = avformat_new_stream(native.ptr, codec.native.ptr)?.pointed
            ?: throw IllegalStateException("Failed to create new stream")

        val codecContext = when (codec.type) {
            MediaType.AUDIO -> AudioEncoder(codec).apply {
                sampleFmt = SampleFormat(codec.native.sample_fmts!![0])
                bitrate = 50000
                bitRateTolerance = 32000
                sampleRate = 48000
            }

            MediaType.VIDEO -> VideoEncoder(codec).apply {
                pixFmt = PixelFormat.YUV420P
                width = 640
                height = 480
                bitrate = 50000
                bitRateTolerance = 128000
                framerate = Rational(30, 1)

                avStream.avg_frame_rate.apply {
                    num = framerate.num
                    den = framerate.den
                }
            }

            else -> error("Unsupported codec type: ${codec.type}")
        }

        avStream.time_base.apply {
            num = codecContext.timeBase.num
            den = codecContext.timeBase.den
        }

        if ((native.oformat!!.pointed.flags and AVFMT_GLOBALHEADER) != 0) {
            codecContext.flags = codecContext.flags or AV_CODEC_FLAG_GLOBAL_HEADER
        }

        avcodec_parameters_from_context(avStream.codecpar, codecContext.native.ptr).checkError()

        val streamObj = when (codec.type) {
            MediaType.AUDIO -> AudioStream(avStream, codecContext)
            MediaType.VIDEO -> VideoStream(avStream, codecContext)
            else -> Stream(avStream, codecContext)
        } as T

        _streams.add(streamObj)
        return streamObj
    }

    public actual fun writeHeader() {
        avformat_write_header(native.ptr, null).checkError()
    }

    public actual fun mux(packet: Packet, stream: Stream) {
        require(packet.streamIndex >= 0) { "Packet must have a valid stream index" }

        if (!started) {
            writeHeader()
            started = true
        }

        val nativePacket = packet.toNative()
        try {
            val codecContext = stream.codecContext
                ?: throw IllegalStateException("Stream ${packet.streamIndex} does not have a codec context")

            // Rescale packet timestamps from the codec time base to the stream time base
            av_packet_rescale_ts(
                nativePacket.ptr,
                codecContext.timeBase.asNative().readValue(),
                stream.native.time_base.readValue()
            )
            nativePacket.stream_index = stream.index

            av_interleaved_write_frame(native.ptr, nativePacket.ptr).checkError()
        } finally {
            av_packet_free(cValuesOf(nativePacket.ptr))
        }
    }

    public actual fun mux(packets: List<Packet>, stream: Stream) {
        packets.forEach { mux(it, stream) }
    }

    public actual override fun close() {
        if (native.pb != null) avio_context_free(cValuesOf(native.pb))

        TODO("Not yet implemented")
    }
}