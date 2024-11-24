package dev.zt64.ffmpegkt.avformat

public actual abstract class Container(internal val ctx: AVFormatContext) : AutoCloseable {
    actual override fun close() {
    }
}

public actual class InputContainer(ctx: AVFormatContext) : Container(ctx) {
    public actual fun demux() {
    }

    public actual fun decode() {
    }
}

public actual class OutputContainer(ctx: AVFormatContext) : Container(ctx) {
    public actual fun addStream(stream: Stream) {
    }
}