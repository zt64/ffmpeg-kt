package dev.zt64.ffmpegkt.avcodec

public expect class CodecParserContext : AutoCloseable {
    public constructor(codec: AVCodecID)

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
}

public data class ParsedPacket(
    public val packet: AVPacket,
    public val bytesRead: Int
)