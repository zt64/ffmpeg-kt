package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.avformat.Read_packet_Pointer_BytePointer_int
import org.bytedeco.ffmpeg.global.avformat.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.Pointer

public actual typealias NativeAVIOContext = org.bytedeco.ffmpeg.avformat.AVIOContext

@JvmInline
public actual value class AVIOContext(
    @PublishedApi
    internal val native: NativeAVIOContext
) : AutoCloseable {
    public actual constructor(filename: String, flags: Int) : this(open(filename, flags).native)

    public actual constructor(bytes: ByteArray) : this(open(bytes).native)

    public actual override fun close() {
        avio_close(native)
    }

    private companion object {
        private fun open(filename: String, flags: Int): AVIOContext {
            val nativeAVIOContext = dev.zt64.ffmpegkt.container.NativeAVIOContext()

            avio_open(nativeAVIOContext, filename, flags).checkError()

            return AVIOContext(nativeAVIOContext)
        }

        private fun open(bytes: ByteArray): AVIOContext {
            val bufferPointer = BytePointer(*bytes)
            val bufferSize = bytes.size

            val read = object : Read_packet_Pointer_BytePointer_int() {
                override fun call(
                    opaque: Pointer?,
                    buf: BytePointer?,
                    buf_size: Int
                ): Int {
                    val size = minOf(buf_size, bufferSize)
                    buf?.put(size.toLong(), bufferPointer.get())
                    return size
                }
            }

            val ctx = avio_alloc_context(bufferPointer, bufferSize, 0, null, read, null, null)

            return AVIOContext(ctx)
        }
    }
}