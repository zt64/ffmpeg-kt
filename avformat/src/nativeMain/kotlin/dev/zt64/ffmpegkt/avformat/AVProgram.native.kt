package dev.zt64.ffmpegkt.avformat

import ffmpeg.av_new_program
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public actual class AVProgram(internal val native: ffmpeg.AVProgram) {
    public actual constructor(
        formatContext: AVFormatContext,
        id: Int
    ) : this(av_new_program(formatContext.native.ptr, id)!!.pointed)
}