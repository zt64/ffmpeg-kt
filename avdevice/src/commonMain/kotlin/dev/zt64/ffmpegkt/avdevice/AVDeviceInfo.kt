package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.avutil.MediaType

public data class AVDeviceInfo(
    val name: String,
    val description: String,
    val mediaTypes: List<MediaType>
)