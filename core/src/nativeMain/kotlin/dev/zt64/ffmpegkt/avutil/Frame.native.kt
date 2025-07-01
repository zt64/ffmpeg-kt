package dev.zt64.ffmpegkt.avutil

import ffmpeg.*
import kotlinx.cinterop.*
import platform.posix.memcpy

internal actual typealias NativeAVFrame = AVFrame

public actual open class Frame internal constructor(public open val native: NativeAVFrame) : AutoCloseable {
    public actual val linesize: IntArray
        get() = IntArray(AV_NUM_DATA_POINTERS) { native.linesize[it] }
    public actual val data: FrameData
        get() = FrameData(native)

    public actual var pts: Long
        get() = native.pts
        set(value) {
            native.pts = value
        }

    public actual var timeBase: Rational
        get() = Rational(native.time_base)
        set(value) {
            native.time_base.num = value.num
            native.time_base.den = value.den
        }

    public actual fun getBuffer(align: Int) {
        av_frame_get_buffer(native.ptr, align)
    }

    public actual fun makeWritable() {
        av_frame_make_writable(native.ptr)
    }

    actual final override fun close() {
        av_frame_free(cValuesOf(native.ptr))
    }
}

public actual class AudioFrame internal constructor(override val native: NativeAVFrame) : Frame(native) {
    public actual constructor() : this(av_frame_alloc()!!.pointed)
    public actual constructor(nbSamples: Int, format: SampleFormat, channelLayout: ChannelLayout) : this(av_frame_alloc()!!.pointed) {
        this.nbSamples = nbSamples
        this.format = format
        this.channelLayout = channelLayout

        getBuffer()
    }

    public actual inline var nbSamples: Int
        get() = native.nb_samples
        set(value) {
            native.nb_samples = value
        }
    public actual inline var format: SampleFormat
        get() = SampleFormat(native.format)
        set(value) {
            native.format = value.num
        }
    public actual inline var channelLayout: ChannelLayout
        get() = ChannelLayout(native.ch_layout)
        set(value) {
            value.copyTo(channelLayout)
        }
    public actual inline var sampleRate: Int
        get() = native.sample_rate
        set(value) {
            native.sample_rate = value
        }
}

public actual class VideoFrame internal constructor(override val native: NativeAVFrame) : Frame(native) {
    public actual constructor() : this(av_frame_alloc()!!.pointed)
    public actual constructor(width: Int, height: Int, format: PixelFormat) : this(av_frame_alloc()!!.pointed) {
        this.width = width
        this.height = height
        this.format = format

        getBuffer()
    }

    public actual inline var width: Int
        get() = native.width
        set(value) {
            native.width = value
        }
    public actual inline var height: Int
        get() = native.height
        set(value) {
            native.height = value
        }
    public actual inline var format: PixelFormat
        get() = PixelFormat(native.format)
        set(value) {
            native.format = value.num
        }
}

public actual class FrameData internal constructor(private val native: NativeAVFrame) : AbstractList<FrameData.FrameDataSegment>() {
    public actual override val size: Int = AV_NUM_DATA_POINTERS

    private val segments = arrayOfNulls<FrameDataSegment>(AV_NUM_DATA_POINTERS)

    public actual override operator fun get(index: Int): FrameDataSegment {
        if (index !in 0..<size) throw IndexOutOfBoundsException("Index: $index, Size: $size")

        return segments[index] ?: run {
            val pointer = native.data[index.toLong()]!!
            val newSegment = FrameDataSegment(pointer, calculatePlaneSize(index))
            segments[index] = newSegment
            newSegment
        }
    }

    public actual operator fun set(index: Int, value: ByteArray) {
        if (index !in 0..<size) throw IndexOutOfBoundsException("Index: $index, Size: $size")

        val segment = get(index)
        segment.put(value, 0, value.size)
    }

    private fun calculatePlaneSize(index: Int): Int {
        val linesize = native.linesize[index]
        if (linesize == 0) return 0
        return linesize * native.height
    }

    public actual class FrameDataSegment(private val cPointer: CPointer<UByteVar>, actual override val size: Int) : AbstractList<UByte>() {
        public actual operator fun set(index: Int, value: UByte) {
            cPointer[index] = value
        }

        public actual override operator fun get(index: Int): UByte = cPointer[index]
        public actual fun put(bytes: ByteArray, offset: Int, length: Int) {
            bytes.usePinned { pinned ->
                memcpy(cPointer, pinned.addressOf(offset), length.toULong())
            }
        }
    }
}