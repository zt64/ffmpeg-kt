package dev.zt64.ffmpegkt.avutil

public typealias AVDictionary = Map<String, String>

public expect class AVDictionaryNative

public expect fun AVDictionaryNative(dict: AVDictionary): AVDictionaryNative

public expect fun AVDictionary(nativeDict: AVDictionaryNative): AVDictionary