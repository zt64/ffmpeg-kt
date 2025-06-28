@file:Suppress("NOTHING_TO_INLINE")

package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.toList
import org.bytedeco.ffmpeg.global.avcodec.*

internal actual typealias NativeAVCodec = org.bytedeco.ffmpeg.avcodec.AVCodec

@JvmInline
public actual value class Codec(public val native: NativeAVCodec) {
    public actual inline val id: CodecID
        get() = CodecID(native.id())

    public actual inline val name: String
        get() = native.name().string

    public actual inline val longName: String
        get() = native.long_name().string

    public actual inline val type: MediaType
        get() = MediaType(native.type())

    public actual inline val capabilities: Int
        get() = native.capabilities()

    public actual inline val maxLowres: Byte
        get() = native.max_lowres()

    public actual inline val supportedFrameRates: List<Rational>
        get() = native.supported_framerates().toList(
            isTerminator = { it.num() == 0 && it.den() == 0 },
            transform = ::Rational
        )

    public actual inline val pixFormats: List<PixelFormat>
        get() = native.pix_fmts().toList(
            terminator = -1,
            transform = ::PixelFormat
        )

    public actual inline val supportedSampleRates: IntArray
        get() = native.supported_samplerates().toList<Int>(terminator = 0).toIntArray()

    public actual inline val sampleFormats: List<SampleFormat>
        get() = native.sample_fmts().toList<SampleFormat>(
            terminator = -1,
            transform = ::SampleFormat
        )

    public actual inline val profiles: List<AVProfile>
        get() = native.profiles().toList(
            isTerminator = { it.profile() == AVProfileType.UNKNOWN },
            transform = { AVProfile(it.profile(), it.name().string) }
        )

    public actual inline val channelLayouts: List<ChannelLayout>
        get() = native.ch_layouts().toList(
            isTerminator = { it.nb_channels() == 0 && it.order() == 0 && it.u_mask() == 0L },
            transform = ::ChannelLayout
        )

    public actual inline val wrapperName: String
        get() = native.wrapper_name().string

    public actual companion object {
        public actual fun findEncoder(name: String): Codec? {
            return avcodec_find_encoder_by_name(name)?.let(::Codec)
        }

        public actual fun findDecoder(name: String): Codec? {
            return avcodec_find_decoder_by_name(name)?.let(::Codec)
        }

        public actual fun findEncoder(id: CodecID): Codec? {
            return avcodec_find_encoder(id.num)?.let(::Codec)
        }

        public actual fun findDecoder(id: CodecID): Codec? {
            return avcodec_find_decoder(id.num)?.let(::Codec)
        }
    }
}