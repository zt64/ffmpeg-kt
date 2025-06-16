package dev.zt64.ffmpegkt.avutil

import kotlin.jvm.JvmInline

@JvmInline
public value class MediaType(public val num: Int) {
    public companion object {
        public val UNKNOWN: MediaType = MediaType(-1)
        public val VIDEO: MediaType = MediaType(0)
        public val AUDIO: MediaType = MediaType(1)
        public val DATA: MediaType = MediaType(2)
        public val SUBTITLE: MediaType = MediaType(3)
        public val ATTACHMENT: MediaType = MediaType(4)
    }

    override fun toString(): String = getString() ?: "Unknown"
}

internal expect fun MediaType.getString(): String?