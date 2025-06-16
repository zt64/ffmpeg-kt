package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.RawRepresentable

public enum class ColorPrimaries(override val rawValue: Int) : RawRepresentable<Int> {
    RESERVED0(0),
    BT709(1),
    UNSPECIFIED(2),
    RESERVED(3),
    BT470M(4),
    BT470BG(5),
    SMPTE170M(6),
    SMPTE240M(7),
    FILM(8),
    BT2020(9),
    SMPTE428(10),
    SMPTEST428_1(10),
    SMPTE431(11),
    SMPTE432(12),
    EBU3213(22),
    JEDEC_P22(22),
    NB(-1)
}