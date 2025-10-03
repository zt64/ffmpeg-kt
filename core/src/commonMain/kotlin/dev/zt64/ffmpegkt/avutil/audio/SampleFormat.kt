package dev.zt64.ffmpegkt.avutil.audio

import kotlin.jvm.JvmInline

@JvmInline
public value class SampleFormat(public val num: Int) {
    public companion object {
        public val NONE: SampleFormat = SampleFormat(-1)
        public val U8: SampleFormat = SampleFormat(0)
        public val S16: SampleFormat = SampleFormat(1)
        public val S32: SampleFormat = SampleFormat(2)
        public val FLT: SampleFormat = SampleFormat(3)
        public val DBL: SampleFormat = SampleFormat(4)
        public val U8P: SampleFormat = SampleFormat(5)
        public val S16P: SampleFormat = SampleFormat(6)
        public val S32P: SampleFormat = SampleFormat(7)
        public val FLTP: SampleFormat = SampleFormat(8)
        public val DBLP: SampleFormat = SampleFormat(9)
        public val S64: SampleFormat = SampleFormat(10)
        public val S64P: SampleFormat = SampleFormat(11)
        public val NB: SampleFormat = SampleFormat(12)
    }

    override fun toString(): String {
        return "SampleFormat(name=$name, bytesPerSample=$bytesPerSample, isPlanar=$isPlanar)"
    }
}

public expect fun SampleFormat(name: String): SampleFormat

public expect val SampleFormat.name: String?
public expect val SampleFormat.packed: SampleFormat
public expect val SampleFormat.planar: SampleFormat
public expect val SampleFormat.bytesPerSample: Int
public expect val SampleFormat.isPlanar: Boolean

public expect fun SampleFormat.altSampleFmt(planar: Int): SampleFormat