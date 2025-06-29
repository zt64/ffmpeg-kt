package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.codec.Codec
import dev.zt64.ffmpegkt.codec.Packet
import dev.zt64.ffmpegkt.stream.Stream

public expect class OutputContainer : Container {
    public override val metadata: MutableMap<String, String>

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