package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_dict_count
import kotlinx.cinterop.CPointer

@Suppress(
    "ACTUAL_WITHOUT_EXPECT",
    "ACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION"
)
public actual typealias AVDictionary = CPointer<ffmpeg.AVDictionary>

public actual fun AVDictionary.count(): Int = av_dict_count(this)

public actual operator fun AVDictionary.get(key: String, flags: Int): String? {
    TODO("Not yet implemented")
}

public actual fun AVDictionary.set(
    key: String,
    value: String,
    flags: Int
) {
    TODO("Not yet implemented")
}

public actual fun AVDictionary.set(
    key: String,
    value: Long,
    flags: Int
) {
    // av_dict_set_int(this, key, value, flags)
}