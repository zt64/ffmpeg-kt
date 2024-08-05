package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avformat.avio_close
import org.bytedeco.ffmpeg.global.avformat.avio_open

public typealias NativeAVIOContext = org.bytedeco.ffmpeg.avformat.AVIOContext

@JvmInline
public actual value class AVIOContext(internal val native: NativeAVIOContext) : AutoCloseable {
    public actual constructor(filename: String, flags: Int) : this(open(filename, flags).native)

    override fun close() {
        avio_close(native)
    }

    private companion object {
        private fun open(filename: String, flags: Int): AVIOContext {
            val nativeAVIOContext = NativeAVIOContext()

            avio_open(nativeAVIOContext, filename, flags).checkError()

            return AVIOContext(nativeAVIOContext)
        }
    }
}