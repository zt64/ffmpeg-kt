@file:Suppress("NOTHING_TO_INLINE")

package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodec
import dev.zt64.ffmpegkt.avcodec.AVPacket
import dev.zt64.ffmpegkt.avcodec.NativeAVPacket
import dev.zt64.ffmpegkt.avutil.AVClass
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.AVDictionaryNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import org.bytedeco.ffmpeg.global.avformat.*
import org.bytedeco.javacpp.BytePointer

internal typealias NativeAVFormatContext = org.bytedeco.ffmpeg.avformat.AVFormatContext

public actual class AVFormatContext(internal val native: NativeAVFormatContext) : AutoCloseable {
    public actual constructor(
        format: AVOutputFormat?,
        formatName: String?,
        filename: String
    ) : this(
        NativeAVFormatContext()
    ) {
        avformat_alloc_output_context2(native, format?.native, formatName, filename).checkError()
    }

    public actual val avClass: AVClass = AVClass(native.av_class())
    public actual val iformat: AVInputFormat? = native.iformat()?.let(::AVInputFormat)
    public actual val oformat: AVOutputFormat? = native.oformat()?.let(::AVOutputFormat)
    public actual var pb: AVIOContext?
        get() = native.pb()?.let(::AVIOContext)
        set(value) {
            native.pb(value?.native)
        }
    public actual var ctxFlags: Int
        get() = native.ctx_flags()
        set(value) {
            native.ctx_flags(value)
        }
    public actual val streams: List<AVStream>
        get() = List(native.nb_streams()) {
            AVStream(native.streams(it))
        }

    public actual val chapters: List<AVChapter> = emptyList()
    public actual val url: String = native.url().string
    public actual var startTime: Long
        get() = native.start_time()
        set(value) {
            native.start_time(value)
        }
    public actual var duration: Long
        get() = native.duration()
        set(value) {
            native.duration(value)
        }
    public actual var bitRate: Long
        get() = native.bit_rate()
        set(value) {
            native.bit_rate(value)
        }
    public actual var packetSize: Int
        get() = native.packet_size()
        set(value) {
            native.packet_size(value)
        }
    public actual var maxDelay: Int
        get() = native.max_delay()
        set(value) {
            native.max_delay(value)
        }
    public actual var flags: Int
        get() = native.flags()
        set(value) {
            native.flags(value)
        }
    public actual var probesize: Long
        get() = native.probesize()
        set(value) {
            native.probesize(value)
        }
    public actual var maxAnalyzeDuration: Long
        get() = native.max_analyze_duration()
        set(value) {
            native.max_analyze_duration(value)
        }
    public actual var key: UByte
        get() = native.key().get().toUByte()
        set(value) {
            native.key(BytePointer(value.toByte()))
        }
    public actual var keylen: Int
        get() = native.keylen()
        set(value) {
            native.keylen(value)
        }
    public actual val programs: List<AVProgram> = List(native.nb_programs()) {
        AVProgram(native.programs(it))
    }
    public actual val videoCodecId: Int = native.video_codec_id()
    public actual val audioCodecId: Int = native.audio_codec_id()
    public actual val subtitleCodecId: Int = native.subtitle_codec_id()
    public actual val dataCodecId: Int = native.data_codec_id()
    public actual val metadata: AVDictionary? = native.metadata()?.let { AVDictionary(it) }
    public actual val startTimeRealtime: Long = native.start_time_realtime()
    public actual val fpsProbeSize: Int = native.fps_probe_size()
    public actual val errorRecognition: Int = native.error_recognition()
    public actual val interruptCallback: AVIOInterruptCB<*> = TODO()
    public actual val debug: Int = native.debug()
    public actual val maxStreams: Int = native.max_streams()
    public actual val maxIndexSize: Int = native.max_index_size()
    public actual val maxPictureBuffer: Int = native.max_picture_buffer()
    public actual val maxInterleaveDelta: Long = native.max_interleave_delta()
    public actual val maxTsProbe: Int = native.max_ts_probe()
    public actual val maxChunkDuration: Int = native.max_chunk_duration()
    public actual val maxChunkSize: Int = native.max_chunk_size()
    public actual val maxProbePackets: Int = native.max_probe_packets()
    public actual val strictStdCompliance: Int = native.strict_std_compliance()
    public actual val eventFlags: Int = native.event_flags()
    public actual val avoidNegativeTs: Int = native.avoid_negative_ts()
    public actual val audioPreload: Int = native.audio_preload()
    public actual val useWallclockAsTimestamps: Int = native.use_wallclock_as_timestamps()
    public actual val skipEstimateDurationFromPts: Int = native.skip_estimate_duration_from_pts()
    public actual val avioFlags: Int = native.avio_flags()
    public actual val durationEstimationMethod: Int = native.duration_estimation_method()
    public actual val skipInitialBytes: Long = native.skip_initial_bytes()
    public actual val correctTsOverflow: Int = native.correct_ts_overflow()
    public actual val seek2any: Int = native.seek2any()
    public actual val flushPackets: Int = native.flush_packets()
    public actual val probeScore: Int = native.probe_score()
    public actual val formatProbesize: Int = native.format_probesize()

    public actual val codecWhitelist: List<String> by lazy {
        List(native.codec_whitelist().sizeof()) {
            native.codec_whitelist().string
        }
    }
    public actual val formatWhitelist: List<String> by lazy {
        List(native.format_whitelist().sizeof()) {
            native.format_whitelist().string
        }
    }
    public actual val protocolWhitelist: List<String> by lazy {
        List(native.protocol_whitelist().sizeof()) {
            native.protocol_whitelist().string
        }
    }
    public actual val protocolBlacklist: List<String> by lazy {
        List(native.protocol_blacklist().sizeof()) {
            native.protocol_blacklist().string
        }
    }

    public actual val ioRepositioned: Int = native.io_repositioned()

    public actual var videoCodec: AVCodec?
        get() = native.video_codec()?.let(::AVCodec)
        set(value) {
            native.video_codec(value?.native)
        }
    public actual var audioCodec: AVCodec?
        get() = native.audio_codec()?.let(::AVCodec)
        set(value) {
            native.audio_codec(value?.native)
        }
    public actual var subtitleCodec: AVCodec?
        get() = native.subtitle_codec()?.let(::AVCodec)
        set(value) {
            native.subtitle_codec(value?.native)
        }
    public actual var dataCodec: AVCodec?
        get() = native.data_codec()?.let(::AVCodec)
        set(value) {
            native.data_codec(value?.native)
        }

    public actual val metadataHeaderPadding: Int = native.metadata_header_padding()
    public actual val outputTsOffset: Long
        get() = native.output_ts_offset()
    public actual var dumpSeparator: ByteArray
        get() = native.dump_separator().stringBytes
        set(value) {
            native.dump_separator(BytePointer(*value))
        }
    public actual var durationProbeSize: Long
        get() = native.probesize()
        set(value) {
            native.probesize(value)
        }

    public actual fun findStreamInfo(options: AVDictionary?): Boolean {
        return avformat_find_stream_info(native, options?.let(::AVDictionaryNative)).checkTrue()
    }

    public actual fun findProgramFromStream(last: AVProgram?, streamIndex: Int): AVProgram? {
        return av_find_program_from_stream(native, last?.native, streamIndex)?.let(::AVProgram)
    }

    public actual fun programAddStreamIndex(programId: Int, streamIndex: Int) {
        av_program_add_stream_index(native, programId, streamIndex)
    }

    public actual fun newStream(): AVStream {
        return AVStream(avformat_new_stream(native, null))
    }

    public override fun close() {
        avformat_close_input(native)
    }

    public actual fun findBestStream(
        type: Int,
        wantedStreamIndex: Int,
        relatedStreamIndex: Int,
        codec: AVCodec,
        flags: Int
    ): Int {
        return av_find_best_stream(
            native,
            type,
            wantedStreamIndex,
            relatedStreamIndex,
            codec.native,
            flags
        )
    }

    public actual fun readFrame(): AVPacket? {
        return NativeAVPacket().takeIf {
            av_read_frame(native, it) == 0
        }?.let(::AVPacket)
    }

    public actual fun seekFrame(
        streamIndex: Int,
        timestamp: Long,
        flags: Int
    ) {
        av_seek_frame(native, streamIndex, timestamp, flags).checkError()
    }

    public actual fun seekFile(
        streamIndex: Int,
        minTimestamp: Long,
        timestamp: Long,
        maxTimestamp: Long,
        flags: Int
    ) {
        avformat_seek_file(
            native,
            streamIndex,
            minTimestamp,
            timestamp,
            maxTimestamp,
            flags
        ).checkError()
    }

    public actual fun flush() {
        avformat_flush(native).checkError()
    }

    public actual fun readPlay() {
        av_read_play(native).checkError()
    }

    public actual fun readPause() {
        av_read_pause(native).checkError()
    }

    public actual fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    ) {
        av_dump_format(native, index, url, if (isOutput) 1 else 0)
    }

    public actual fun writeHeader(options: AVDictionary?) {
        avformat_write_header(native, options?.let(::AVDictionaryNative)).checkError()
    }

    public actual fun writeTrailer() {
        av_write_trailer(native).checkError()
    }

    public actual fun writeFrame(packet: AVPacket) {
        av_write_frame(native, packet.native).checkError()
    }

    public actual fun interleavedWriteFrame(packet: AVPacket) {
        av_interleaved_write_frame(native, packet.native).checkError()
    }

    public actual companion object {
        public actual fun openInput(
            url: String,
            format: AVInputFormat?,
            options: AVDictionary?
        ): AVFormatContext {
            val formatContext = avformat_alloc_context()

            avformat_open_input(
                formatContext,
                url,
                format,
                options?.let(::AVDictionaryNative)
            ).checkError()

            return AVFormatContext(formatContext)
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