package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.avutil.AVFrame.AV_NUM_DATA_POINTERS
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.PointerPointer

public actual typealias NativeAVFrame = org.bytedeco.ffmpeg.avutil.AVFrame

@JvmInline
public actual value class AudioFrame(
    override val native: NativeAVFrame
) : Frame {
    public actual constructor() : this(av_frame_alloc())

    override val linesize: IntArray
        get() = IntArray(AV_NUM_DATA_POINTERS) { native.linesize(it) }

    override val data: FrameData
        get() = FrameData(native.data(), linesize)

    override var pts: Long
        get() = native.pts()
        set(value) {
            native.pts(value)
        }

    public actual inline var nbSamples: Int
        get() = native.nb_samples()
        set(value) {
            native.nb_samples(value)
        }

    public actual inline var format: AVSampleFormat
        get() = AVSampleFormat(native.format())
        set(value) {
            native.format(value.num)
        }

    public actual inline var channelLayout: AVChannelLayout
        get() = AVChannelLayout(native.ch_layout())
        set(value) {
            value.copyTo(channelLayout)
        }

    public actual inline var sampleRate: Int
        get() = native.sample_rate()
        set(value) {
            native.sample_rate(value)
        }

    override fun getBuffer(align: Int) {
        av_frame_get_buffer(native, align)
    }

    override fun makeWritable() {
        av_frame_make_writable(native)
    }

    override fun close() {
        av_frame_free(native)
    }
}

@JvmInline
public actual value class VideoFrame(
    override val native: NativeAVFrame
) : Frame {
    public actual constructor() : this(av_frame_alloc())

    override val linesize: IntArray
        get() = IntArray(AV_NUM_DATA_POINTERS) { native.linesize(it) }

    override val data: FrameData
        get() = FrameData(native.data(), linesize)

    override var pts: Long
        get() = native.pts()
        set(value) {
            native.pts(value)
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

    public actual inline var format: AVPixelFormat
        get() = AVPixelFormat(native.format())
        set(value) {
            native.format(value.num)
        }

    override fun getBuffer(align: Int) {
        av_frame_get_buffer(native, align)
    }

    override fun makeWritable() {
        av_frame_make_writable(native)
    }

    override fun close() {
        av_frame_free(native)
    }
}

public actual class FrameData(
    private val native: PointerPointer<*>,
    private val linesizes: IntArray
) : AbstractList<FrameData.FrameDataSegment>() {
    override val size: Int = AV_NUM_DATA_POINTERS

    public override operator fun get(index: Int): FrameDataSegment {
        return FrameDataSegment(BytePointer(native.get(index.toLong())), linesizes[index])
    }

    public actual inner class FrameDataSegment(
        private val pointer: BytePointer,
        override val size: Int
    ) : AbstractList<UByte>() {
        public override operator fun get(index: Int): UByte {
            return pointer[index.toLong()].toUByte()
        }

        public actual operator fun set(index: Int, value: UByte) {
            pointer.put(index.toLong(), value.toByte())
        }
    }
}