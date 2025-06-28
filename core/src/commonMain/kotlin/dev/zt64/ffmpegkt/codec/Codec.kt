package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.codec.Codec.Companion.findDecoder
import dev.zt64.ffmpegkt.codec.Codec.Companion.findEncoder

internal expect class NativeAVCodec

// TODO: Split into dedicated AudioCodec and VideoCodec classes to avoid confusion and misuse

/**
 * A codec used for encoding or decoding audio or video.
 *
 * Get a codec by calling [findEncoder] or [findDecoder] with the name of the codec.
 *
 * Or get a codec by calling [findEncoder] or [findDecoder] with the [CodecID] of the codec.
 */
public expect value class Codec internal constructor(internal val native: NativeAVCodec) {
    public val name: String
    public val longName: String
    public val type: MediaType
    public val id: CodecID
    public val capabilities: Int
    public val maxLowres: Byte
    public val supportedFrameRates: List<Rational>
    public val pixFormats: List<PixelFormat>
    public val supportedSampleRates: IntArray
    public val sampleFormats: List<SampleFormat>
    public val profiles: List<AVProfile>
    public val channelLayouts: List<ChannelLayout>
    public val wrapperName: String

    public companion object {
        public fun findEncoder(name: String): Codec?
        public fun findDecoder(name: String): Codec?

        public fun findEncoder(id: CodecID): Codec?
        public fun findDecoder(id: CodecID): Codec?
    }
}