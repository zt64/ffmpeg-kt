package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avcodec.AVCodec.Companion.findDecoder
import dev.zt64.ffmpegkt.avcodec.AVCodec.Companion.findEncoder
import dev.zt64.ffmpegkt.avutil.*

public expect class NativeAVCodec

// TODO: Split into dedicated AudioCodec and VideoCodec classes to avoid confusion and misuse
/**
 * A codec used for encoding or decoding audio or video.
 *
 * Get a codec by calling [findEncoder] or [findDecoder] with the name of the codec.
 *
 * Or get a codec by calling [findEncoder] or [findDecoder] with the [AVCodecID] of the codec.
 *
 * @property native
 */
public expect value class AVCodec(internal val native: NativeAVCodec) : AutoCloseable {
    public val name: String
    public val longName: String
    public val type: AVMediaType
    public val id: AVCodecID
    public val capabilities: Int
    public val maxLowres: Byte
    public val supportedFrameRates: List<Rational>
    public val pixFormats: List<PixelFormat>
    public val supportedSampleRates: IntArray
    public val sampleFormats: List<SampleFormat>
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