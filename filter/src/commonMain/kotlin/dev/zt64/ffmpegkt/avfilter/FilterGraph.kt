package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.avutil.Frame

public expect class AVFilterGraph

public expect value class FilterGraph(private val native: AVFilterGraph) : AutoCloseable {
    public constructor()

    internal fun addFilter(filter: Filter)

    public fun filter(frame: Frame): Frame

    override fun close()
}

public fun FilterGraph(block: FilterGraphBuilder.() -> Unit): FilterGraph {
    val graph = FilterGraph()
    val builder = FilterGraphBuilder().apply(block)
    builder.build().forEach { graph.addFilter(it) }
    return graph
}

public class FilterGraphBuilder {
    private val filters = mutableListOf<Filter>()

    /**
     * Add a filter to the graph
     *
     * @param name The name of the filter to add
     */
    public fun filter(name: String, block: FilterBuilder.() -> Unit = {}): Filter {
        val builder = FilterBuilder(name)
        builder.block()
        val filter = builder.build()
        filters += filter
        return filter
    }

    public fun filter(
        name: String,
        input: FilterOutput,
        block: FilterBuilder.() -> Unit = {}
    ): Filter {
        val builder = FilterBuilder(name)
        builder.block()
        val filter = builder.build()
        filters += filter
        return filter
    }

    internal fun build(): List<Filter> = filters
}

public class FilterStream {
    internal val filters = mutableListOf<Filter>()
}

public fun FilterStream.filter(name: String, block: FilterBuilder.() -> Unit = {}): Filter {
    val builder = FilterBuilder(name)
    builder.block()
    val filter = builder.build()
    filters += filter
    return filter
}

public class FilterOutput {
    internal val filters = mutableListOf<Filter>()

    public fun filter(name: String, block: FilterBuilder.() -> Unit = {}): FilterOutput {
        val builder = FilterBuilder(name)
        builder.block()
        filters += builder.build()
        return this
    }
}

public fun interface InputBuilder {
    public fun addInput(filter: Filter)
}

public fun interface OutputBuilder {
    public fun build(): FilterOutput
}