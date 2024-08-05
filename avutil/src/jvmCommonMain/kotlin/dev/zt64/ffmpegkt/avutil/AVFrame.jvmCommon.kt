package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.PointerPointer

public actual typealias NativeAVFrame = org.bytedeco.ffmpeg.avutil.AVFrame

@JvmInline
public actual value class AVFrame(public val native: NativeAVFrame) : AutoCloseable {
    public actual constructor() : this(av_frame_alloc())

    public actual val linesize: IntArray
        get() {
            return IntArray(8) { native.linesize(it) }
        }

    public actual val data: AVFrameData
        get() {
            return AVFrameData(native.data(), linesize, IntArray(8) { native.height() })
        }

    public actual var width: Int
        get() = native.width()
        set(value) {
            native.width(value)
        }
    public actual var height: Int
        get() = native.height()
        set(value) {
            native.height(value)
        }
    public actual var nbSamples: Int
        get() = native.nb_samples()
        set(value) {
            native.nb_samples(value)
        }
    public actual var format: AVPixelFormat
        get() = AVPixelFormat(native.format())
        set(value) {
            native.format(value.num)
        }
    public actual var channelLayout: AVChannelLayout
        get() = AVChannelLayout(native.ch_layout())
        set(value) {
            native.ch_layout(value.native)
        }
    public actual var pts: Long
        get() = native.pts()
        set(value) {
            native.pts(value)
        }

    override fun close() {
        av_frame_free(native)
    }

    public actual fun getBuffer(align: Int) {
        av_frame_get_buffer(native, align).checkError()
    }

    public actual fun makeWritable() {
        av_frame_make_writable(native).checkError()
    }
}

public actual class AVFrameData(
    private val native: PointerPointer<*>,
    private val linesize: IntArray,
    private val height: IntArray
) {
    public actual operator fun get(index: Int): AVFrameDataSegment {
        return AVFrameDataSegment(BytePointer(native.get(index.toLong())))
    }

    public actual inner class AVFrameDataSegment(private val pointer: BytePointer) {
        public actual operator fun get(index: Int): Byte {
            return pointer.get(index.toLong())
        }

        public actual operator fun set(index: Int, value: Byte) {
            pointer.put(index.toLong(), value)
        }
    }
}