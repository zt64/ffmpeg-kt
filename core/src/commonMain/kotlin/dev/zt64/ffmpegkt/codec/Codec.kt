package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.audio.ChannelLayout
import dev.zt64.ffmpegkt.avutil.audio.SampleFormat
import dev.zt64.ffmpegkt.avutil.hw.HWConfig
import dev.zt64.ffmpegkt.avutil.video.PixelFormat
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
    /**
     * The unique name of the codec.
     */
    public val name: String

    /**
     * The descriptive name of the codec.
     */
    public val longName: String

    /**
     * The type of media this codec handles (e.g., audio, video).
     */
    public val type: MediaType

    /**
     * The unique identifier for this codec.
     */
    public val id: CodecID

    /**
     * The capabilities of the codec, represented as a bitmask.
     */
    public val capabilities: Int

    /**
     * The maximum low-resolution decoding factor.
     */
    public val maxLowres: Byte

    /**
     * A list of supported frame rates.
     */
    public val supportedFrameRates: List<Rational>

    /**
     * A list of supported pixel formats.
     */
    public val pixFormats: List<PixelFormat>

    /**
     * An array of supported sample rates.
     */
    public val supportedSampleRates: IntArray

    /**
     * A list of supported sample formats.
     */
    public val sampleFormats: List<SampleFormat>

    /**
     * A list of supported profiles.
     */
    public val profiles: List<Profile>

    /**
     * A list of supported channel layouts.
     */
    public val channelLayouts: List<ChannelLayout>

    public val hardwareConfigs: List<HWConfig>

    /**
     * The name of the wrapper for this codec.
     */
    public val wrapperName: String

    public companion object {
        /**
         * Find an encoder by its name.
         * @param name The name of the encoder.
         * @return The [Codec] if found, otherwise null.
         */
        public fun findEncoder(name: String): Codec?

        /**
         * Find a decoder by its name.
         * @param name The name of the decoder.
         * @return The [Codec] if found, otherwise null.
         */
        public fun findDecoder(name: String): Codec?

        /**
         * Find an encoder by its [CodecID].
         * @param id The ID of the encoder.
         * @return The [Codec] if found, otherwise null.
         */
        public fun findEncoder(id: CodecID): Codec?

        /**
         * Find a decoder by its [CodecID].
         * @param id The ID of the decoder.
         * @return The [Codec] if found, otherwise null.
         */
        public fun findDecoder(id: CodecID): Codec?

        public fun getCodecs(): List<Codec>
    }
}