package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodec
import dev.zt64.ffmpegkt.avcodec.AVPacket
import dev.zt64.ffmpegkt.avutil.AVClass
import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.*
import kotlinx.cinterop.*

internal typealias NativeAVFormatContext = CPointer<ffmpeg.AVFormatContext>

public actual class AVFormatContext(public val ptr: NativeAVFormatContext) : AutoCloseable {
    public actual val avClass: AVClass
        get() = ptr.pointed.av_class!!.let(::AVClass)
    public actual val iformat: AVInputFormat?
        get() = ptr.pointed.iformat?.pointed
    public actual val oformat: AVOutputFormat?
        get() = ptr.pointed.oformat?.pointed

    public actual var pb: AVIOContext?
        get() = ptr.pointed.pb?.pointed?.let(::AVIOContext)
        set(value) {
            ptr.pointed.pb = value?.native?.ptr
        }
    public actual var ctxFlags: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual val streams: List<AVStream>
        get() = TODO("Not yet implemented")
    public actual val chapters: List<AVChapter>
        get() = TODO("Not yet implemented")
    public actual val url: String
        get() = TODO("Not yet implemented")
    public actual var startTime: Long
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var duration: Long
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var bitRate: Long
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var packetSize: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var maxDelay: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var flags: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var probesize: Long
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var maxAnalyzeDuration: Long
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var key: UByte
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var keylen: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual val programs: List<AVProgram>
        get() = TODO("Not yet implemented")
    public actual val videoCodecId: Int
        get() = TODO("Not yet implemented")
    public actual val audioCodecId: Int
        get() = TODO("Not yet implemented")
    public actual val subtitleCodecId: Int
        get() = TODO("Not yet implemented")
    public actual val dataCodecId: Int
        get() = TODO("Not yet implemented")
    public actual val startTimeRealtime: Long
        get() = TODO("Not yet implemented")
    public actual val fpsProbeSize: Int
        get() = TODO("Not yet implemented")
    public actual val errorRecognition: Int
        get() = TODO("Not yet implemented")
    public actual val debug: Int
        get() = TODO("Not yet implemented")
    public actual val maxStreams: Int
        get() = TODO("Not yet implemented")
    public actual val maxIndexSize: Int
        get() = TODO("Not yet implemented")
    public actual val maxPictureBuffer: Int
        get() = TODO("Not yet implemented")
    public actual val maxInterleaveDelta: Long
        get() = TODO("Not yet implemented")
    public actual val maxTsProbe: Int
        get() = TODO("Not yet implemented")
    public actual val maxChunkDuration: Int
        get() = TODO("Not yet implemented")
    public actual val maxChunkSize: Int
        get() = TODO("Not yet implemented")
    public actual val maxProbePackets: Int
        get() = TODO("Not yet implemented")
    public actual val strictStdCompliance: Int
        get() = TODO("Not yet implemented")
    public actual val eventFlags: Int
        get() = TODO("Not yet implemented")
    public actual val avoidNegativeTs: Int
        get() = TODO("Not yet implemented")
    public actual val audioPreload: Int
        get() = TODO("Not yet implemented")
    public actual val useWallclockAsTimestamps: Int
        get() = TODO("Not yet implemented")
    public actual val skipEstimateDurationFromPts: Int
        get() = TODO("Not yet implemented")
    public actual val avioFlags: Int
        get() = TODO("Not yet implemented")
    public actual val durationEstimationMethod: Int
        get() = TODO("Not yet implemented")
    public actual val skipInitialBytes: Long
        get() = TODO("Not yet implemented")
    public actual val correctTsOverflow: Int
        get() = TODO("Not yet implemented")
    public actual val seek2any: Int
        get() = TODO("Not yet implemented")
    public actual val flushPackets: Int
        get() = TODO("Not yet implemented")
    public actual val probeScore: Int
        get() = TODO("Not yet implemented")
    public actual val formatProbesize: Int
        get() = TODO("Not yet implemented")
    public actual val codecWhitelist: List<String>
        get() = TODO("Not yet implemented")
    public actual val formatWhitelist: List<String>
        get() = TODO("Not yet implemented")
    public actual val protocolWhitelist: List<String>
        get() = TODO("Not yet implemented")
    public actual val protocolBlacklist: List<String>
        get() = TODO("Not yet implemented")
    public actual val ioRepositioned: Int
        get() = TODO("Not yet implemented")
    public actual var videoCodec: AVCodec?
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var audioCodec: AVCodec?
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var subtitleCodec: AVCodec?
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var dataCodec: AVCodec?
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual val metadataHeaderPadding: Int
        get() = TODO("Not yet implemented")
    public actual val outputTsOffset: Long
        get() = TODO("Not yet implemented")
    public actual var dumpSeparator: ByteArray
        get() = TODO("Not yet implemented")
        set(value) {}
    public actual var durationProbeSize: Long
        get() = ptr.pointed.probesize
        set(value) {
            ptr.pointed.probesize = value
        }

    public actual fun findProgramFromStream(last: AVProgram?, streamIndex: Int): AVProgram? {
        return av_find_program_from_stream(
            ptr,
            last?.ptr,
            streamIndex
        )?.pointed
    }

    public actual fun programAddStreamIndex(programId: Int, streamIndex: Int) {
        av_program_add_stream_index(
            ptr,
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
            ptr,
            type,
            wantedStreamIndex,
            relatedStreamIndex,
            codec.ptr.ptr,
            flags
        )
    }

    public actual fun newStream(): AVStream {
        return avformat_new_stream(
            ptr,
            null
        )!!.pointed.let { AVStream(it) }
    }

    public actual fun seekFrame(
        streamIndex: Int,
        timestamp: Long,
        flags: Int
    ) {
        av_seek_frame(
            ptr,
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
            ptr,
            streamIndex,
            minTimestamp,
            timestamp,
            maxTimestamp,
            flags
        )
    }

    public actual fun flush() {
        avformat_flush(ptr)
    }

    public actual fun readFrame(): AVPacket? {
        val pkt = nativeHeap.alloc<AVPacket>()
        av_read_frame(ptr, pkt.ptr).checkError()
        return pkt
    }

    public actual fun readPlay() {
        av_read_play(ptr).checkError()
    }

    public actual fun readPause() {
        av_read_pause(ptr).checkError()
    }

    public actual fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    ) {
        av_dump_format(ptr, index, url, if (isOutput) 1 else 0)
    }

    public actual fun writeTrailer() {
        av_write_trailer(ptr).checkError()
    }

    public actual fun writeFrame(packet: AVPacket) {
        av_write_frame(ptr, packet.ptr).checkError()
    }

    public actual fun interleavedWriteFrame(packet: AVPacket) {
        av_interleaved_write_frame(ptr, packet.ptr).checkError()
    }
}