package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.*
import kotlinx.cinterop.*

public actual object LibAVUtil : FfmpegLibrary {
    override fun version(): Int = avutil_version().toInt()

    override fun configuration(): String {
        return avutil_configuration()?.toKString().orEmpty()
    }

    override fun license(): String {
        return avutil_license()?.toKString().orEmpty()
    }

    public actual fun versionInfo(): String {
        return av_version_info()?.toKString().orEmpty()
    }

    public actual fun getTimeBaseQ(): Rational {
        return av_get_time_base_q().useContents { Rational(this) }
    }

    public actual fun setLogLevel(level: LogLevel) {
        av_log_set_level(level.value)
    }

    public actual fun errorToString(error: Int): String {
        return memScoped {
            av_make_error_string(
                alloc<ByteVar>().ptr,
                AV_ERROR_MAX_STRING_SIZE.toULong(),
                error
            )!!.toKString().trimEnd('\u0000')
        }
    }

    public actual fun samplesFillArrays(
        audioData: ByteArray,
        linesize: IntArray,
        buffer: ByteArray,
        channels: Int,
        samples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int {
        return memScoped {
            av_samples_fill_arrays(
                cValuesOf(audioData.toUByteArray().toCValues().ptr),
                linesize.toCValues(),
                buffer.toUByteArray().toCValues(),
                channels,
                samples,
                sampleFmt.num,
                align
            ).checkError()
        }
    }

    public actual fun samplesAlloc(
        audioData: ByteArray,
        linesize: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int = memScoped {
        av_samples_alloc(
            cValuesOf(audioData.asUByteArray().toCValues().ptr),
            linesize.toCValues(),
            nbChannels,
            nbSamples,
            sampleFmt.num,
            align
        ).checkError()
    }

    public actual fun samplesAllocArrayAndSamples(
        audioData: Array<Array<ByteArray>>,
        linesizes: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int = memScoped {
        av_samples_alloc_array_and_samples(
            audioData.map {
                it.map { ba ->
                    ba.toUByteArray().toCValues().ptr
                }.toCValues().ptr
            }.toCValues().ptr,
            linesizes.toCValues(),
            nbChannels,
            nbSamples,
            sampleFmt.num,
            align
        ).checkError()
    }

    public actual fun samplesCopy(
        dst: ByteArray,
        src: ByteArray,
        dstOffset: Int,
        srcOffset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: SampleFormat
    ): Int = memScoped {
        av_samples_copy(
            cValuesOf(dst.toUByteArray().toCValues().ptr),
            cValuesOf(src.toUByteArray().toCValues().ptr),
            dstOffset,
            srcOffset,
            nbSamples,
            nbChannels,
            sampleFmt.num
        ).checkError()
    }

    public actual fun samplesSetSilence(
        audioData: ByteArray,
        offset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: SampleFormat
    ): Int = memScoped {
        av_samples_set_silence(
            cValuesOf(audioData.toUByteArray().toCValues().ptr),
            offset,
            nbSamples,
            nbChannels,
            sampleFmt.num
        ).checkError()
    }

    public actual fun imageAlloc(
        pointers: ByteArray,
        linesizes: IntArray,
        width: Int,
        height: Int,
        pixFmt: PixelFormat,
        align: Int
    ): Int = memScoped {
        av_image_alloc(
            cValuesOf(pointers.toUByteArray().toCValues().ptr),
            linesizes.toCValues(),
            width,
            height,
            pixFmt.num,
            align
        ).checkError()
    }
}