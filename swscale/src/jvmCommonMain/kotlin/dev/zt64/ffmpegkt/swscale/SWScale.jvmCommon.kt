package dev.zt64.ffmpegkt.swscale

import dev.zt64.ffmpegkt.Library
import dev.zt64.ffmpegkt.avutil.AVPixelFormat
import dev.zt64.ffmpegkt.checkTrue
import org.bytedeco.ffmpeg.global.swscale.*

public actual object SWScale : Library {
    public override fun version(): Int = swscale_version()

    override fun configuration(): String = swscale_configuration().string

    override fun license(): String = swscale_license().string

    public actual fun getCoefficients(colorspace: Int): Int {
        return sws_getCoefficients(colorspace).get()
    }

    public actual fun isSupportedInput(pixFmt: AVPixelFormat): Boolean {
        return sws_isSupportedInput(pixFmt.value).checkTrue()
    }

    public actual fun isSupportedOutput(pixFmt: AVPixelFormat): Boolean {
        return sws_isSupportedOutput(pixFmt.value).checkTrue()
    }

    public actual fun isSupportedEndianness(pixFmt: AVPixelFormat): Boolean {
        return sws_isSupportedEndiannessConversion(pixFmt.value).checkTrue()
    }

    public actual fun allocContext(): SwsContext {
        TODO("Not yet implemented")
    }

    public actual fun initContext(
        context: SwsContext,
        srcFilter: SwsFilter,
        dstFilter: SwsFilter
    ) {
    }

    public actual fun freeContext(swsContext: SwsContext) {
    }
}