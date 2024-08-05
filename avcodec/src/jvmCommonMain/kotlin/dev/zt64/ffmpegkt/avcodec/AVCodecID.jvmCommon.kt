package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVMediaType
import org.bytedeco.ffmpeg.global.avcodec

public actual inline val AVCodecID.type: AVMediaType
    get() = AVMediaType(avcodec.avcodec_get_type(this.num))