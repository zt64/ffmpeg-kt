package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.*
import ffmpeg.AVIOContext
import kotlinx.cinterop.*
import platform.posix.memcpy

public actual typealias NativeAVIOContext = AVIOContext

public actual value class AVIOContext(public val native: NativeAVIOContext) : AutoCloseable {
    public actual constructor(filename: String, flags: Int) : this(open(filename, flags))
    public actual constructor(bytes: ByteArray) : this(open(bytes))

    actual override fun close() {
        avio_close(native.ptr)
    }

    private companion object {
        private fun open(filename: String, flags: Int): NativeAVIOContext {
            return nativeHeap.alloc<NativeAVIOContext> {
                avio_open(cValuesOf(ptr), filename, flags).checkError()
            }
        }

        private fun open(bytes: ByteArray): NativeAVIOContext {
            val buffer = bytes.asUByteArray().toCValues().getPointer(scope = ArenaBase())
            val bufferDataRef = StableRef.create(BufferData(buffer, bytes.size))

            val avioCtxBuffer = av_malloc(4096u)!!.reinterpret<UByteVar>()

            val ctx = avio_alloc_context(
                avioCtxBuffer,
                4096,
                0,
                bufferDataRef.asCPointer(),
                staticCFunction { opaque, buf, size ->
                    val bd = opaque!!.asStableRef<BufferData>().get()
                    val bufferSize = minOf(size, bd.size)

                    if (bufferSize == 0) return@staticCFunction -1

                    memcpy(buf, bd.ptr, bufferSize.toULong())

                    bd.ptr = bd.ptr.plus(bufferSize.toLong())!!
                    bd.size -= bufferSize

                    bufferSize
                },
                null,
                null
            )

            // bufferDataRef.dispose()

            return ctx!!.pointed
        }
    }
}

private data class BufferData(
    var ptr: CPointer<UByteVar>,
    var size: Int
)