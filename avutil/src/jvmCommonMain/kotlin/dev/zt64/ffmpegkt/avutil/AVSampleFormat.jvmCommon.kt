package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.global.avutil.*

public actual fun AVSampleFormat(name: String): AVSampleFormat {
    return AVSampleFormat(av_get_sample_fmt(name))
}

public actual val AVSampleFormat.name: String?
    get() = av_get_sample_fmt_name(num)?.string

public actual val AVSampleFormat.packed: AVSampleFormat
    get() = AVSampleFormat(av_get_packed_sample_fmt(num))

public actual val AVSampleFormat.planar: AVSampleFormat
    get() = AVSampleFormat(av_get_planar_sample_fmt(num))

public actual val AVSampleFormat.bytesPerSample: Int
    get() = av_get_bytes_per_sample(num)

public actual val AVSampleFormat.isPlanar: Boolean
    get() = av_sample_fmt_is_planar(num) != 0

public actual fun AVSampleFormat.altSampleFmt(planar: Int): AVSampleFormat {
    return AVSampleFormat(av_get_alt_sample_fmt(num, planar))
}