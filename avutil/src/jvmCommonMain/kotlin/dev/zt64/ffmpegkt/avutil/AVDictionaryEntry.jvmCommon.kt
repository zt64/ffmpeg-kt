package dev.zt64.ffmpegkt.avutil

public actual typealias AVDictionaryEntry = org.bytedeco.ffmpeg.avutil.AVDictionaryEntry

public actual inline val AVDictionaryEntry.tkey: String
    get() = key().string

public actual inline val AVDictionaryEntry.tvalue: String
    get() = value().string