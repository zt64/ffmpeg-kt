package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.avutil.MediaType
import dev.zt64.ffmpegkt.avutil.Rational

public data class AVFilterLink(
    val sourceFilter: AVFilterContext,
    val sourcePad: AVFilterPad,
    val destinationFilter: AVFilterContext,
    val destinationPad: AVFilterPad,
    val type: MediaType,
    val width: Int,
    val height: Int,
    val sampleAspectRatio: Rational
)