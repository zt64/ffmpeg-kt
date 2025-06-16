package dev.zt64.ffmpegkt.avfilter

import ffmpeg.avfilter_graph_alloc
import ffmpeg.avfilter_graph_free
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public actual typealias AVFilterGraph = ffmpeg.AVFilterGraph

public actual value class FilterGraph actual constructor(public actual val native: AVFilterGraph) : AutoCloseable {
    public actual constructor() : this(avfilter_graph_alloc()!!.pointed)

    public actual fun addFilter(filter: Filter) {
        // avfilter_graph_alloc_filter(native.ptr, filter, "")
        TODO()
    }

    actual override fun close() {
        avfilter_graph_free(cValuesOf(native.ptr))
    }
}