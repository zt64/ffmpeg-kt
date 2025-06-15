package dev.zt64.ffmpegkt.avutil

public typealias Dictionary = Map<String, String>

internal expect class NativeAVDictionary

/**
 * Converts an [NativeAVDictionary] to a [Dictionary] for use in Kotlin.
 */
internal expect fun Dictionary(nativeDict: NativeAVDictionary): Dictionary

/**
 * Converts an [Dictionary] to a [NativeAVDictionary] for passing to FFmpeg.
 */
internal expect fun Dictionary.toNative(): NativeAVDictionary