package dev.zt64.ffmpegkt.codec

public interface Encoder {
    /**
     * Receive a new encoded packet of data
     * @return the packet or null if no more data
     */
    public fun encode(): Packet?
}