package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.stream.AudioStream
import dev.zt64.ffmpegkt.stream.Stream
import dev.zt64.ffmpegkt.stream.VideoStream

/**
 * A specialized list implementation for holding and managing media streams from a container.
 *
 * This class acts as a read-only list of [Stream] objects, providing convenient
 * delegated properties to quickly access filtered lists of specific stream types,
 * such as audio or video.
 *
 * @property streams The complete list of all streams in the container.
 */
public class StreamContainer(
    public val streams: List<Stream>
) : AbstractList<Stream>() {
    /**
     * The total number of streams in the container.
     */
    override val size: Int = streams.size

    /**
     * A filtered list containing only the [AudioStream]s from the container.
     */
    public val audio: List<AudioStream>
        get() = streams.filterIsInstance<AudioStream>()

    /**
     * A filtered list containing only the [VideoStream]s from the container.
     */
    public val video: List<VideoStream>
        get() = streams.filterIsInstance<VideoStream>()

    /**
     * Retrieves the stream at the specified [index].
     *
     * @param index The index of the stream to retrieve.
     * @return The [Stream] at the given index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    override fun get(index: Int): Stream = streams[index]
}