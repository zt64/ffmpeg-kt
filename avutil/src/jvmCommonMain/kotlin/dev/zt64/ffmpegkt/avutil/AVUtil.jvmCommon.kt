package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.PointerPointer
import java.nio.ByteBuffer
import java.nio.IntBuffer

public actual object AVUtil : FfmpegLibrary {
    public override fun version(): Int {
        return avutil_version()
    }

    public override fun configuration(): String {
        return avutil_configuration()?.string.orEmpty()
    }

    public override fun license(): String = avutil_license().string

    public actual fun versionInfo(): String = av_version_info().string

    public actual fun getMediaTypeString(type: AVMediaType): String? {
        return av_get_media_type_string(type.num)?.string
    }

    public actual fun getPictureTypeChar(type: AVPictureType): Char {
        return av_get_picture_type_char(type.value).toInt().toChar()
    }

    public actual fun getTimeBaseQ(): AVRational = AVRational(av_get_time_base_q())

    public actual fun setLogLevel(level: Int) {
        av_log_set_level(level)
    }

    public actual fun errorToString(error: Int): String {
        return av_make_error_string(
            ByteArray(AV_ERROR_MAX_STRING_SIZE),
            AV_ERROR_MAX_STRING_SIZE.toLong(),
            error
        ).decodeToString().trimEnd('\u0000')
    }

    public actual fun samplesFillArrays(
        audioData: ByteArray,
        linesize: IntArray,
        buffer: ByteArray,
        channels: Int,
        samples: Int,
        sampleFmt: AVSampleFormat,
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
        sampleFmt: AVSampleFormat,
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
        audioData: ByteArray,
        linesizes: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: AVSampleFormat,
        align: Int
    ): Int = av_samples_alloc_array_and_samples(
        PointerPointer<Pointer>(audioData),
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
        sampleFmt: AVSampleFormat
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
        sampleFmt: AVSampleFormat
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
        pixFmt: AVPixelFormat,
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