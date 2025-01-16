package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.ChannelLayout
import dev.zt64.ffmpegkt.avutil.MediaType
import ffmpeg.*
import kotlinx.cinterop.get
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString

internal actual typealias NativeAVCodec = ffmpeg.AVCodec

public actual value class AVCodec(public val native: NativeAVCodec) : AutoCloseable {
    public actual inline val name: String
        get() = native.name?.toKString().orEmpty()
    public actual inline val longName: String
        get() = native.long_name?.toKString().orEmpty()
    public actual inline val type: MediaType
        get() = MediaType(native.type)
    public actual inline val id: AVCodecID
        get() = AVCodecID(native.id.toInt())
    public actual inline val capabilities: Int
        get() = native.capabilities
    public actual inline val maxLowres: Byte
        get() = native.max_lowres.toByte()
    public actual inline val supportedFrameRates: List<Rational>
        get() = emptyList()
    public actual inline val pixFormats: List<PixelFormat>
        get() = emptyList()
    public actual inline val supportedSampleRates: IntArray
        get() = native.supported_samplerates?.let {
            buildList {
                var i = 0
                while (true) {
                    val rate = native.supported_samplerates?.get(i).takeUnless {
                        it == 0
                    } ?: break
                    add(rate)
                    i++
                }
            }
        }.orEmpty().toIntArray()
    public actual inline val sampleFormats: List<SampleFormat>
        get() = emptyList()
    public actual inline val profiles: List<AVProfile>
        get() = emptyList()
    public actual inline val channelLayouts: List<ChannelLayout>
        get() = native.ch_layouts!!.let { layouts ->
            buildList {
                var i = 0
                while (true) {
                    val layout = layouts[i].takeIf {
                        it.nb_channels != 0 && it.order.value != 0u
                    }?.let(::ChannelLayout) ?: break
                    add(layout)
                    i++
                }
            }
        }
    public actual inline val wrapperName: String
        get() = native.wrapper_name?.toKString().orEmpty()

    public actual companion object {
        public actual fun findEncoder(name: String): AVCodec? {
            return avcodec_find_encoder_by_name(name)?.let { AVCodec(it.pointed) }
        }

        public actual fun findDecoder(name: String): AVCodec? {
            return avcodec_find_decoder_by_name(name)?.let { AVCodec(it.pointed) }
        }

        public actual fun findEncoder(id: AVCodecID): AVCodec? {
            return avcodec_find_encoder(id.num.toUInt())?.let { AVCodec(it.pointed) }
        }

        public actual fun findDecoder(id: AVCodecID): AVCodec? {
            return avcodec_find_decoder(id.num.toUInt())?.let { AVCodec(it.pointed) }
        }
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}