package dev.zt64.ffmpegkt.avutil.hash

import org.bytedeco.ffmpeg.global.avutil.*

// public actual object CRC {
//     public actual fun table(id: AVCRCId): List<AVCRC> {
//         return av_crc_get_table(id.ordinal).asBuffer().array().toList()
//     }
//
//     public actual fun init(
//         ctx: List<AVCRC>,
//         le: Int,
//         bits: Int,
//         poly: Int,
//         ctxSize: Int
//     ) {
//         av_crc_init(
//             ctx.toIntArray(),
//             le,
//             bits,
//             poly,
//             ctxSize
//         ).checkError()
//     }
//
//     public actual fun calculate(
//         ctx: List<AVCRC>,
//         crc: AVCRC,
//         buf: ByteArray
//     ): AVCRC = av_crc(ctx.toIntArray(), crc, buf, buf.size.toLong())
// }