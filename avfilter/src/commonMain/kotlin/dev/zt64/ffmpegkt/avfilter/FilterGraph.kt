package dev.zt64.ffmpegkt.avfilter

public expect class AVFilterGraph

public expect value class FilterGraph(private val native: AVFilterGraph) : AutoCloseable {
    public constructor()

    public fun addFilter(filter: AVFilter)

    override fun close()
}

public fun FilterGraph(block: FilterGraph.() -> Unit): FilterGraph {
    val graph = FilterGraph()
    graph.block()
    return graph
}