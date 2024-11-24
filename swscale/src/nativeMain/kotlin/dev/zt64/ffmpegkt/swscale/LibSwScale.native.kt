package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avutil.PixelFormat
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import ffmpeg.*
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value

public actual object LibSwScale : FfmpegLibrary {
    override fun version(): Int {
        return swscale_version().toInt()
    }

    override fun configuration(): String {
        return swscale_configuration()?.toKString().orEmpty()
    }

    override fun license(): String {
        return swscale_license()?.toKString().orEmpty()
    }

    public actual fun getCoefficients(colorspace: Int): Int {
        return sws_getCoefficients(colorspace)!!.pointed.value
    }

    public actual fun isSupportedInput(pixFmt: PixelFormat): Boolean {
        return sws_isSupportedInput(pixFmt.value).checkTrue()
    }

    public actual fun isSupportedOutput(pixFmt: PixelFormat): Boolean {
        return sws_isSupportedOutput(pixFmt.value).checkTrue()
    }

    public actual fun isSupportedEndianness(pixFmt: PixelFormat): Boolean {
        return sws_isSupportedEndiannessConversion(pixFmt.value).checkTrue()
    }

    public actual fun allocContext(): SwsContext {
        // return sws_alloc_context()?.pointed
        TODO()
    }

    public actual fun initContext(
        context: SwsContext,
        srcFilter: SwsFilter,
        dstFilter: SwsFilter
    ) {
        // sws_init_context(context, srcFilter, dstFilter)
    }

    public actual fun freeContext(swsContext: SwsContext) {
        // sws_freeContext(swsContext)
    }
}