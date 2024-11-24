package dev.zt64.ffmpegkt.avformat

public actual abstract class Container : AutoCloseable {
    actual override fun close() {
    }
}

public actual class InputContainer : Container() {
    public actual fun demux() {
    }

    public actual fun decode() {
    }
}

public actual class OutputContainer : Container() {
    public actual fun addStream(stream: Stream) {
    }
}