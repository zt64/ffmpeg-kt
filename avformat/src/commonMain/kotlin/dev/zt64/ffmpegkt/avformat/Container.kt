package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.Codec
import dev.zt64.ffmpegkt.avcodec.Packet
import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.avutil.Frame

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
            format: AVInputFormat? = null,
            options: Dictionary? = null
        ): InputContainer

        /**
         * Open an input container from a byte array.
         */
        public fun openInput(
            byteArray: ByteArray,
            format: AVInputFormat? = null,
            options: Dictionary? = null
        ): InputContainer

        public fun openOutput(
            format: AVOutputFormat? = null,
            formatName: String?,
            filename: String
        ): OutputContainer

        public fun openOutput(filename: String): OutputContainer
    }
}

public expect class InputContainer : Container {
    public var startTime: Long
    public var duration: Long
    public var bitRate: Long

    public fun demux(): List<Packet>

    public fun decode(): List<Frame>

    public fun seek(offset: Int)

    public override fun close()
}

public expect class OutputContainer : Container {
    public override val metadata: MutableMap<String, String>

    public constructor(
        format: AVOutputFormat? = null,
        formatName: String? = null,
        filename: String
    )

    /**
     * Add a new stream to the container.
     */
    public fun <T : Stream> addStream(stream: T): T

    /**
     * Create a new stream with the given codec.
     *
     * @param T The type of the stream to create.
     * @param codec The codec to use for the new stream.
     * @param streamIndex The index of the stream, or -1 to let FFmpeg choose.
     * @return The newly created stream.
     */
    public inline fun <reified T : Stream> newStream(codec: Codec, streamIndex: Int = -1): T

    public fun writeHeader()

    /**
     * Mux a packet into the container.
     */
    public fun mux(packet: Packet)

    /**
     * Mux multiple packets into the container.
     */
    public fun mux(packets: List<Packet>)

    public override fun close()
}