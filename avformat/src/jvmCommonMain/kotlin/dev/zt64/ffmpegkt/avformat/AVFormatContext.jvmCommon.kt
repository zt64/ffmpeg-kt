package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodec
import dev.zt64.ffmpegkt.avcodec.AVPacket
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.checkTrue
import org.bytedeco.ffmpeg.global.avformat.*

public actual typealias AVFormatContext = org.bytedeco.ffmpeg.avformat.AVFormatContext

public actual val AVFormatContext.metadata: AVDictionary
    get() = metadata()

public actual fun AVFormatContext.findStreamInfo(options: AVDictionary?): Boolean {
    return avformat_find_stream_info(this, options).checkTrue()
}

public actual fun AVFormatContext.findProgramFromStream(
    last: AVProgram?,
    streamIndex: Int
): AVProgram? {
    return av_find_program_from_stream(this, last, streamIndex)
}

public actual fun AVFormatContext.programAddStreamIndex(programId: Int, streamIndex: Int) {
    av_program_add_stream_index(this, programId, streamIndex)
}

public actual fun AVFormatContext.findBestStream(
    type: Int,
    wantedStreamIndex: Int,
    relatedStreamIndex: Int,
    codec: AVCodec,
    flags: Int
): Int {
    return av_find_best_stream(
        this,
        type,
        wantedStreamIndex,
        relatedStreamIndex,
        codec,
        flags
    )
}

public actual fun AVFormatContext.readFrame(): AVPacket {
    val packet = AVPacket()
    av_read_frame(this, packet)
    return packet
}

public actual fun AVFormatContext.seekFrame(
    streamIndex: Int,
    timestamp: Long,
    flags: Int
) {
    av_seek_frame(this, streamIndex, timestamp, flags)
}

public actual fun AVFormatContext.seekFile(
    streamIndex: Int,
    minTimestamp: Long,
    timestamp: Long,
    maxTimestamp: Long,
    flags: Int
) {
    avformat_seek_file(this, streamIndex, minTimestamp, timestamp, maxTimestamp, flags)
}

public actual fun AVFormatContext.flush() {
    avformat_flush(this)
}

public actual fun AVFormatContext.readPlay() {
    av_read_play(this)
}

public actual fun AVFormatContext.readPause() {
    av_read_pause(this)
}

public actual fun AVFormatContext.closeInput() {
    avformat_close_input(this)
}