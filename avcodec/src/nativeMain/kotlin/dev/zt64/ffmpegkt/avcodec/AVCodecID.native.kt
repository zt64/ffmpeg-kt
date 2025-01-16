package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.MediaType
import ffmpeg.avcodec_get_type
import kotlinx.cinterop.toKString

public actual inline val AVCodecID.type: MediaType
    get() = MediaType(avcodec_get_type(num.toUInt()))

public actual inline val AVCodecID.name: String
    get() = ffmpeg.avcodec_get_name(num.toUInt())!!.toKString()