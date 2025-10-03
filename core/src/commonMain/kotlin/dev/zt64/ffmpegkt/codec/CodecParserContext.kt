package dev.zt64.ffmpegkt.codec

/**
 * A context for parsing raw codec data streams.
 *
 * This class wraps an FFmpeg `AVCodecParserContext` and is used to split a raw
 * bitstream into packets.
 */
public expect class CodecParserContext : AutoCloseable {
    /**
     * Creates a parser context for the given codec context.
     *
     * @param codec The codec context that provides information about the stream to be parsed.
     */
    public constructor(codec: CodecContext)

    /**
     * The underlying FFmpeg codec parser instance.
     */
    public val parser: CodecParser

    /**
     * The byte offset of the parsed frame in the input stream.
     */
    public val frameOffset: Long

    /**
     * Parses the input data to extract a single packet.
     *
     * @param input The raw bitstream data to parse.
     * @param dataSize The number of bytes from the input to parse.
     * @param pts The presentation timestamp (PTS) for the packet. Defaults to no timestamp.
     * @param dts The decompression timestamp (DTS) for the packet. Defaults to no timestamp.
     * @param pos The position of the packet in the stream.
     * @return A [ParsedPacket] containing the resulting packet and the number of bytes consumed from the input.
     */
    public fun parse(
        input: ByteArray,
        dataSize: Int,
        pts: Long = 0x800000000000000,
        dts: Long = 0x800000000000000,
        pos: Long = 0
    ): ParsedPacket

    /**
     * Parses the entire input byte array into a list of packets.
     *
     * This function repeatedly calls the parser until all data in the input array has been consumed.
     *
     * @param input The raw bitstream data to parse.
     * @return A list of all packets extracted from the input data.
     */
    public fun parsePackets(input: ByteArray): List<ParsedPacket>

    /**
     * Releases the native resources associated with this parser context.
     */
    public override fun close()
}

/**
 * A data class that holds a parsed [Packet] and the number of bytes read to create it.
 *
 * @property packet The packet extracted from the bitstream.
 * @property bytesRead The number of bytes consumed from the input buffer to generate the packet.
 */
public data class ParsedPacket(public val packet: Packet, public val bytesRead: Int)