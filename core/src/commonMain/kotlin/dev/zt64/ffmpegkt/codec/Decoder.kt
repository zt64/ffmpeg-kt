package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.Frame

public interface Decoder {
    // TODO: Figure out way to close this
    public val parser: CodecParserContext

    /**
     * Decode a packet into a list of frames.
     *
     * @param packet The packet to decode.
     * @return A list of frames decoded from the packet. If the packet is null, it indicates the end of the stream.
     */
    public fun decode(packet: Packet?): List<Frame>
}