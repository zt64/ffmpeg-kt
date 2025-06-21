package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avutil.PixelFormat

public expect object LibSwScale : FfmpegLibrary {
    public override fun version(): Int
    public override fun configuration(): String
    public override fun license(): String

    public fun getCoefficients(colorspace: Int): Int
    public fun isSupportedInput(pixFmt: PixelFormat): Boolean
    public fun isSupportedOutput(pixFmt: PixelFormat): Boolean
    public fun isSupportedEndianness(pixFmt: PixelFormat): Boolean
    public fun allocContext(): SwsContext

    public fun initContext(
        context: SwsContext,
        srcFilter: SwsFilter,
        dstFilter: SwsFilter
    )

    public fun freeContext(swsContext: SwsContext)
}