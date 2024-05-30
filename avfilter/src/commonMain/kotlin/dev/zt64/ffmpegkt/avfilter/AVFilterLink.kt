package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.avutil.AVMediaType
import dev.zt64.ffmpegkt.avutil.AVRational

public data class AVFilterLink(
    val sourceFilter: AVFilterContext,
    val sourcePad: AVFilterPad,
    val destinationFilter: AVFilterContext,
    val destinationPad: AVFilterPad,
    val type: AVMediaType,
    val width: Int,
    val height: Int,
    val sampleAspectRatio: AVRational
)