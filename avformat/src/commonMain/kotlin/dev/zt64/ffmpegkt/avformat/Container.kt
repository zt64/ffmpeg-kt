package dev.zt64.ffmpegkt.avformat

public expect abstract class Container : AutoCloseable {
    override fun close()
}

public expect class InputContainer : Container {
    public fun demux()

    public fun decode()
}

public expect class OutputContainer : Container {
    public fun addStream(stream: Stream)
}