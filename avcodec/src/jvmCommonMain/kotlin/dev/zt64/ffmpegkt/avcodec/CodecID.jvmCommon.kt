package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.MediaType
import org.bytedeco.ffmpeg.global.avcodec

public actual inline val CodecID.type: MediaType
    get() = MediaType(avcodec.avcodec_get_type(this.num))

public actual inline val CodecID.name: String
    get() = avcodec.avcodec_get_name(num)!!.string