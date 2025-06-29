package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.codec.Packet

/**
 * A container for reading multimedia data from an input file.
 *
 * This class is used to open and read from multimedia files. It can be used to read files in various formats, such as MP4, MKV, etc.
 *
 * @property startTime The start time of the media
 * @property duration The duration of the media
 * @property bitRate The total bit rate of the media in bits per second.
 */
public expect class InputContainer : Container {
    public val startTime: Long
    public val duration: Long
    public val bitRate: Long

    /**
     * Read packets from the container.
     *
     * @return A list of [Packet]s read from the container.
     */
    public fun demux(): List<Packet>

    /**
     * Decode frames from the container.
     *
     * @return A list of [Frame]s decoded from the container.
     */
    public fun decode(): List<Frame>

    /**
     * Seek to a specific position in the media.
     *
     * @param offset The offset to seek to
     */
    public fun seek(offset: Int)

    /**
     * Close the container and release all associated resources.
     */
    public override fun close()
}