package dev.zt64.ffmpegkt.avutil

import kotlin.jvm.JvmInline

public interface VideoFormat {
    public val name: String
    public val isPlanar: Boolean
    public val isRgb: Boolean
}

public expect fun VideoFormat(name: String): VideoFormat

// TODO: There's probably a better way to do this
@JvmInline
public value class PixelFormat(public val num: Int) {
    public companion object {
        public val NONE: PixelFormat = PixelFormat(-1)
        public val YUV420P: PixelFormat = PixelFormat(0)
        public val YUYV422: PixelFormat = PixelFormat(1)
        public val RGB24: PixelFormat = PixelFormat(2)
        public val BGR24: PixelFormat = PixelFormat(3)
        public val YUV422P: PixelFormat = PixelFormat(4)
        public val YUV444P: PixelFormat = PixelFormat(5)
        public val YUV410P: PixelFormat = PixelFormat(6)
        public val GRAY8: PixelFormat = PixelFormat(8)
        public val MONOWHITE: PixelFormat = PixelFormat(9)
        public val MONOBLACK: PixelFormat = PixelFormat(10)
        public val PAL8: PixelFormat = PixelFormat(11)
        public val YUVJ420P: PixelFormat = PixelFormat(12)
        public val YUVJ422P: PixelFormat = PixelFormat(13)
        public val YUVJ444P: PixelFormat = PixelFormat(14)
        public val UYVY422: PixelFormat = PixelFormat(15)
        public val UYYVYY411: PixelFormat = PixelFormat(16)
        public val BGR8: PixelFormat = PixelFormat(17)
        public val BGR4: PixelFormat = PixelFormat(18)
        public val BGR4_BYTE: PixelFormat = PixelFormat(19)
        public val RGB8: PixelFormat = PixelFormat(20)
        public val RGB4: PixelFormat = PixelFormat(21)
        public val RGB4_BYTE: PixelFormat = PixelFormat(22)
        public val NV12: PixelFormat = PixelFormat(23)
        public val NV21: PixelFormat = PixelFormat(24)
        public val ARGB: PixelFormat = PixelFormat(25)
        public val RGBA: PixelFormat = PixelFormat(26)
        public val ABGR: PixelFormat = PixelFormat(27)
        public val BGRA: PixelFormat = PixelFormat(28)
        public val GRAY16BE: PixelFormat = PixelFormat(29)
        public val GRAY16LE: PixelFormat = PixelFormat(30)
        public val YUV440P: PixelFormat = PixelFormat(31)
        public val YUVJ440P: PixelFormat = PixelFormat(32)
        public val YUVA420P: PixelFormat = PixelFormat(33)
        public val RGB48BE: PixelFormat = PixelFormat(34)
        public val RGB48LE: PixelFormat = PixelFormat(35)
        public val RGB565BE: PixelFormat = PixelFormat(36)
        public val RGB565LE: PixelFormat = PixelFormat(37)
        public val RGB555BE: PixelFormat = PixelFormat(38)
        public val RGB555LE: PixelFormat = PixelFormat(39)
        public val BGR565BE: PixelFormat = PixelFormat(40)
        public val BGR565LE: PixelFormat = PixelFormat(41)
        public val BGR555BE: PixelFormat = PixelFormat(42)
        public val BGR555LE: PixelFormat = PixelFormat(43)
        public val VAAPI: PixelFormat = PixelFormat(44)
        public val YUV420P16LE: PixelFormat = PixelFormat(45)
        public val YUV420P16BE: PixelFormat = PixelFormat(46)
        public val YUV422P16LE: PixelFormat = PixelFormat(47)
        public val YUV422P16BE: PixelFormat = PixelFormat(48)
        public val YUV444P16LE: PixelFormat = PixelFormat(49)
        public val YUV444P16BE: PixelFormat = PixelFormat(50)
        public val DXVA2_VLD: PixelFormat = PixelFormat(51)
        public val RGB444LE: PixelFormat = PixelFormat(52)
        public val RGB444BE: PixelFormat = PixelFormat(53)
        public val BGR444LE: PixelFormat = PixelFormat(54)
        public val BGR444BE: PixelFormat = PixelFormat(55)
        public val YA8: PixelFormat = PixelFormat(56)
        public val Y400A: PixelFormat = PixelFormat(YA8.num)
        public val GRAY8A: PixelFormat = PixelFormat(YA8.num)
        public val BGR48BE: PixelFormat = PixelFormat(YA8.num + 1)
        public val BGR48LE: PixelFormat = PixelFormat(YA8.num + 2)
        public val YUV420P9BE: PixelFormat = PixelFormat(YA8.num + 3)
        public val YUV420P9LE: PixelFormat = PixelFormat(YA8.num + 4)
        public val YUV420P10BE: PixelFormat = PixelFormat(YA8.num + 5)
        public val YUV420P10LE: PixelFormat = PixelFormat(YA8.num + 6)
        public val YUV422P10BE: PixelFormat = PixelFormat(YA8.num + 7)
        public val YUV422P10LE: PixelFormat = PixelFormat(YA8.num + 8)
        public val YUV444P9BE: PixelFormat = PixelFormat(YA8.num + 9)
        public val YUV444P9LE: PixelFormat = PixelFormat(YA8.num + 10)
        public val YUV444P10BE: PixelFormat = PixelFormat(YA8.num + 11)
        public val YUV444P10LE: PixelFormat = PixelFormat(YA8.num + 12)
        public val YUV422P9BE: PixelFormat = PixelFormat(YA8.num + 13)
        public val YUV422P9LE: PixelFormat = PixelFormat(YA8.num + 14)
        public val GBRP: PixelFormat = PixelFormat(YA8.num + 15)
        public val GBR24P: PixelFormat = PixelFormat(GBRP.num)
        public val GBRP9BE: PixelFormat = PixelFormat(GBRP.num + 1)
        public val GBRP9LE: PixelFormat = PixelFormat(GBRP.num + 2)
        public val GBRP10BE: PixelFormat = PixelFormat(GBRP.num + 3)
        public val GBRP10LE: PixelFormat = PixelFormat(GBRP.num + 4)
        public val GBRP16BE: PixelFormat = PixelFormat(GBRP.num + 5)
        public val GBRP16LE: PixelFormat = PixelFormat(GBRP.num + 6)
        public val YUVA422P: PixelFormat = PixelFormat(GBRP.num + 7)
        public val YUVA444P: PixelFormat = PixelFormat(GBRP.num + 8)
        public val YUVA420P9BE: PixelFormat = PixelFormat(GBRP.num + 9)
        public val YUVA420P9LE: PixelFormat = PixelFormat(GBRP.num + 10)
        public val YUVA422P9BE: PixelFormat = PixelFormat(GBRP.num + 11)
        public val YUVA422P9LE: PixelFormat = PixelFormat(GBRP.num + 12)
        public val YUVA444P9BE: PixelFormat = PixelFormat(GBRP.num + 13)
        public val YUVA444P9LE: PixelFormat = PixelFormat(GBRP.num + 14)
        public val YUVA420P10BE: PixelFormat = PixelFormat(GBRP.num + 15)
        public val YUVA420P10LE: PixelFormat = PixelFormat(GBRP.num + 16)
        public val YUVA422P10BE: PixelFormat = PixelFormat(GBRP.num + 17)
        public val YUVA422P10LE: PixelFormat = PixelFormat(GBRP.num + 18)
        public val YUVA444P10BE: PixelFormat = PixelFormat(GBRP.num + 19)
        public val YUVA444P10LE: PixelFormat = PixelFormat(GBRP.num + 20)
        public val YUVA420P16BE: PixelFormat = PixelFormat(GBRP.num + 21)
        public val YUVA420P16LE: PixelFormat = PixelFormat(GBRP.num + 22)
        public val YUVA422P16BE: PixelFormat = PixelFormat(GBRP.num + 23)
        public val YUVA422P16LE: PixelFormat = PixelFormat(GBRP.num + 24)
        public val YUVA444P16BE: PixelFormat = PixelFormat(GBRP.num + 25)
        public val YUVA444P16LE: PixelFormat = PixelFormat(GBRP.num + 26)
        public val VDPAU: PixelFormat = PixelFormat(GBRP.num + 27)
        public val XYZ12LE: PixelFormat = PixelFormat(GBRP.num + 28)
        public val XYZ12BE: PixelFormat = PixelFormat(GBRP.num + 29)
        public val NV16: PixelFormat = PixelFormat(GBRP.num + 30)
        public val NV20LE: PixelFormat = PixelFormat(GBRP.num + 31)
        public val NV20BE: PixelFormat = PixelFormat(GBRP.num + 32)
        public val RGBA64BE: PixelFormat = PixelFormat(GBRP.num + 33)
        public val RGBA64LE: PixelFormat = PixelFormat(GBRP.num + 34)
        public val BGRA64BE: PixelFormat = PixelFormat(GBRP.num + 35)
        public val BGRA64LE: PixelFormat = PixelFormat(GBRP.num + 36)
        public val YVYU422: PixelFormat = PixelFormat(GBRP.num + 37)
        public val YA16BE: PixelFormat = PixelFormat(GBRP.num + 38)
        public val YA16LE: PixelFormat = PixelFormat(GBRP.num + 39)
        public val GBRAP: PixelFormat = PixelFormat(GBRP.num + 40)
        public val GBRAP16BE: PixelFormat = PixelFormat(GBRP.num + 41)
        public val GBRAP16LE: PixelFormat = PixelFormat(GBRP.num + 42)
        public val QSV: PixelFormat = PixelFormat(GBRP.num + 43)
        public val MMAL: PixelFormat = PixelFormat(GBRP.num + 44)
        public val D3D11VA_VLD: PixelFormat = PixelFormat(GBRP.num + 45)
        public val CUDA: PixelFormat = PixelFormat(GBRP.num + 46)
        public val PIX_FMT_0RGB: PixelFormat = PixelFormat(GBRP.num + 47)
        public val RGB0: PixelFormat = PixelFormat(GBRP.num + 48)
        public val PIX_FMT_0BGR: PixelFormat = PixelFormat(GBRP.num + 49)
        public val BGR0: PixelFormat = PixelFormat(GBRP.num + 50)
        public val YUV420P12BE: PixelFormat = PixelFormat(GBRP.num + 51)
        public val YUV420P12LE: PixelFormat = PixelFormat(GBRP.num + 52)
        public val YUV420P14BE: PixelFormat = PixelFormat(GBRP.num + 53)
        public val YUV420P14LE: PixelFormat = PixelFormat(GBRP.num + 54)
        public val YUV422P12BE: PixelFormat = PixelFormat(GBRP.num + 55)
        public val YUV422P12LE: PixelFormat = PixelFormat(GBRP.num + 56)
        public val YUV422P14BE: PixelFormat = PixelFormat(GBRP.num + 57)
        public val YUV422P14LE: PixelFormat = PixelFormat(GBRP.num + 58)
        public val YUV444P12BE: PixelFormat = PixelFormat(GBRP.num + 59)
        public val YUV444P12LE: PixelFormat = PixelFormat(GBRP.num + 60)
        public val YUV444P14BE: PixelFormat = PixelFormat(GBRP.num + 61)
        public val YUV444P14LE: PixelFormat = PixelFormat(GBRP.num + 62)
        public val GBRP12BE: PixelFormat = PixelFormat(GBRP.num + 63)
        public val GBRP12LE: PixelFormat = PixelFormat(GBRP.num + 64)
        public val GBRP14BE: PixelFormat = PixelFormat(GBRP.num + 65)
        public val GBRP14LE: PixelFormat = PixelFormat(GBRP.num + 66)
        public val YUVJ411P: PixelFormat = PixelFormat(GBRP.num + 67)
        public val BAYER_BGGR8: PixelFormat = PixelFormat(GBRP.num + 68)
        public val BAYER_RGGB8: PixelFormat = PixelFormat(GBRP.num + 69)
        public val BAYER_GBRG8: PixelFormat = PixelFormat(GBRP.num + 70)
        public val BAYER_GRBG8: PixelFormat = PixelFormat(GBRP.num + 71)
        public val BAYER_BGGR16LE: PixelFormat = PixelFormat(GBRP.num + 72)
        public val BAYER_BGGR16BE: PixelFormat = PixelFormat(GBRP.num + 73)
        public val BAYER_RGGB16LE: PixelFormat = PixelFormat(GBRP.num + 74)
        public val BAYER_RGGB16BE: PixelFormat = PixelFormat(GBRP.num + 75)
        public val BAYER_GBRG16LE: PixelFormat = PixelFormat(GBRP.num + 76)
        public val BAYER_GBRG16BE: PixelFormat = PixelFormat(GBRP.num + 77)
        public val BAYER_GRBG16LE: PixelFormat = PixelFormat(GBRP.num + 78)
        public val BAYER_GRBG16BE: PixelFormat = PixelFormat(GBRP.num + 79)
        public val XVMC: PixelFormat = PixelFormat(GBRP.num + 80)
        public val YUV440P10LE: PixelFormat = PixelFormat(GBRP.num + 81)
        public val YUV440P10BE: PixelFormat = PixelFormat(GBRP.num + 82)
        public val YUV440P12LE: PixelFormat = PixelFormat(GBRP.num + 83)
        public val YUV440P12BE: PixelFormat = PixelFormat(GBRP.num + 84)
        public val AYUV64LE: PixelFormat = PixelFormat(GBRP.num + 85)
        public val AYUV64BE: PixelFormat = PixelFormat(GBRP.num + 86)
        public val VIDEOTOOLBOX: PixelFormat = PixelFormat(GBRP.num + 87)
        public val P010LE: PixelFormat = PixelFormat(GBRP.num + 88)
        public val P010BE: PixelFormat = PixelFormat(GBRP.num + 89)
        public val GBRAP12BE: PixelFormat = PixelFormat(GBRP.num + 90)
        public val GBRAP12LE: PixelFormat = PixelFormat(GBRP.num + 91)
        public val GBRAP10BE: PixelFormat = PixelFormat(GBRP.num + 92)
        public val GBRAP10LE: PixelFormat = PixelFormat(GBRP.num + 93)
        public val MEDIACODEC: PixelFormat = PixelFormat(GBRP.num + 94)
        public val GRAY12BE: PixelFormat = PixelFormat(GBRP.num + 95)
        public val GRAY12LE: PixelFormat = PixelFormat(GBRP.num + 96)
        public val GRAY10BE: PixelFormat = PixelFormat(GBRP.num + 97)
        public val GRAY10LE: PixelFormat = PixelFormat(GBRP.num + 98)
        public val P016LE: PixelFormat = PixelFormat(GBRP.num + 99)
        public val P016BE: PixelFormat = PixelFormat(GBRP.num + 100)
        public val D3D11: PixelFormat = PixelFormat(GBRP.num + 101)
        public val GRAY9BE: PixelFormat = PixelFormat(GBRP.num + 102)
        public val GRAY9LE: PixelFormat = PixelFormat(GBRP.num + 103)
        public val GBRPF32BE: PixelFormat = PixelFormat(GBRP.num + 104)
        public val GBRPF32LE: PixelFormat = PixelFormat(GBRP.num + 105)
        public val GBRAPF32BE: PixelFormat = PixelFormat(GBRP.num + 106)
        public val GBRAPF32LE: PixelFormat = PixelFormat(GBRP.num + 107)
        public val DRM_PRIME: PixelFormat = PixelFormat(GBRP.num + 108)
        public val OPENCL: PixelFormat = PixelFormat(GBRP.num + 109)
        public val GRAY14BE: PixelFormat = PixelFormat(GBRP.num + 110)
        public val GRAY14LE: PixelFormat = PixelFormat(GBRP.num + 111)
        public val GRAYF32BE: PixelFormat = PixelFormat(GBRP.num + 112)
        public val GRAYF32LE: PixelFormat = PixelFormat(GBRP.num + 113)
        public val YUVA422P12BE: PixelFormat = PixelFormat(GBRP.num + 114)
        public val YUVA422P12LE: PixelFormat = PixelFormat(GBRP.num + 115)
        public val YUVA444P12BE: PixelFormat = PixelFormat(GBRP.num + 116)
        public val YUVA444P12LE: PixelFormat = PixelFormat(GBRP.num + 117)
        public val NV24: PixelFormat = PixelFormat(GBRP.num + 118)
        public val NV42: PixelFormat = PixelFormat(GBRP.num + 119)
        public val VULKAN: PixelFormat = PixelFormat(GBRP.num + 120)
        public val Y210BE: PixelFormat = PixelFormat(GBRP.num + 121)
        public val Y210LE: PixelFormat = PixelFormat(GBRP.num + 122)
        public val X2RGB10LE: PixelFormat = PixelFormat(GBRP.num + 123)
        public val X2RGB10BE: PixelFormat = PixelFormat(GBRP.num + 124)
        public val X2BGR10LE: PixelFormat = PixelFormat(GBRP.num + 125)
        public val X2BGR10BE: PixelFormat = PixelFormat(GBRP.num + 126)
        public val P210BE: PixelFormat = PixelFormat(GBRP.num + 127)
        public val P210LE: PixelFormat = PixelFormat(GBRP.num + 128)
        public val P410BE: PixelFormat = PixelFormat(GBRP.num + 129)
        public val P410LE: PixelFormat = PixelFormat(GBRP.num + 130)
        public val P216BE: PixelFormat = PixelFormat(GBRP.num + 131)
        public val P216LE: PixelFormat = PixelFormat(GBRP.num + 132)
        public val P416BE: PixelFormat = PixelFormat(GBRP.num + 133)
        public val P416LE: PixelFormat = PixelFormat(GBRP.num + 134)
        public val VUYA: PixelFormat = PixelFormat(GBRP.num + 135)
        public val RGBAF16BE: PixelFormat = PixelFormat(GBRP.num + 136)
        public val RGBAF16LE: PixelFormat = PixelFormat(GBRP.num + 137)
        public val VUYX: PixelFormat = PixelFormat(GBRP.num + 138)
        public val P012LE: PixelFormat = PixelFormat(GBRP.num + 139)
        public val P012BE: PixelFormat = PixelFormat(GBRP.num + 140)
        public val Y212BE: PixelFormat = PixelFormat(GBRP.num + 141)
        public val Y212LE: PixelFormat = PixelFormat(GBRP.num + 142)
        public val XV30BE: PixelFormat = PixelFormat(GBRP.num + 143)
        public val XV30LE: PixelFormat = PixelFormat(GBRP.num + 144)
        public val XV36BE: PixelFormat = PixelFormat(GBRP.num + 145)
        public val XV36LE: PixelFormat = PixelFormat(GBRP.num + 146)
        public val RGBF32BE: PixelFormat = PixelFormat(GBRP.num + 147)
        public val RGBF32LE: PixelFormat = PixelFormat(GBRP.num + 148)
        public val RGBAF32BE: PixelFormat = PixelFormat(GBRP.num + 149)
        public val RGBAF32LE: PixelFormat = PixelFormat(GBRP.num + 150)
        public val P212BE: PixelFormat = PixelFormat(GBRP.num + 151)
        public val P212LE: PixelFormat = PixelFormat(GBRP.num + 152)
        public val P412BE: PixelFormat = PixelFormat(GBRP.num + 153)
        public val P412LE: PixelFormat = PixelFormat(GBRP.num + 154)
        public val GBRAP14BE: PixelFormat = PixelFormat(GBRP.num + 155)
        public val GBRAP14LE: PixelFormat = PixelFormat(GBRP.num + 156)
        public val NB: PixelFormat = PixelFormat(GBRP.num + 157)
    }

    override fun toString(): String = commonToString() ?: "Unknown"
}

internal expect fun PixelFormat(name: String): PixelFormat
internal expect fun PixelFormat.commonToString(): String?