package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.codec.Codec
import dev.zt64.ffmpegkt.codec.Packet
import dev.zt64.ffmpegkt.stream.Stream

/**
 * A container for writing multimedia data to an output file.
 *
 * This class is used to create and write to multimedia files. It can be used to create files in various formats, such as MP4, MKV, etc.
 *
 * @property metadata A map of metadata key-value pairs to be written to the container.
 */
public expect class OutputContainer : Container {
    public override val metadata: MutableMap<String, String>

    /**
     * Creates a new OutputContainer.
     *
     * @param filename The path to the output file.
     * @param format The output format to use. If null, FFmpeg will guess from the filename.
     * @param formatName The name of the output format to use. If null, FFmpeg will guess from the filename.
     */
    public constructor(
        filename: String,
        format: AVOutputFormat? = null,
        formatName: String? = null
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

    /**
     * Write the header of the container.
     *
     * This must be called before any packets are muxed.
     */
    public fun writeHeader()

    /**
     * Mux a packet into the container.
     */
    public fun mux(packet: Packet)

    /**
     * Mux multiple packets into the container.
     */
    public fun mux(packets: List<Packet>)

    /**
     * Close the container and release resources.
     *
     * This must be called when you are done writing to the container. This will finalize the container, and write any remaining data to
     * the output file.
     */
    public override fun close()
}