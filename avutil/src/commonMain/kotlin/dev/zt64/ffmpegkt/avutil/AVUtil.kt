package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.Library

public expect object AVUtil : Library {
    public fun versionInfo(): String

    public fun getMediaTypeString(type: AVMediaType): String?

    public fun getPictureTypeChar(type: AVPictureType): Char

    public fun getTimeBaseQ(): AVRational

    public fun dictIterate(dict: AVDictionary, prev: AVDictionaryEntry?): AVDictionaryEntry?
}