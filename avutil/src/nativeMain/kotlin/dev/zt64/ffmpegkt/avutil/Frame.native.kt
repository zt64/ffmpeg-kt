package dev.zt64.ffmpegkt.avutil

import ffmpeg.*
import kotlinx.cinterop.*

public actual typealias NativeAVFrame = AVFrame

public actual value class AudioFrame actual constructor(override val native: NativeAVFrame) : Frame {
    public actual constructor() : this(av_frame_alloc()!!.pointed)

    override val linesize: IntArray
        get() = IntArray(AV_NUM_DATA_POINTERS) { native.linesize[it] }
    override val data: FrameData
        get() = FrameData(native.data)

    override var pts: Long
        get() = native.pts
        set(value) {
            native.pts = value
        }

    public actual inline var nbSamples: Int
        get() = native.nb_samples
        set(value) {
            native.nb_samples = value
        }
    public actual inline var format: AVSampleFormat
        get() = AVSampleFormat(native.format)
        set(value) {
            native.format = value.num
        }
    public actual inline var channelLayout: AVChannelLayout
        get() = AVChannelLayout(native.ch_layout)
        set(value) {
            value.copyTo(channelLayout)
        }
    public actual inline var sampleRate: Int
        get() = native.sample_rate
        set(value) {
            native.sample_rate = value
        }

    override fun getBuffer(align: Int) {
        av_frame_get_buffer(native.ptr, align)
    }

    override fun makeWritable() {
        av_frame_make_writable(native.ptr)
    }

    override fun close() {
        av_frame_free(cValuesOf(native.ptr))
    }
}

public actual value class VideoFrame(override val native: NativeAVFrame) : Frame {
    public actual constructor() : this(av_frame_alloc()!!.pointed)

    override val linesize: IntArray
        get() = IntArray(AV_NUM_DATA_POINTERS) { native.linesize[it] }
    override val data: FrameData
        get() = FrameData(native.data)

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
    public actual inline var format: AVPixelFormat
        get() = AVPixelFormat(native.format)
        set(value) {
            native.format = value.num
        }

    override var pts: Long
        get() = native.pts
        set(value) {
            native.pts = value
        }

    override fun getBuffer(align: Int) {
        av_frame_get_buffer(native.ptr, align)
    }

    override fun makeWritable() {
        av_frame_make_writable(native.ptr)
    }

    override fun close() {
        av_frame_free(cValuesOf(native.ptr))
    }
}

public actual class FrameData(private val array: CArrayPointer<CPointerVar<UByteVar>>) : AbstractList<FrameData.FrameDataSegment>() {
    override val size: Int = AV_NUM_DATA_POINTERS

    override fun get(index: Int): FrameDataSegment {
        return FrameDataSegment(array[index]!!)
    }

    public actual inner class FrameDataSegment(private val cPointer: CPointer<UByteVar>) : Iterable<UByte> {
        public actual operator fun set(index: Int, value: UByte) {
            cPointer[index] = value
        }

        public actual operator fun get(index: Int): UByte {
            return cPointer[index]
        }

        override fun iterator(): Iterator<UByte> {
            TODO()
        }
    }
}