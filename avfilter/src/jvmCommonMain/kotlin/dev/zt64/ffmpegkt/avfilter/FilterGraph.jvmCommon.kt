package dev.zt64.ffmpegkt.avfilter

import org.bytedeco.ffmpeg.avfilter.AVFilterGraph
import org.bytedeco.ffmpeg.global.avfilter
import org.bytedeco.ffmpeg.global.avfilter.avfilter_graph_alloc

public actual typealias AVFilterGraph = AVFilterGraph

@JvmInline
public actual value class FilterGraph actual constructor(private actual val native: AVFilterGraph) : AutoCloseable {
    public actual constructor() : this(avfilter_graph_alloc())

    public actual fun addFilter(filter: AVFilter) {
        avfilter.avfilter_graph_alloc_filter(native, filter, "")
        TODO()
    }

    actual override fun close() {
        TODO("Not yet implemented")
    }
}

public actual typealias FilterGraph2 = AVFilterGraph

public actual fun FilterGraph2D(): FilterGraph2 {
    return avfilter_graph_alloc()
}

public actual fun FilterGraph2.close() {
    avfilter.avfilter_graph_free(this)
}

public actual val FilterGraph2.threads: Int
    get() = this.nb_threads()