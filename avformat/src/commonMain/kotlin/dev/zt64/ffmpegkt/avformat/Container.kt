package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.Packet
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.Frame

public expect abstract class Container : AutoCloseable {
    public val metadata: AVDictionary?
    public val streams: StreamContainer
    public val chapters: List<Chapter>

    public fun dumpFormat(
        index: Int,
        url: String,
        isOutput: Boolean
    )

    abstract override fun close()

    public companion object {
        public fun openInput(
            url: String,
            format: AVInputFormat? = null,
            options: AVDictionary? = null
        ): InputContainer

        public fun openInput(
            byteArray: ByteArray,
            format: AVInputFormat? = null,
            options: AVDictionary? = null
        ): InputContainer

        public fun openOutput(
            format: AVOutputFormat? = null,
            formatName: String?,
            filename: String
        ): OutputContainer

        public fun openOutput(filename: String): OutputContainer
    }
}

public expect class InputContainer : Container {
    public var startTime: Long
    public var duration: Long
    public var bitRate: Long

    public fun demux(): List<Packet>

    public fun decode(): List<Frame>

    public fun seek(offset: Int)

    override fun close()
}

public expect class OutputContainer : Container {
    public constructor(
        format: AVOutputFormat? = null,
        formatName: String? = null,
        filename: String
    )

    public fun addStream(stream: Stream)

    override fun close()
}