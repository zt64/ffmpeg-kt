package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.Library
import org.bytedeco.ffmpeg.global.avutil.*

public actual object AVUtil : Library {
    public override fun version(): Int {
        return avutil_version()
    }

    public override fun configuration(): String {
        return avutil_configuration()?.string.orEmpty()
    }

    public override fun license(): String = avutil_license().string

    public actual fun versionInfo(): String = av_version_info().string

    public actual fun getMediaTypeString(type: AVMediaType): String? {
        return av_get_media_type_string(type.value)?.string
    }

    public actual fun getPictureTypeChar(type: AVPictureType): Char {
        return av_get_picture_type_char(type.value).toInt().toChar()
    }

    public actual fun getTimeBaseQ(): AVRational = av_get_time_base_q()

    public actual fun dictIterate(
        dict: AVDictionary,
        prev: AVDictionaryEntry?
    ): AVDictionaryEntry? = av_dict_iterate(dict, prev)
}