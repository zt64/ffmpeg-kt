package dev.zt64.ffmpegkt.avfilter

public data class AVFilterParams(
    val filter: AVFilterContext,
    val filterName: String,
    val instanceName: String
)