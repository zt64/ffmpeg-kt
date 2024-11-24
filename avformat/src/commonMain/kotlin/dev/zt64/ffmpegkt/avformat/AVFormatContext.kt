package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodec
import dev.zt64.ffmpegkt.avcodec.AVPacket
import dev.zt64.ffmpegkt.avutil.*

public expect class AVFormatContext(
    format: AVOutputFormat? = null,
    formatName: String? = null,
    filename: String
) : AutoCloseable {
    public val avClass: AVClass
    public val iformat: AVInputFormat?
    public val oformat: AVOutputFormat?

    public var pb: AVIOContext?
    public var ctxFlags: Int
    public val streams: StreamContainer

    // public val streamGroups: List<AVStreamGroup>
    public val chapters: List<Chapter>
    public val url: String
    public var startTime: Long
    public var duration: Long
    public var bitRate: Long
    public var packetSize: Int
    public var maxDelay: Int
    public var flags: Int
    public var probesize: Long
    public var maxAnalyzeDuration: Long
    public var key: UByte
    public var keylen: Int
    public val programs: List<AVProgram>
    public val videoCodecId: Int
    public val audioCodecId: Int
    public val subtitleCodecId: Int
    public val dataCodecId: Int
    public val metadata: AVDictionary?
    public val startTimeRealtime: Long
    public val fpsProbeSize: Int
    public val errorRecognition: Int
    public val interruptCallback: AVIOInterruptCB<*>
    public val debug: Int
    public val maxStreams: Int
    public val maxIndexSize: Int
    public val maxPictureBuffer: Int
    public val maxInterleaveDelta: Long
    public val maxTsProbe: Int
    public val maxChunkDuration: Int
    public val maxChunkSize: Int
    public val maxProbePackets: Int
    public val strictStdCompliance: Int
    public val eventFlags: Int
    public val avoidNegativeTs: Int
    public val audioPreload: Int
    public val useWallclockAsTimestamps: Int
    public val skipEstimateDurationFromPts: Int
    public val avioFlags: Int
    public val durationEstimationMethod: Int
    public val skipInitialBytes: Long
    public val correctTsOverflow: Int
    public val seek2any: Int
    public val flushPackets: Int
    public val probeScore: Int
    public val formatProbesize: Int
    public val codecWhitelist: List<String>
    public val formatWhitelist: List<String>
    public val protocolWhitelist: List<String>
    public val protocolBlacklist: List<String>
    public val ioRepositioned: Int

    public var videoCodec: AVCodec?
    public var audioCodec: AVCodec?
    public var subtitleCodec: AVCodec?
    public var dataCodec: AVCodec?

    public val metadataHeaderPadding: Int

    // public val opaque: Any
    // public val controlMessageCb: AVFormatControlMessage

    public val outputTsOffset: Long
    public var dumpSeparator: ByteArray
    public var durationProbeSize: Long

    public fun findStreamInfo(options: AVDictionary? = null): Boolean
    public fun findProgramFromStream(last: AVProgram?, streamIndex: Int): AVProgram?
    public fun programAddStreamIndex(programId: Int, streamIndex: Int)
    public fun findBestStream(
        type: Int,
        wantedStreamIndex: Int,
        relatedStreamIndex: Int,
        codec: AVCodec,
        flags: Int
    ): Int

    public fun newStream(): Stream

    public fun seekFrame(
        streamIndex: Int,
        timestamp: Long,
        flags: Int
    )

    public fun seekFile(
        streamIndex: Int,
        minTimestamp: Long,
        timestamp: Long,
        maxTimestamp: Long,
        flags: Int
    )

    public fun flush()

    public fun readFrame(): AVPacket?
    public fun readPlay()
    public fun readPause()

    public fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    )

    public fun writeHeader(options: AVDictionary? = null)
    public fun writeTrailer()

    public fun writeFrame(packet: AVPacket)
    public fun interleavedWriteFrame(packet: AVPacket)

    /**
     * Guess the sample aspect ratio of a frame, based on both the stream and the frame aspect ratio.
     *
     * @param stream the stream which the frame belongs to.
     * @param frame the frame with the sample aspect ratio to guess.
     * @return the guessed sample aspect ratio. `null` if the sample aspect ratio could not be guessed.
     */
    public fun guessSampleAspectRatio(stream: Stream, frame: Frame): Rational?

    /**
     * Guess the frame rate of a frame, based on both the stream and the frame rate.
     *
     * @param stream the stream which the frame belongs to.
     * @param frame the frame with the frame rate to guess.
     * @return the guessed frame rate. `null` if the frame rate could not be guessed.
     */
    public fun guessFrameRate(stream: Stream, frame: Frame? = null): Rational?

    public companion object {
        public fun openInput(
            url: String,
            format: AVInputFormat? = null,
            options: AVDictionary? = null
        ): AVFormatContext

        public fun openInput(
            byteArray: ByteArray,
            format: AVInputFormat? = null,
            options: AVDictionary? = null
        ): AVFormatContext

        public fun openOutput(
            format: AVOutputFormat? = null,
            formatName: String?,
            filename: String
        ): AVFormatContext

        public fun openOutput(filename: String): AVFormatContext
    }
}