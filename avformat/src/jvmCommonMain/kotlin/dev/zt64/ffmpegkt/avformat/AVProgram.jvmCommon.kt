package dev.zt64.ffmpegkt.avformat

import org.bytedeco.ffmpeg.global.avformat.av_new_program

// public actual typealias AVProgram = org.bytedeco.ffmpeg.avformat.AVProgram
public actual class AVProgram(internal val native: org.bytedeco.ffmpeg.avformat.AVProgram) {
    public actual constructor(
        formatContext: AVFormatContext,
        id: Int
    ) : this(av_new_program(formatContext.native, id))
}