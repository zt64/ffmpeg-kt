package dev.zt64.ffmpegkt.avutil

import kotlin.jvm.JvmInline

@JvmInline
public value class AVSampleFormat(public val num: Int) {
    public companion object {
        public val NONE: AVSampleFormat = AVSampleFormat(-1)
        public val U8: AVSampleFormat = AVSampleFormat(0)
        public val S16: AVSampleFormat = AVSampleFormat(1)
        public val S32: AVSampleFormat = AVSampleFormat(2)
        public val FLT: AVSampleFormat = AVSampleFormat(3)
        public val DBL: AVSampleFormat = AVSampleFormat(4)
        public val U8P: AVSampleFormat = AVSampleFormat(5)
        public val S16P: AVSampleFormat = AVSampleFormat(6)
        public val S32P: AVSampleFormat = AVSampleFormat(7)
        public val FLTP: AVSampleFormat = AVSampleFormat(8)
        public val DBLP: AVSampleFormat = AVSampleFormat(9)
        public val S64: AVSampleFormat = AVSampleFormat(10)
        public val S64P: AVSampleFormat = AVSampleFormat(11)
        public val NB: AVSampleFormat = AVSampleFormat(12)
    }
}

public expect fun AVSampleFormat(name: String): AVSampleFormat

public expect val AVSampleFormat.name: String?
public expect val AVSampleFormat.packed: AVSampleFormat
public expect val AVSampleFormat.planar: AVSampleFormat
public expect val AVSampleFormat.bytesPerSample: Int
public expect val AVSampleFormat.isPlanar: Boolean

public expect fun AVSampleFormat.altSampleFmt(planar: Int): AVSampleFormat