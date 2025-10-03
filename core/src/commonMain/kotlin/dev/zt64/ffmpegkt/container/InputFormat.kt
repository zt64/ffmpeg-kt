package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avformat.CodecTag

internal expect class NativeAVInputFormat

/**
 * Represents an FFmpeg input format (demuxer) that can read and parse media container formats.
 *
 * This is a value class that wraps the native FFmpeg AVInputFormat structure, providing
 * access to format metadata and capabilities in a type-safe manner.
 *
 * @property native The underlying native AVInputFormat implementation
 */
public expect value class InputFormat internal constructor(
    @PublishedApi
    internal val native: NativeAVInputFormat
) {
    /** The short name identifier of the input format (e.g., "mp4", "avi") */
    public val name: String

    /** The human-readable descriptive name of the format */
    public val longName: String

    /** Bitfield of format-specific flags indicating capabilities and behavior */
    public val flags: Int

    /** Comma-separated list of file extensions associated with this format, or null if none */
    public val extensions: String?

    /** MIME type string for this format, or null if not applicable */
    public val mimeType: String?

    /** List of codec tags supported by this input format */
    public val codecTag: List<CodecTag>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun InputFormat.commonToString(): String {
    return "AVInputFormat(name='$name', longName='$longName', flags=$flags, extensions='$extensions', mimeType='$mimeType', " +
        "codecTag=$codecTag)"
}