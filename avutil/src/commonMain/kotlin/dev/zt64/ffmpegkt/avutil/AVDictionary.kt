package dev.zt64.ffmpegkt.avutil

public typealias AVDictionary = Map<String, String>

internal expect class AVDictionaryNative

/**
 * Converts an [AVDictionaryNative] to a [AVDictionary] for use in Kotlin.
 */
internal expect fun AVDictionary(nativeDict: AVDictionaryNative): AVDictionary

/**
 * Converts an [AVDictionary] to a [AVDictionaryNative] for passing to FFmpeg.
 */
internal expect fun AVDictionary.toNative(): AVDictionaryNative