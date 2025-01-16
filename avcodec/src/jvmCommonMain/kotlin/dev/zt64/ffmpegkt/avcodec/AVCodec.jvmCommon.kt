package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import org.bytedeco.ffmpeg.global.avcodec.*

internal actual typealias NativeAVCodec = org.bytedeco.ffmpeg.avcodec.AVCodec

@JvmInline
public actual value class AVCodec(public val native: NativeAVCodec) : AutoCloseable {
    public actual inline val id: AVCodecID
        get() = AVCodecID(native.id())

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
        get() = native.supported_framerates()?.run {
            buildList {
                var i = 0
                while (true) {
                    val rate = position(i.toLong()).takeUnless {
                        it.num() == 0 && it.den() == 0
                    } ?: break
                    add(Rational(rate))
                    i++
                }
            }
        }.orEmpty()

    public actual inline val pixFormats: List<PixelFormat>
        get() = native.pix_fmts()?.run {
            buildList {
                var i = 0
                while (true) {
                    val format = position(i.toLong()).get().takeUnless { it == -1 } ?: break
                    add(PixelFormat(format))
                    i++
                }
            }
        }.orEmpty()

    public actual inline val supportedSampleRates: IntArray
        get() = native.supported_samplerates()?.run {
            buildList {
                var i = 0
                while (true) {
                    val rate = position(i.toLong()).get().takeUnless { it == 0 } ?: break
                    add(rate)
                    i++
                }
            }
        }.orEmpty().toIntArray()

    public actual inline val sampleFormats: List<SampleFormat>
        get() = native.sample_fmts()?.run {
            buildList {
                var i = 0
                while (true) {
                    val format = position(i.toLong()).get().takeUnless { it == -1 } ?: break
                    add(SampleFormat(format))
                    i++
                }
            }
        }.orEmpty()

    public actual inline val profiles: List<AVProfile>
        get() = native.profiles()?.run {
            buildList {
                var i = 0
                while (true) {
                    val profile = position(i.toLong()).takeUnless {
                        it.profile() == AVProfileType.UNKNOWN
                    } ?: break
                    add(AVProfile(profile.profile(), profile.name().string))
                    i++
                }
            }
        }.orEmpty()
    public actual inline val channelLayouts: List<ChannelLayout>
        get() = native.ch_layouts().run {
            buildList {
                var i = 0
                while (true) {
                    val layout = getPointer(i.toLong()).takeUnless {
                        it.nb_channels() == 0 && it.order() == 0 && it.u_mask() == 0L
                    } ?: break
                    add(ChannelLayout(layout))
                    i++
                }
            }
        }

    public actual inline val wrapperName: String
        get() = native.wrapper_name().string

    public override fun close() {
        native.close()
    }

    public actual companion object {
        public actual fun findEncoder(name: String): AVCodec? {
            return avcodec_find_encoder_by_name(name)?.let(::AVCodec)
        }

        public actual fun findDecoder(name: String): AVCodec? {
            return avcodec_find_decoder_by_name(name)?.let(::AVCodec)
        }

        public actual fun findEncoder(id: AVCodecID): AVCodec? {
            return avcodec_find_encoder(id.num)?.let(::AVCodec)
        }

        public actual fun findDecoder(id: AVCodecID): AVCodec? {
            return avcodec_find_decoder(id.num)?.let(::AVCodec)
        }
    }
}