package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.avio_open
import kotlinx.cinterop.*

public actual typealias NativeAVIOContext = ffmpeg.AVIOContext

public actual value class AVIOContext(public val native: NativeAVIOContext) : AutoCloseable {
    public actual constructor(filename: String, flags: Int) : this(open(filename, flags).native)

    override fun close() {
        TODO("Not yet implemented")
    }

    private companion object {
        private fun open(filename: String, flags: Int): AVIOContext {
            memScoped {
                val nativeAVIOContext = alloc<NativeAVIOContext>()

                avio_open(cValuesOf(nativeAVIOContext.ptr), filename, flags).checkError()

                return AVIOContext(nativeAVIOContext)
            }
        }
    }
}