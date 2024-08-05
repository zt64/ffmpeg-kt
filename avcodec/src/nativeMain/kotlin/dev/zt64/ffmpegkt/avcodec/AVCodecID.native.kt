package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVMediaType
import ffmpeg.avcodec_get_type

public actual inline val AVCodecID.type: AVMediaType
    get() = AVMediaType(avcodec_get_type(num.toUInt()))