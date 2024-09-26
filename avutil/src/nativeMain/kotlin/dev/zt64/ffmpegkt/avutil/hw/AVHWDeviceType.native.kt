package dev.zt64.ffmpegkt.avutil.hw

import ffmpeg.av_hwdevice_find_type_by_name
import ffmpeg.av_hwdevice_get_type_name
import kotlinx.cinterop.toKString

public actual inline val AVHWDeviceType.name: String?
    get() = av_hwdevice_get_type_name(num.toUInt())?.toKString()

public actual fun AVHWDeviceType.Companion.findTypeByName(name: String): AVHWDeviceType? {
    return av_hwdevice_find_type_by_name(name).takeUnless { it.toInt() == NONE.num }
        ?.let { AVHWDeviceType(it.toInt()) }
}