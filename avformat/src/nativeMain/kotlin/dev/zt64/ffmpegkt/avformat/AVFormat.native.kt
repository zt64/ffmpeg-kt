package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avutil.AVDictionary
import dev.zt64.ffmpegkt.avutil.AVDictionaryNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.*
import kotlinx.cinterop.*

public actual object AVFormat : FfmpegLibrary {
    override fun version(): Int = avformat_version().toInt()

    override fun configuration(): String {
        return avformat_configuration()?.toKString().orEmpty()
    }

    override fun license(): String {
        return avformat_license()?.toKString().orEmpty()
    }

    public actual fun networkInit(): Int = avformat_network_init()

    public actual fun networkDeinit(): Int = avformat_network_deinit()

    public actual fun openInput(
        url: String,
        format: AVInputFormat?,
        options: AVDictionary?
    ): AVFormatContext {
        val formatContext = avformat_alloc_context()
            ?: error("avformat_alloc_context returned null")

        memScoped {
            avformat_open_input(
                ps = cValuesOf(formatContext),
                url = url,
                fmt = format?.ptr,
                options = cValuesOf(options?.let(::AVDictionaryNative)?.ptr)
            ).checkError()
        }

        return formatContext.pointed
    }

    public actual fun allocOutputContext(
        format: AVOutputFormat?,
        formatName: String?,
        filename: String
    ): AVFormatContext {
        val formatContext = avformat_alloc_context()
            ?: error("avformat_alloc_context returned null")

        memScoped {
            avformat_alloc_output_context2(
                ctx = cValuesOf(formatContext),
                oformat = format?.ptr,
                format_name = formatName,
                filename = filename
            ).checkError()
        }

        return formatContext.pointed
    }

    public actual fun allocOutputContext(filename: String): AVFormatContext {
        return allocOutputContext(null, null, filename)
    }
}