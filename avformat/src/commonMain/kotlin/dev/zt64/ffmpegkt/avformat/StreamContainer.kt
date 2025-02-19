package dev.zt64.ffmpegkt.avformat

public class StreamContainer(
    public val streams: List<Stream>,
) : AbstractList<Stream>() {
    override val size: Int = streams.size

    public val audio: List<AudioStream>
        get() = streams.filterIsInstance<AudioStream>()

    public val video: List<VideoStream>
        get() = streams.filterIsInstance<VideoStream>()

    override fun get(index: Int): Stream = streams[index]
}