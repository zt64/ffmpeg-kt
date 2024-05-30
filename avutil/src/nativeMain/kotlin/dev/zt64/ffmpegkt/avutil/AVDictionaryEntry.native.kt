package dev.zt64.ffmpegkt.avutil

import kotlinx.cinterop.toKString

public actual typealias AVDictionaryEntry = ffmpeg.AVDictionaryEntry

public actual val AVDictionaryEntry.tkey: String
    get() = key!!.toKString()

public actual val AVDictionaryEntry.tvalue: String
    get() = value!!.toKString()