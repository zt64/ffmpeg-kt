package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodec
import dev.zt64.ffmpegkt.avcodec.Packet
import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.AVClass
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.MediaType
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import ffmpeg.*
import kotlinx.cinterop.*

internal typealias NativeAVFormatContext = ffmpeg.AVFormatContext

public actual class AVFormatContext(
    @PublishedApi
    internal val native: NativeAVFormatContext
) : AutoCloseable {
    public actual constructor(
        format: AVOutputFormat?,
        formatName: String?,
        filename: String
    ) : this(
        nativeHeap.alloc<NativeAVFormatContext> {
            avformat_alloc_output_context2(cValuesOf(ptr), format?.native?.ptr, formatName, filename).checkError()
        }
    )

    public actual inline val avClass: AVClass
        get() = AVClass(native.av_class!!.pointed)
    public actual inline val iformat: AVInputFormat?
        get() = native.iformat?.pointed
    public actual inline val oformat: AVOutputFormat?
        get() = AVOutputFormat(native.oformat!!.pointed)

    public actual inline var pb: AVIOContext?
        get() = native.pb?.pointed?.let(::AVIOContext)
        set(value) {
            native.pb = value?.native?.ptr
        }
    public actual inline var ctxFlags: Int
        get() = native.ctx_flags
        set(value) {
            native.ctx_flags = value
        }
    public actual inline val streams: StreamContainer
        get() {
            val streams = List(native.nb_streams.toInt()) {
                val stream = native.streams!![it]!!.pointed

                when (MediaType(stream.codecpar!!.pointed.codec_type)) {
                    MediaType.AUDIO -> AudioStream(stream)
                    MediaType.VIDEO -> VideoStream(stream)
                    else -> Stream(stream)
                }
            }

            return StreamContainer(streams)
        }

    public actual inline val chapters: List<Chapter>
        get() = emptyList()
    public actual inline val url: String
        get() = native.url!!.toKString()
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
    public actual inline var packetSize: Int
        get() = native.packet_size.toInt()
        set(value) {
            native.packet_size = value.toUInt()
        }
    public actual inline var maxDelay: Int
        get() = native.max_delay
        set(value) {
            native.max_delay = value
        }
    public actual inline var flags: Int
        get() = native.flags
        set(value) {
            native.flags = value
        }
    public actual inline var probesize: Long
        get() = native.probesize
        set(value) {
            native.probesize = value
        }
    public actual inline var maxAnalyzeDuration: Long
        get() = native.max_analyze_duration
        set(value) {
            native.max_analyze_duration = value
        }
    public actual inline var key: UByte
        get() = native.key!!.pointed.value
        set(value) {
            memScoped {
                native.key?.pointed?.value = value
            }
        }
    public actual inline var keylen: Int
        get() = native.keylen
        set(value) {
            native.keylen = value
        }
    public actual inline val programs: List<AVProgram>
        get() = List(native.nb_programs.toInt()) {
            AVProgram(native.programs!![it]!!.pointed)
        }
    public actual inline val videoCodecId: Int
        get() = native.video_codec_id.toInt()
    public actual inline val audioCodecId: Int
        get() = native.audio_codec_id.toInt()
    public actual inline val subtitleCodecId: Int
        get() = native.subtitle_codec_id.toInt()
    public actual inline val dataCodecId: Int
        get() = native.data_codec_id.toInt()
    public actual inline val metadata: Map<String, String>?
        get() {
            return native.metadata?.let { AVDictionary(it.reinterpret<AVDictionaryNative>().pointed) }
        }
    public actual inline val startTimeRealtime: Long
        get() = native.start_time_realtime
    public actual inline val fpsProbeSize: Int
        get() = native.fps_probe_size
    public actual inline val errorRecognition: Int
        get() = native.error_recognition
    public actual inline val interruptCallback: AVIOInterruptCB<*>
        get() = TODO()
    public actual inline val debug: Int
        get() = native.debug
    public actual inline val maxStreams: Int
        get() = native.max_streams
    public actual inline val maxIndexSize: Int
        get() = native.max_index_size.toInt()
    public actual inline val maxPictureBuffer: Int
        get() = native.max_picture_buffer.toInt()
    public actual inline val maxInterleaveDelta: Long
        get() = native.max_interleave_delta
    public actual inline val maxTsProbe: Int
        get() = native.max_ts_probe
    public actual inline val maxChunkDuration: Int
        get() = native.max_chunk_duration
    public actual inline val maxChunkSize: Int
        get() = native.max_chunk_size
    public actual inline val maxProbePackets: Int
        get() = native.max_probe_packets
    public actual inline val strictStdCompliance: Int
        get() = native.strict_std_compliance
    public actual inline val eventFlags: Int
        get() = native.event_flags
    public actual inline val avoidNegativeTs: Int
        get() = native.avoid_negative_ts
    public actual inline val audioPreload: Int
        get() = native.audio_preload
    public actual inline val useWallclockAsTimestamps: Int
        get() = native.use_wallclock_as_timestamps
    public actual inline val skipEstimateDurationFromPts: Int
        get() = native.skip_estimate_duration_from_pts
    public actual inline val avioFlags: Int
        get() = native.avio_flags
    public actual inline val durationEstimationMethod: Int
        get() = native.duration_estimation_method.value.toInt()
    public actual inline val skipInitialBytes: Long
        get() = native.skip_initial_bytes
    public actual inline val correctTsOverflow: Int
        get() = native.correct_ts_overflow.toInt()
    public actual inline val seek2any: Int
        get() = native.seek2any
    public actual inline val flushPackets: Int
        get() = native.flush_packets
    public actual inline val probeScore: Int
        get() = native.probe_score
    public actual inline val formatProbesize: Int
        get() = native.format_probesize

    public actual val codecWhitelist: List<String>
        get() = TODO()
    public actual val formatWhitelist: List<String>
        get() = TODO()
    public actual val protocolWhitelist: List<String>
        get() = TODO()
    public actual val protocolBlacklist: List<String>
        get() = TODO()

    public actual inline val ioRepositioned: Int
        get() = native.io_repositioned

    public actual inline var videoCodec: AVCodec?
        get() = native.video_codec?.pointed?.let(::AVCodec)
        set(value) {
            native.video_codec = value?.native?.ptr
        }
    public actual inline var audioCodec: AVCodec?
        get() = native.audio_codec?.pointed?.let(::AVCodec)
        set(value) {
            native.audio_codec = value?.native?.ptr
        }
    public actual inline var subtitleCodec: AVCodec?
        get() = native.subtitle_codec?.pointed?.let(::AVCodec)
        set(value) {
            native.subtitle_codec = value?.native?.ptr
        }
    public actual inline var dataCodec: AVCodec?
        get() = native.data_codec?.pointed?.let(::AVCodec)
        set(value) {
            native.data_codec = value?.native?.ptr
        }

    public actual inline val metadataHeaderPadding: Int
        get() = native.metadata_header_padding
    public actual inline val outputTsOffset: Long
        get() = native.output_ts_offset
    public actual inline var dumpSeparator: ByteArray
        get() = TODO()
        set(value) {
            memScoped {
                native.dump_separator = value.asUByteArray().toCValues().ptr
            }
        }
    public actual var durationProbeSize: Long
        get() = native.probesize
        set(value) {
            native.probesize = value
        }

    public actual fun findStreamInfo(options: AVDictionary?): Boolean {
        return avformat_find_stream_info(native.ptr, options?.toNative()?.reinterpret()).checkTrue()
    }

    public actual fun findProgramFromStream(last: AVProgram?, streamIndex: Int): AVProgram? {
        return av_find_program_from_stream(
            native.ptr,
            last?.native?.ptr,
            streamIndex
        )?.pointed?.let { AVProgram(it) }
    }

    public actual fun programAddStreamIndex(programId: Int, streamIndex: Int) {
        av_program_add_stream_index(
            native.ptr,
            programId,
            streamIndex.toUInt()
        )
    }

    public actual fun findBestStream(
        type: Int,
        wantedStreamIndex: Int,
        relatedStreamIndex: Int,
        codec: AVCodec,
        flags: Int
    ): Int {
        return av_find_best_stream(
            native.ptr,
            type,
            wantedStreamIndex,
            relatedStreamIndex,
            cValuesOf(codec.native.ptr),
            flags
        )
    }

    public actual fun newStream(): Stream {
        return avformat_new_stream(
            native.ptr,
            null
        )!!.pointed.let { Stream(it) }
    }

    public actual fun seekFrame(
        streamIndex: Int,
        timestamp: Long,
        flags: Int
    ) {
        av_seek_frame(
            native.ptr,
            streamIndex,
            timestamp,
            flags
        )
    }

    public actual fun seekFile(
        streamIndex: Int,
        minTimestamp: Long,
        timestamp: Long,
        maxTimestamp: Long,
        flags: Int
    ) {
        avformat_seek_file(
            native.ptr,
            streamIndex,
            minTimestamp,
            timestamp,
            maxTimestamp,
            flags
        )
    }

    public actual fun flush() {
        avformat_flush(native.ptr)
    }

    public actual fun readFrame(): Packet? {
        val pkt = Packet()
        av_read_frame(native.ptr, pkt.native.ptr).checkError()
        return pkt
    }

    public actual fun readPlay() {
        av_read_play(native.ptr).checkError()
    }

    public actual fun readPause() {
        av_read_pause(native.ptr).checkError()
    }

    public actual fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    ) {
        av_dump_format(native.ptr, index, url, if (isOutput) 1 else 0)
    }

    public actual fun writeHeader(options: AVDictionary?) {
        avformat_write_header(native.ptr, options?.toNative()?.reinterpret()).checkError()
    }

    public actual fun writeTrailer() {
        av_write_trailer(native.ptr).checkError()
    }

    public actual fun writeFrame(packet: Packet) {
        av_write_frame(native.ptr, packet.native.ptr).checkError()
    }

    public actual fun interleavedWriteFrame(packet: Packet) {
        av_interleaved_write_frame(native.ptr, packet.native.ptr).checkError()
    }

    public actual fun guessSampleAspectRatio(stream: Stream, frame: Frame): Rational? {
        return av_guess_sample_aspect_ratio(native.ptr, stream.native.ptr, frame.native.ptr).takeUnless {
            it.useContents { num == 0 && den == 1 }
        }?.placeTo(ArenaBase())?.pointed?.let { Rational(it) }
    }

    public actual fun guessFrameRate(stream: Stream, frame: Frame?): Rational? {
        return av_guess_frame_rate(native.ptr, stream.native.ptr, frame?.native?.ptr).takeUnless {
            it.useContents { num == 0 && den == 1 }
        }?.placeTo(ArenaBase())?.pointed?.let { Rational(it) }
    }

    public override fun close() {
        if (pb != null) avio_context_free(cValuesOf(native.pb))
        // avformat_close_input(native)
    }

    public actual companion object {
        public actual fun openInput(
            url: String,
            format: AVInputFormat?,
            options: AVDictionary?
        ): AVFormatContext {
            val formatContext = avformat_alloc_context()!!

            avformat_open_input(
                cValuesOf(formatContext),
                url,
                format?.readValue(),
                options?.toNative()?.reinterpret()
            ).checkError()

            return AVFormatContext(formatContext.pointed)
        }

        public actual fun openInput(
            byteArray: ByteArray,
            format: AVInputFormat?,
            options: AVDictionary?
        ): AVFormatContext {
            val avioCtx = AVIOContext(byteArray)

            val formatContext = avformat_alloc_context()!!.apply {
                pointed.pb = avioCtx.native.ptr
            }

            memScoped {
                val g = alloc<CPointerVar<ffmpeg.AVFormatContext>> {
                    value = formatContext
                }
            }
            avformat_open_input(cValuesOf(formatContext), null, format?.readValue(), null).checkError()

            return AVFormatContext(formatContext.pointed)
        }

        public actual fun openOutput(
            format: AVOutputFormat?,
            formatName: String?,
            filename: String
        ): AVFormatContext {
            return AVFormatContext(format, formatName, filename)
        }

        public actual fun openOutput(filename: String): AVFormatContext {
            return AVFormatContext(null, null, filename)
        }
    }
}