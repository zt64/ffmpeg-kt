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
import org.bytedeco.ffmpeg.avformat.AVIOContext
import org.bytedeco.ffmpeg.global.avcodec
import org.bytedeco.ffmpeg.global.avcodec.*
import org.bytedeco.ffmpeg.global.avformat.*
import org.bytedeco.ffmpeg.global.avutil.av_dict_free
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.PointerPointer

public actual class OutputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
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
        filename: String,
        format: OutputFormat?,
        formatName: String?
    ) : this(NativeAVFormatContext2()) {
        avformat_alloc_output_context2(
            native,
            format?.native,
            formatName,
            filename
        ).checkError()
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
            MediaType.AUDIO -> AudioEncoder(codec).apply {
                sampleFmt = SampleFormat(codec.native.sample_fmts().get(0))
                bitrate = 50000
                bitRateTolerance = 32000
                sampleRate = 48000

                avStream.time_base(timeBase.toNative())
            }

            MediaType.VIDEO -> VideoEncoder(codec).apply {
                pixFmt = PixelFormat.YUV420P
                width = 640
                height = 480
                bitrate = 50000
                bitRateTolerance = 128000
                framerate = Rational(25, 1)
                timeBase = Rational(1, 25)

                avStream.avg_frame_rate(framerate.toNative())
                avStream.time_base(timeBase.toNative())
            }

            else -> error("Unsupported codec type: ${codec.type}")
        }
        avStream.time_base(codecContext.timeBase.toNative())

        if ((native.oformat().flags() and AVFMT_GLOBALHEADER) != 0) {
            codecContext.flags = codecContext.flags or avcodec.AV_CODEC_FLAG_GLOBAL_HEADER
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

            if (!ctx.isOpen) ctx.open()

            if (stream.native.time_base() == null) {
                stream.native.time_base(ctx.timeBase.toNative())
            }

            avcodec_parameters_from_context(stream.native.codecpar(), ctx.native).checkError()
        }

        if (native.pb() == null && (native.oformat().flags() and AVFMT_NOFILE) == 0) {
            val pb = PointerPointer<AVIOContext>(1)

            avio_open(pb, BytePointer(native.url().string), AVIO_FLAG_WRITE).checkError()

            val ioContext = pb.get(AVIOContext::class.java)
            native.pb(ioContext)
        }

        avformat_write_header(native, metadata.toNative()).checkError()
    }

    public actual fun mux(packet: Packet, stream: Stream) {
        if (!started) {
            writeHeader()
            started = true
        }

        val nativePacket = packet.toNative()
        try {
            val codecContext = stream.codecContext
                ?: throw IllegalStateException("Stream ${packet.streamIndex} does not have a codec context")

            av_packet_rescale_ts(nativePacket, codecContext.timeBase.toNative(), stream.native.time_base())
            nativePacket.stream_index(stream.index)

            av_interleaved_write_frame(native, nativePacket).checkError()
        } finally {
            av_packet_free(nativePacket)
        }
    }

    public actual fun mux(packets: List<Packet>, stream: Stream) {
        packets.forEach { mux(it, stream) }
    }

    public actual override fun close() {
        if (!started) {
            throw IllegalStateException("Cannot close container without writing header first")
        }

        av_write_trailer(native).checkError()
        if (native.pb() != null) avio_context_free(native.pb())
    }
}