package dev.zt64.ffmpegkt.container

public expect class NativeAVIOContext

/**
 * An FFmpeg I/O context (AVIO) for reading from or writing to various data sources.
 *
 * This is a value class that wraps the native FFmpeg AVIOContext structure, providing
 * buffered I/O operations for media files and streams. The I/O context handles the
 * low-level reading/writing operations and can work with files, memory buffers, or
 * custom data sources.
 *
 * @property native The underlying native AVIOContext implementation
 */
public expect value class IOContext internal constructor(internal val native: NativeAVIOContext) : AutoCloseable {
    /**
     * Creates an I/O context for file-based operations.
     *
     * @param filename Path to the file to open
     * @param flags I/O flags specifying read/write mode and other options
     */
    public constructor(filename: String, flags: Int)

    /**
     * Creates an I/O context for memory-based operations using a byte array.
     *
     * @param bytes The byte array containing the data to read from
     */
    public constructor(bytes: ByteArray)

    /**
     * Closes the I/O context and releases any associated resources.
     * This should be called when the context is no longer needed.
     */
    public override fun close()
}