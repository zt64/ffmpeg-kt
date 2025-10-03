package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.avformat.Read_packet_Pointer_BytePointer_int
import org.bytedeco.ffmpeg.global.avformat.*
import org.bytedeco.ffmpeg.global.avutil.AVERROR_EOF
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.Pointer
import java.io.InputStream

public actual typealias NativeAVIOContext = org.bytedeco.ffmpeg.avformat.AVIOContext

@JvmInline
public actual value class IOContext(
    @PublishedApi
    internal val native: NativeAVIOContext
) : AutoCloseable {
    public actual constructor(filename: String, flags: Int) : this(open(filename, flags).native)

    public actual constructor(bytes: ByteArray) : this(open(bytes).native)

    public constructor(stream: InputStream) : this(open(stream).native)

    public actual override fun close() {
        avio_close(native)
    }

    private companion object {
        private fun open(filename: String, flags: Int): IOContext {
            val nativeAVIOContext = NativeAVIOContext()

            avio_open(nativeAVIOContext, filename, flags).checkError()

            return IOContext(nativeAVIOContext)
        }

        private fun open(stream: InputStream): IOContext {
            val ba = ByteArray(4096)
            var pos = 0

            val read = object : Read_packet_Pointer_BytePointer_int() {
                override fun call(
                    opaque: Pointer?,
                    buf: BytePointer,
                    requestedSize: Int
                ): Int {
                    val bytesRead = stream.read(ba, 0, minOf(requestedSize, ba.size))
                    if (bytesRead == -1) return AVERROR_EOF

                    buf.put(ba, 0, bytesRead)

                    pos += bytesRead
                    return bytesRead
                }
            }

            val avioBuffer = BytePointer(4096)
            val ctx = avio_alloc_context(avioBuffer, 4096, 0, null, read, null, null)
                ?: error("Failed to allocate AVIOContext")

            return IOContext(ctx)
        }
        private fun open(bytes: ByteArray): IOContext {
            val bufferPointer = BytePointer(*bytes)
            val dataSize = bytes.size
            var position = 0

            val read = object : Read_packet_Pointer_BytePointer_int() {
                override fun call(
                    opaque: Pointer,
                    buf: BytePointer,
                    requestedSize: Int
                ): Int {
                    val remaining = dataSize - position
                    val size = minOf(requestedSize, remaining)

                    memcpy(buf, bufferPointer.position(position.toLong()), size.toLong())

                    position += size
                    return size
                }
            }

            val avioBuffer = BytePointer(4096)
            val ctx = avio_alloc_context(avioBuffer, 4096, 0, bufferPointer, read, null, null)

            return IOContext(ctx)
        }
    }
}