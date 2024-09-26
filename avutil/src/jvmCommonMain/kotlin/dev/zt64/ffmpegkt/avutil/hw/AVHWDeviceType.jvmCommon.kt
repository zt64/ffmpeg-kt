package dev.zt64.ffmpegkt.avutil.hw

import org.bytedeco.ffmpeg.global.avutil.av_hwdevice_find_type_by_name
import org.bytedeco.ffmpeg.global.avutil.av_hwdevice_get_type_name

public actual inline val AVHWDeviceType.name: String?
    get() = av_hwdevice_get_type_name(num)?.string

public actual fun AVHWDeviceType.Companion.findTypeByName(name: String): AVHWDeviceType? {
    return av_hwdevice_find_type_by_name(name).takeUnless { it == NONE.num }
        ?.let { AVHWDeviceType(it) }
}