package dev.zt64.ffmpegkt.codec

import kotlinx.coroutines.flow.Flow

public expect class CodecParserContext : AutoCloseable {
    public constructor(codec: CodecID)

    public val parser: AVCodecParser
    public val frameOffset: Long

    /**
     * Parse a packet.
     *
     * @param avCtx
     * @param pts
     * @param dts
     * @param pos
     * @return the number of bytes of the input bitstream used.
     */
    public fun parse(
        avCtx: CodecContext,
        input: ByteArray,
        dataSize: Int,
        pts: Long = 0x800000000000000,
        dts: Long = 0x800000000000000,
        pos: Long = 0
    ): ParsedPacket

    public fun parsePackets(
        avCtx: CodecContext,
        input: ByteArray,
        pts: Long = 0x800000000000000,
        dts: Long = 0x800000000000000,
        pos: Long = 0
    ): Flow<ParsedPacket>

    public override fun close()
}

public data class ParsedPacket(public val packet: Packet, public val bytesRead: Int)