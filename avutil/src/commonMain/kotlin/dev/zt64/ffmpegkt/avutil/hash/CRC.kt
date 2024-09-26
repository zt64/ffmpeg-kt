package dev.zt64.ffmpegkt.avutil.hash

public typealias AVCRC = Int

// /**
//  * Cycle Redundancy Check
//  */
// public expect object CRC {
//     /**
//      * Get a CRC table.
//      *
//      * @param id CRC table ID
//      * @return CRC table
//      */
//     public fun table(id: AVCRCId): List<AVCRC>
//
//     /**
//      * Initialize a CRC table.
//      *
//      * @param le
//      * @param bits
//      * @param poly
//      * @param ctxSize
//      */
//     public fun init(
//         ctx: List<AVCRC>,
//         le: Int,
//         bits: Int,
//         poly: Int,
//         ctxSize: Int
//     )
//
//     /**
//      * Calculate the CRC of a block.
//      *
//      * @param crc CRC of previous blocks
//      * @param buf Buffer whose CRC to calculate
//      * @param len Length of the buffer
//      * @return CRC updated with the buffer
//      */
//     public fun calculate(
//         ctx: List<AVCRC>,
//         crc: AVCRC,
//         buf: ByteArray
//     ): AVCRC
// }