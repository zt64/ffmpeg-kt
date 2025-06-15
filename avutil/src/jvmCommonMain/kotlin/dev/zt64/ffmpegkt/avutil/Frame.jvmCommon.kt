package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.avutil.AVFrame.AV_NUM_DATA_POINTERS
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.javacpp.BytePointer

internal actual typealias NativeAVFrame = org.bytedeco.ffmpeg.avutil.AVFrame

public actual open class Frame(public open val native: NativeAVFrame) : AutoCloseable {
    public constructor() : this(av_frame_alloc())

    public actual val linesize: IntArray
        get() = IntArray(AV_NUM_DATA_POINTERS) { native.linesize(it) }
    public actual val data: FrameData
        get() = FrameData(native)

    public actual var pts: Long
        get() = native.pts()
        set(value) {
            native.pts(value)
        }

    public actual fun getBuffer(align: Int) {
        av_frame_get_buffer(native, align)
    }

    public actual fun makeWritable() {
        av_frame_make_writable(native)
    }

    actual final override fun close() {
        av_frame_free(native)
    }
}

public actual class AudioFrame(override val native: NativeAVFrame) : Frame(native) {
    public actual constructor() : this(av_frame_alloc())
    public actual constructor(nbSamples: Int, format: SampleFormat, channelLayout: ChannelLayout) : this(av_frame_alloc()) {
        this.nbSamples = nbSamples
        this.format = format
        this.channelLayout = channelLayout

        getBuffer()
    }

    public actual inline var nbSamples: Int
        get() = native.nb_samples()
        set(value) {
            native.nb_samples(value)
        }

    public actual inline var format: SampleFormat
        get() = SampleFormat(native.format())
        set(value) {
            native.format(value.num)
        }

    public actual inline var channelLayout: ChannelLayout
        get() = ChannelLayout(native.ch_layout())
        set(value) {
            value.copyTo(channelLayout)
        }

    public actual inline var sampleRate: Int
        get() = native.sample_rate()
        set(value) {
            native.sample_rate(value)
        }
}

public actual class VideoFrame(override val native: NativeAVFrame) : Frame(native) {
    public actual constructor() : this(av_frame_alloc())
    public actual constructor(width: Int, height: Int, format: PixelFormat) : this(av_frame_alloc()) {
        this.width = width
        this.height = height
        this.format = format

        getBuffer()
    }

    public actual inline var width: Int
        get() = native.width()
        set(value) {
            native.width(value)
        }
    public actual inline var height: Int
        get() = native.height()
        set(value) {
            native.height(value)
        }

    public actual inline var format: PixelFormat
        get() = PixelFormat(native.format())
        set(value) {
            native.format(value.num)
        }
}

public actual class FrameData(private val native: NativeAVFrame) : AbstractList<FrameData.FrameDataSegment>() {
    public actual override val size: Int = AV_NUM_DATA_POINTERS

    public actual override operator fun get(index: Int): FrameDataSegment {
        val pointer = BytePointer(native.data().get(index.toLong()))
        return FrameDataSegment(pointer, calculatePlaneSize(index))
    }

    private fun calculatePlaneSize(index: Int): Int {
        return av_image_get_linesize(native.format(), native.width(), index) * native.height()
    }

    public actual inner class FrameDataSegment(
        private val pointer: BytePointer,
        actual override val size: Int
    ) : AbstractList<UByte>() {
        public actual override operator fun get(index: Int): UByte {
            return pointer[index.toLong()].toUByte()
        }

        public actual operator fun set(index: Int, value: UByte) {
            pointer.put(index.toLong(), value.toByte())
        }
    }
}