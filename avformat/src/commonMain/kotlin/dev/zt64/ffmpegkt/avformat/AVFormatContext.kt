package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodec
import dev.zt64.ffmpegkt.avcodec.AVPacket
import dev.zt64.ffmpegkt.avutil.AVDictionary

public expect class AVFormatContext

public expect val AVFormatContext.metadata: AVDictionary

public expect fun AVFormatContext.findStreamInfo(options: AVDictionary? = null): Boolean

public expect fun AVFormatContext.findProgramFromStream(
    last: AVProgram?,
    streamIndex: Int
): AVProgram?

public expect fun AVFormatContext.programAddStreamIndex(programId: Int, streamIndex: Int)

public expect fun AVFormatContext.findBestStream(
    type: Int,
    wantedStreamIndex: Int,
    relatedStreamIndex: Int,
    codec: AVCodec,
    flags: Int
): Int

public expect fun AVFormatContext.readFrame(): AVPacket

public expect fun AVFormatContext.seekFrame(
    streamIndex: Int,
    timestamp: Long,
    flags: Int
)

public expect fun AVFormatContext.seekFile(
    streamIndex: Int,
    minTimestamp: Long,
    timestamp: Long,
    maxTimestamp: Long,
    flags: Int
)

public expect fun AVFormatContext.flush()

public expect fun AVFormatContext.readPlay()

public expect fun AVFormatContext.readPause()

public expect fun AVFormatContext.closeInput()

public fun AVFormatContext.listDevices() {
}