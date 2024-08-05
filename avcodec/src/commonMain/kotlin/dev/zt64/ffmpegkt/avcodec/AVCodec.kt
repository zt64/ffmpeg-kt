package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import kotlin.jvm.JvmInline

public expect class NativeAVCodec

@JvmInline
public expect value class AVCodec(internal val native: NativeAVCodec) : AutoCloseable {
    public val name: String
    public val longName: String
    public val type: AVMediaType
    public val id: AVCodecID
    public val capabilities: Int
    public val maxLowres: Byte
    public val supportedFrameRates: List<AVRational>
    public val pixFormats: List<AVPixelFormat>
    public val supportedSampleRates: IntArray
    public val sampleFormats: List<AVSampleFormat>
    public val profiles: List<AVProfile>
    public val channelLayouts: List<AVChannelLayout>
    public val wrapperName: String

    public companion object {
        public fun findEncoder(name: String): AVCodec?
        public fun findDecoder(name: String): AVCodec?

        public fun findEncoder(id: AVCodecID): AVCodec?
        public fun findDecoder(id: AVCodecID): AVCodec?
    }
}