package dev.zt64.ffmpegkt.avutil

import kotlin.jvm.JvmInline

@JvmInline
public value class AVMediaType(public val num: Int) {
    public companion object {
        public val UNKNOWN: AVMediaType = AVMediaType(-1)
        public val VIDEO: AVMediaType = AVMediaType(0)
        public val AUDIO: AVMediaType = AVMediaType(1)
        public val DATA: AVMediaType = AVMediaType(2)
        public val SUBTITLE: AVMediaType = AVMediaType(3)
        public val ATTACHMENT: AVMediaType = AVMediaType(4)
    }

    override fun toString(): String = getString() ?: "Unknown"
}

internal expect fun AVMediaType.getString(): String?