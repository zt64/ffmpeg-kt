package dev.zt64.ffmpegkt.avutil

public typealias AVDictionary = Map<String, String>

public expect class AVDictionaryNative

/**
 * Converts an [AVDictionary] to a [AVDictionaryNative] for passing to FFmpeg.
 */
public expect fun AVDictionaryNative(dict: AVDictionary): AVDictionaryNative

/**
 * Converts an [AVDictionaryNative] to a [AVDictionary] for use in Kotlin.
 */
public expect fun AVDictionary(nativeDict: AVDictionaryNative): AVDictionary