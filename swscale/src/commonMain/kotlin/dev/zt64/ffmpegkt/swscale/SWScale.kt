package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.Library
import dev.zt64.ffmpegkt.avutil.AVPixelFormat

public expect object SWScale : Library {
    public fun getCoefficients(colorspace: Int): Int

    public fun isSupportedInput(pixFmt: AVPixelFormat): Boolean

    public fun isSupportedOutput(pixFmt: AVPixelFormat): Boolean

    public fun isSupportedEndianness(pixFmt: AVPixelFormat): Boolean

    public fun allocContext(): SwsContext

    public fun initContext(
        context: SwsContext,
        srcFilter: SwsFilter,
        dstFilter: SwsFilter
    )

    public fun freeContext(swsContext: SwsContext)
}