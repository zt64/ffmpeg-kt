package dev.zt64.ffmpegkt.avutil.hash

public typealias AVCRC = Int

/**
 * A utility object for calculating Cycle Redundancy Checks (CRC).
 *
 * This object provides functions for initializing CRC tables and calculating CRC values for data buffers,
 * wrapping the underlying FFmpeg `libavutil/crc.h` functionality.
 */
public expect object CRC {
    /**
     * Get a pre-calculated CRC table.
     *
     * @param id The ID of the CRC table to retrieve.
     * @return The requested CRC table as a list of integers.
     */
    public fun table(id: AVCRCId): List<AVCRC>

    /**
     * Initialize a CRC table.
     *
     * @param ctx The CRC table to initialize.
     * @param le If true, the CRC is calculated in little-endian format.
     * @param bits The number of bits in the CRC (e.g., 8, 16, 24, 32).
     * @param poly The polynomial for the CRC calculation.
     * @param ctxSize The size of the context/table.
     */
    public fun init(
        ctx: List<AVCRC>,
        le: Boolean,
        bits: Int,
        poly: Int,
        ctxSize: Int
    )

    /**
     * Calculate the CRC of a data buffer.
     *
     * @param ctx The CRC table to use for the calculation.
     * @param crc The initial CRC value, typically from a previous calculation on a preceding data block.
     * @param buf The buffer of data whose CRC is to be calculated.
     * @return The updated CRC value after processing the buffer.
     */
    public fun calculate(
        ctx: List<AVCRC>,
        crc: AVCRC,
        buf: ByteArray
    ): AVCRC
}