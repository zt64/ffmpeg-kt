package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.global.avutil

public actual typealias AVDictionary = org.bytedeco.ffmpeg.avutil.AVDictionary

public actual fun AVDictionary.count(): Int = avutil.av_dict_count(this)

public actual operator fun AVDictionary.get(key: String, flags: Int): String? {
    return avutil.av_dict_get(this, key, null, flags).value().string
}

public actual fun AVDictionary.set(
    key: String,
    value: String,
    flags: Int
) {
    avutil.av_dict_set(this, key, value, flags)
}

public actual fun AVDictionary.set(
    key: String,
    value: Long,
    flags: Int
) {
    avutil.av_dict_set_int(this, key, value, flags)
}