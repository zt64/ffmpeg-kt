package dev.zt64.ffmpegkt.avutil

public enum class AVMediaType(internal val value: Int) {
    UNKNOWN(-1),
    VIDEO(0),
    AUDIO(1),
    DATA(2),
    SUBTITLE(3),
    ATTACHMENT(4)
}