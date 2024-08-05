package dev.zt64.ffmpegkt.avutil

public enum class AVMediaType(public val num: Int) {
    UNKNOWN(-1),
    VIDEO(0),
    AUDIO(1),
    DATA(2),
    SUBTITLE(3),
    ATTACHMENT(4);

    public companion object {
        public operator fun invoke(value: Int): AVMediaType {
            return entries.firstOrNull { it.num == value } ?: UNKNOWN
        }
    }
}