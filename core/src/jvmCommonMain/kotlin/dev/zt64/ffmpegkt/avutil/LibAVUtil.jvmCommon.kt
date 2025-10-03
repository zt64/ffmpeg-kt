package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avutil.audio.SampleFormat
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.video.PixelFormat
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.javacpp.BytePointer
import org.bytedeco.javacpp.PointerPointer
import java.nio.ByteBuffer
import java.nio.IntBuffer

public actual object LibAVUtil : FfmpegLibrary {
    init {
        // Disable pointer garbage collection
        // This is necessary because the JVM does not know how to handle the pointers allocated by FFmpeg
        System.setProperty("org.bytedeco.javacpp.nopointergc", "true")
    }

    public actual override fun version(): Int = avutil_version()

    public actual override fun configuration(): String {
        return avutil_configuration()?.string.orEmpty()
    }

    public actual override fun license(): String = avutil_license().string

    public actual fun versionInfo(): String = av_version_info().string

    public actual fun getTimeBaseQ(): Rational = Rational(av_get_time_base_q())

    public actual fun errorToString(error: Int): String {
        val ba = ByteArray(AV_ERROR_MAX_STRING_SIZE)
        av_make_error_string(
            ba,
            AV_ERROR_MAX_STRING_SIZE.toLong(),
            error
        )
        return ba.decodeToString().trimEnd('\u0000')
    }

    public actual fun samplesFillArrays(
        audioData: ByteArray,
        linesize: IntArray,
        buffer: ByteArray,
        channels: Int,
        samples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int = av_samples_fill_arrays(
        audioData,
        linesize,
        buffer,
        channels,
        samples,
        sampleFmt.num,
        align
    ).checkError()

    public actual fun samplesAlloc(
        audioData: ByteArray,
        linesize: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int = av_samples_alloc(
        audioData,
        linesize,
        nbChannels,
        nbSamples,
        sampleFmt.num,
        align
    ).checkError()

    public actual fun samplesAllocArrayAndSamples(
        audioData: Array<Array<ByteArray>>,
        linesizes: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int = av_samples_alloc_array_and_samples(
        PointerPointer<PointerPointer<BytePointer>>(
            audioData.map { ba ->
                ba.map {
                    BytePointer(*it)
                }.let { pp ->
                    PointerPointer(*pp.toTypedArray())
                }
            }.let { pp ->
                PointerPointer(*pp.toTypedArray())
            }
        ),
        linesizes,
        nbChannels,
        nbSamples,
        sampleFmt.num,
        align
    ).checkError()

    public actual fun samplesCopy(
        dst: ByteArray,
        src: ByteArray,
        dstOffset: Int,
        srcOffset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: SampleFormat
    ): Int = av_samples_copy(
        dst,
        src,
        dstOffset,
        srcOffset,
        nbSamples,
        nbChannels,
        sampleFmt.num
    ).checkError()

    public actual fun samplesSetSilence(
        audioData: ByteArray,
        offset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: SampleFormat
    ): Int = av_samples_set_silence(
        audioData,
        offset,
        nbSamples,
        nbChannels,
        sampleFmt.num
    ).checkError()

    public actual fun imageAlloc(
        pointers: ByteArray,
        linesizes: IntArray,
        width: Int,
        height: Int,
        pixFmt: PixelFormat,
        align: Int
    ): Int {
        return av_image_alloc(
            ByteBuffer.wrap(pointers),
            IntBuffer.wrap(linesizes),
            width,
            height,
            pixFmt.num,
            align
        ).checkError()
    }
}