package dev.zt64.ffmpegkt.avfilter

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.hw.HWDeviceContext

internal expect class NativeAVFilterContext

public expect value class AVFilterContext internal constructor(internal val native: NativeAVFilterContext) : AutoCloseable {
    public val name: String
    public val width: Int
    public val height: Int

    public val filter: AVFilter
    public val graph: AVFilterGraph

    public val inputPads: List<AVFilterPad>
    public val inputs: List<AVFilterLink>
    public val outputs: List<AVFilterLink>

    public var hwDeviceCtx: HWDeviceContext?
    public var extraHwFrames: Int

    public var threads: Int
    public var threadType: Int

    public fun getFrame(): Frame
    public fun getFrame(flags: Int): Frame
    public fun getSamples(samples: Int): Frame

    public fun setFrameSize(size: Int)

    override fun close()
}