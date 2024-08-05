package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.avutil.AVFrame
import dev.zt64.ffmpegkt.avutil.AVPixelFormat
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.swscale.*

public actual typealias SwsContext = org.bytedeco.ffmpeg.swscale.SwsContext

public actual fun SwsContext(
    srcW: Int,
    srcH: Int,
    srcFormat: AVPixelFormat,
    dstW: Int,
    dstH: Int,
    dstFormat: AVPixelFormat,
    flags: Int,
    srcFilter: SwsFilter?,
    dstFilter: SwsFilter?,
    param: DoubleArray?
): SwsContext {
    val context = org.bytedeco.ffmpeg.swscale.SwsContext()

    return sws_getCachedContext(
        context,
        srcW,
        srcH,
        srcFormat.num,
        dstW,
        dstH,
        dstFormat.num,
        flags,
        srcFilter,
        dstFilter,
        param
    )
}

public actual fun SwsContext.scale(
    srcSlice: ByteArray,
    srcStride: IntArray,
    srcSliceY: Int,
    srcSliceH: Int,
    dst: ByteArray,
    dstStride: IntArray
): Int {
    return sws_scale(
        this,
        srcSlice,
        srcStride,
        srcSliceY,
        srcSliceH,
        dst,
        dstStride
    )
}

public actual fun SwsContext.scaleFrame(src: AVFrame, dst: AVFrame) {
    sws_scale_frame(this, src.native, dst.native).checkError()
}