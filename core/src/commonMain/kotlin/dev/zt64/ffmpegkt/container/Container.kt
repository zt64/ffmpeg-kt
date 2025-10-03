package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avformat.Chapter
import dev.zt64.ffmpegkt.avutil.Dictionary

/**
 * Represents a multimedia container format (e.g., MP4, MKV).
 * This is a base class for [InputContainer] and [OutputContainer].
 */
public expect abstract class Container : AutoCloseable {
    /**
     * Metadata associated with the container.
     */
    public open val metadata: Dictionary

    /**
     * Streams in the container.
     */
    public val streams: StreamContainer

    /**
     * List of chapters in the container.
     */
    public val chapters: List<Chapter>

    /**
     * Dumps detailed information about the container format to the standard error stream.
     *
     * @param index The stream index to dump information for.
     * @param url The URL of the file.
     * @param isOutput Whether this is an output container.
     */
    public fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    )

    /**
     * Close the container and write any pending data.
     */
    public abstract override fun close()

    public companion object {
        /**
         * Open an input container from a URL or file path.
         *
         * @param url The URL or file path to open.
         * @param format The input format to use, or null to auto-detect.
         * @param options Additional options for opening the input.
         * @return An [InputContainer] instance for reading from the input.
         */
        public fun openInput(
            url: String,
            format: InputFormat? = null,
            options: Dictionary? = null
        ): InputContainer

        /**
         * Open an input container from a byte array.
         *
         * @param byteArray The byte array containing the media data.
         * @param format The input format to use, or null to auto-detect.
         * @param options Additional options for opening the input.
         * @return An [InputContainer] instance for reading from the input.
         */
        public fun openInput(
            byteArray: ByteArray,
            format: InputFormat? = null,
            options: Dictionary? = null
        ): InputContainer

        /**
         * Open an output container for writing.
         *
         * @param format The output format to use.
         * @param formatName The name of the output format.
         * @param filename The path to the output file.
         * @return An [OutputContainer] instance for writing to the output.
         */
        public fun openOutput(
            format: OutputFormat? = null,
            formatName: String?,
            filename: String
        ): OutputContainer

        /**
         * Open an output container for writing, guessing the format from the filename.
         *
         * @param filename The path to the output file.
         * @return An [OutputContainer] instance for writing to the output.
         */
        public fun openOutput(filename: String): OutputContainer
    }
}