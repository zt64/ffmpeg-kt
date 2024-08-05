package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.avutil.AVFrame
import dev.zt64.ffmpegkt.avutil.AVPixelFormat

public expect class SwsContext : AutoCloseable

public expect fun SwsContext(
    srcW: Int,
    srcH: Int,
    srcFormat: AVPixelFormat,
    dstW: Int,
    dstH: Int,
    dstFormat: AVPixelFormat,
    flags: Int,
    srcFilter: SwsFilter? = null,
    dstFilter: SwsFilter? = null,
    param: DoubleArray? = null
): SwsContext

public fun SwsContext(
    srcW: Int,
    srcH: Int,
    srcFormat: AVPixelFormat,
    dstW: Int,
    dstH: Int,
    dstFormat: AVPixelFormat,
    flags: List<SwsFlag>,
    srcFilter: SwsFilter? = null,
    dstFilter: SwsFilter? = null,
    param: DoubleArray? = null
): SwsContext {
    return SwsContext(
        srcW = srcW,
        srcH = srcH,
        srcFormat = srcFormat,
        dstW = dstW,
        dstH = dstH,
        dstFormat = dstFormat,
        flags = flags.fold(0) { acc, flag -> acc or flag.value },
        srcFilter = srcFilter,
        dstFilter = dstFilter,
        param = param
    )
}

public expect fun SwsContext.scale(
    srcSlice: ByteArray,
    srcStride: IntArray,
    srcSliceY: Int,
    srcSliceH: Int,
    dst: ByteArray,
    dstStride: IntArray
): Int

public expect fun SwsContext.scaleFrame(src: AVFrame, dst: AVFrame)