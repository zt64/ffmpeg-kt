package dev.zt64.ffmpegkt.codec

public enum class AVDiscard(internal val num: Int) {
    NONE(-16),
    DEFAULT(0),
    NONREF(8),
    BIDIR(16),
    NONINTRA(24),
    NONKEY(32),
    ALL(48)
}