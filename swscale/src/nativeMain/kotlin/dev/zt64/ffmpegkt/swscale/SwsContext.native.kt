package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.avutil.PixelFormat
import dev.zt64.ffmpegkt.avutil.Frame
import ffmpeg.sws_scale_frame
import kotlinx.cinterop.ptr

public actual typealias SwsContext = cnames.structs.SwsContext

public actual fun SwsContext(
    srcW: Int,
    srcH: Int,
    srcFormat: PixelFormat,
    dstW: Int,
    dstH: Int,
    dstFormat: PixelFormat,
    flags: Int,
    srcFilter: SwsFilter?,
    dstFilter: SwsFilter?,
    param: DoubleArray?
): SwsContext {
    TODO("Not yet implemented")
}

public actual fun SwsContext.scale(
    srcSlice: ByteArray,
    srcStride: IntArray,
    srcSliceY: Int,
    srcSliceH: Int,
    dst: ByteArray,
    dstStride: IntArray
): Int {
    TODO("Not yet implemented")
}

public actual fun SwsContext.scaleFrame(src: Frame, dst: Frame) {
    sws_scale_frame(ptr, src.native.ptr, dst.native.ptr)
}