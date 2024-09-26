package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVMediaType
import org.bytedeco.ffmpeg.global.avcodec

public actual inline val AVCodecID.type: AVMediaType
    get() = AVMediaType(avcodec.avcodec_get_type(this.num))

public actual inline val AVCodecID.name: String
    get() = avcodec.avcodec_get_name(num).string