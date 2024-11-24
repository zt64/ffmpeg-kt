package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avutil.PixelFormat
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import org.bytedeco.ffmpeg.global.swscale.*

public actual object LibSwScale : FfmpegLibrary {
    public override fun version(): Int = swscale_version()

    override fun configuration(): String = swscale_configuration().string

    override fun license(): String = swscale_license().string

    public actual fun getCoefficients(colorspace: Int): Int {
        return sws_getCoefficients(colorspace).get()
    }

    public actual fun isSupportedInput(pixFmt: PixelFormat): Boolean {
        return sws_isSupportedInput(pixFmt.num).checkTrue()
    }

    public actual fun isSupportedOutput(pixFmt: PixelFormat): Boolean {
        return sws_isSupportedOutput(pixFmt.num).checkTrue()
    }

    public actual fun isSupportedEndianness(pixFmt: PixelFormat): Boolean {
        return sws_isSupportedEndiannessConversion(pixFmt.num).checkTrue()
    }

    public actual fun allocContext(): SwsContext {
        return sws_alloc_context()
    }

    public actual fun initContext(
        context: SwsContext,
        srcFilter: SwsFilter,
        dstFilter: SwsFilter
    ) {
        sws_init_context(context, srcFilter, dstFilter)
    }

    public actual fun freeContext(swsContext: SwsContext) {
        sws_freeContext(swsContext)
    }
}