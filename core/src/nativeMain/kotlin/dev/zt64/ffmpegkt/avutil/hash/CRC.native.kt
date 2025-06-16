package dev.zt64.ffmpegkt.avutil.hash

import dev.zt64.ffmpegkt.avutil.util.checkError
import ffmpeg.av_crc
import ffmpeg.av_crc_init
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toCValues

public actual object CRC {
    public actual fun table(id: AVCRCId): List<AVCRC> {
        ffmpeg.av_crc_get_table(id.ordinal.toUInt())!!.pointed
        TODO()
    }

    public actual fun init(
        ctx: List<AVCRC>,
        le: Int,
        bits: Int,
        poly: Int,
        ctxSize: Int
    ) {
        av_crc_init(
            cValuesOf(*ctx.toIntArray().toUIntArray()),
            le,
            bits,
            poly.toUInt(),
            ctxSize
        ).checkError()
    }

    public actual fun calculate(
        ctx: List<AVCRC>,
        crc: AVCRC,
        buf: ByteArray
    ): AVCRC {
        return av_crc(
            cValuesOf(*ctx.toIntArray().toUIntArray()),
            crc.toUInt(),
            buf.toUByteArray().toCValues(),
            buf.size.toULong()
        ).toInt()
    }
}