package dev.zt64.ffmpegkt.codec

public expect class CodecParserContext : AutoCloseable {
    public constructor(codec: CodecContext)

    public val parser: AVCodecParser
    public val frameOffset: Long

    /**
     * Parse a packet.
     *
     * @param input
     * @param pts
     * @param dts
     * @param pos
     * @return the number of bytes of the input bitstream used.
     */
    public fun parse(
        input: ByteArray,
        dataSize: Int,
        pts: Long = 0x800000000000000,
        dts: Long = 0x800000000000000,
        pos: Long = 0
    ): ParsedPacket

    public fun parsePackets(input: ByteArray): List<ParsedPacket>

    public override fun close()
}

public data class ParsedPacket(public val packet: Packet, public val bytesRead: Int)