package dev.zt64.ffmpegkt.avutil

public enum class AVPictureType(internal val value: Int) {
    /** Undefined */
    NONE(0),

    /** Intra */
    I(1),

    /** Predicted */
    P(2),

    /** Bi-dir predicted */
    B(3),

    /** S(GMC)-VOP MPEG4 */
    S(4),

    /** Switching Intra */
    SI(5),

    /** Switching Predicted */
    SP(6),

    /** BI type */
    BI(7)
}

public expect fun AVPictureType.getPictureTypeChar(): Char