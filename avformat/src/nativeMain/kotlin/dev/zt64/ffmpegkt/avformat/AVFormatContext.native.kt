package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.checkTrue
import ffmpeg.*
import kotlinx.cinterop.*

public actual typealias AVFormatContext = ffmpeg.AVFormatContext

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
public actual val AVFormatContext.metadata: AVDictionary
    get() = metadata ?: error("metadata is null")

public actual fun AVFormatContext.findStreamInfo(options: AVDictionary?): Boolean {
    return avformat_find_stream_info(ptr, options?.reinterpret()).checkTrue()
}

public actual fun AVFormatContext.findProgramFromStream(
    last: AVProgram?,
    streamIndex: Int
): AVProgram? {
    return av_find_program_from_stream(ptr, last?.ptr, streamIndex)?.pointed
}

public actual fun AVFormatContext.programAddStreamIndex(programId: Int, streamIndex: Int) {
    av_program_add_stream_index(ptr, programId, streamIndex.toUInt())
}

public actual fun AVFormatContext.findBestStream(
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
        codec.ptr,
        flags
    )
}

public actual fun AVFormatContext.readFrame(): AVPacket {
    val packet = nativeHeap.alloc<AVPacket>()
    av_read_frame(ptr, packet.ptr)
    return packet
}

public actual fun AVFormatContext.seekFrame(
    streamIndex: Int,
    timestamp: Long,
    flags: Int
) {
    av_seek_frame(ptr, streamIndex, timestamp, flags)
}

public actual fun AVFormatContext.seekFile(
    streamIndex: Int,
    minTimestamp: Long,
    timestamp: Long,
    maxTimestamp: Long,
    flags: Int
) {
    avformat_seek_file(ptr, streamIndex, minTimestamp, timestamp, maxTimestamp, flags)
}

public actual fun AVFormatContext.flush() {
    avformat_flush(ptr)
}

public actual fun AVFormatContext.readPlay() {
    av_read_play(ptr)
}

public actual fun AVFormatContext.readPause() {
    av_read_pause(ptr)
}

public actual fun AVFormatContext.closeInput() {
    avformat_close_input(ptr.reinterpret())
}