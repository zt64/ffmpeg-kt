package dev.zt64.ffmpegkt.avutil

public actual typealias AVDictionaryEntry = org.bytedeco.ffmpeg.avutil.AVDictionaryEntry

public actual val AVDictionaryEntry.tkey: String
    get() = key().string

public actual val AVDictionaryEntry.tvalue: String
    get() = value().string