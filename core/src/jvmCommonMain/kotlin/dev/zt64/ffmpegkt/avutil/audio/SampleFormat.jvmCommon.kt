package dev.zt64.ffmpegkt.avutil.audio

import org.bytedeco.ffmpeg.global.avutil.*

public actual fun SampleFormat(name: String): SampleFormat {
    return SampleFormat(av_get_sample_fmt(name))
}

public actual inline val SampleFormat.name: String?
    get() = av_get_sample_fmt_name(num)?.string

public actual inline val SampleFormat.packed: SampleFormat
    get() = SampleFormat(av_get_packed_sample_fmt(num))

public actual inline val SampleFormat.planar: SampleFormat
    get() = SampleFormat(av_get_planar_sample_fmt(num))

public actual inline val SampleFormat.bytesPerSample: Int
    get() = av_get_bytes_per_sample(num)

public actual inline val SampleFormat.isPlanar: Boolean
    get() = av_sample_fmt_is_planar(num) != 0

public actual fun SampleFormat.altSampleFmt(planar: Int): SampleFormat {
    return SampleFormat(av_get_alt_sample_fmt(num, planar))
}