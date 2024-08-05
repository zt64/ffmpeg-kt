package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.FfmpegLibrary
import ffmpeg.*
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString

public actual object AVUtil : FfmpegLibrary {
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

    public actual fun getMediaTypeString(type: AVMediaType): String? {
        return av_get_media_type_string(type.value)?.toKString()
    }

    public actual fun getPictureTypeChar(type: AVPictureType): Char {
        return av_get_picture_type_char(type.value.toUInt()).toInt().toChar()
    }

    public actual fun getTimeBaseQ(): AVRational {
        return memScoped {
            av_get_time_base_q().ptr.pointed
        }
    }
}