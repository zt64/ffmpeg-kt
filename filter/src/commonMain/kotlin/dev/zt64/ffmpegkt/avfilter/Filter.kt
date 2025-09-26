package dev.zt64.ffmpegkt.avfilter

internal expect class AVFilter

public class Filter(public val name: String)

public class FilterBuilder(private val name: String) {
    private val filter = Filter(name)

    /**
     * Add an option
     */
    public fun option(key: String, value: String) {
    }

    /**
     * Add an option
     */
    public fun option(key: String, value: Number) {
    }

    public fun input(stream: FilterOutput) {
    }

    public fun input(input: FilterOutput, block: InputBuilder.() -> FilterOutput) {
    }

    public fun output(filter: Filter) {
    }

    public fun output(block: OutputBuilder.() -> Unit): FilterOutput {
        TODO()
    }

    public fun x(x: Int) {
    }

    public fun y(y: Int) {
    }

    public fun width(width: Int) {
    }

    public fun height(height: Int) {
    }

    internal fun build(): Filter = filter
}