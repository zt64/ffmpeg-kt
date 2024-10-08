package dev.zt64.ffmpegkt.avutil

import kotlin.jvm.JvmInline

@JvmInline
public value class AVPixelFormat(public inline val num: Int) {
    public companion object {
        public val NONE: AVPixelFormat = AVPixelFormat(-1)
        public val YUV420P: AVPixelFormat = AVPixelFormat(0)
        public val YUYV422: AVPixelFormat = AVPixelFormat(1)
        public val RGB24: AVPixelFormat = AVPixelFormat(2)
        public val BGR24: AVPixelFormat = AVPixelFormat(3)
        public val YUV422P: AVPixelFormat = AVPixelFormat(4)
        public val YUV444P: AVPixelFormat = AVPixelFormat(5)
        public val YUV410P: AVPixelFormat = AVPixelFormat(6)
        public val GRAY8: AVPixelFormat = AVPixelFormat(8)
        public val MONOWHITE: AVPixelFormat = AVPixelFormat(9)
        public val MONOBLACK: AVPixelFormat = AVPixelFormat(10)
        public val PAL8: AVPixelFormat = AVPixelFormat(11)
        public val YUVJ420P: AVPixelFormat = AVPixelFormat(12)
        public val YUVJ422P: AVPixelFormat = AVPixelFormat(13)
        public val YUVJ444P: AVPixelFormat = AVPixelFormat(14)
        public val UYVY422: AVPixelFormat = AVPixelFormat(15)
        public val UYYVYY411: AVPixelFormat = AVPixelFormat(16)
        public val BGR8: AVPixelFormat = AVPixelFormat(17)
        public val BGR4: AVPixelFormat = AVPixelFormat(18)
        public val BGR4_BYTE: AVPixelFormat = AVPixelFormat(19)
        public val RGB8: AVPixelFormat = AVPixelFormat(20)
        public val RGB4: AVPixelFormat = AVPixelFormat(21)
        public val RGB4_BYTE: AVPixelFormat = AVPixelFormat(22)
        public val NV12: AVPixelFormat = AVPixelFormat(23)
        public val NV21: AVPixelFormat = AVPixelFormat(24)
        public val ARGB: AVPixelFormat = AVPixelFormat(25)
        public val RGBA: AVPixelFormat = AVPixelFormat(26)
        public val ABGR: AVPixelFormat = AVPixelFormat(27)
        public val BGRA: AVPixelFormat = AVPixelFormat(28)
        public val GRAY16BE: AVPixelFormat = AVPixelFormat(29)
        public val GRAY16LE: AVPixelFormat = AVPixelFormat(30)
        public val YUV440P: AVPixelFormat = AVPixelFormat(31)
        public val YUVJ440P: AVPixelFormat = AVPixelFormat(32)
        public val YUVA420P: AVPixelFormat = AVPixelFormat(33)
        public val RGB48BE: AVPixelFormat = AVPixelFormat(34)
        public val RGB48LE: AVPixelFormat = AVPixelFormat(35)
        public val RGB565BE: AVPixelFormat = AVPixelFormat(36)
        public val RGB565LE: AVPixelFormat = AVPixelFormat(37)
        public val RGB555BE: AVPixelFormat = AVPixelFormat(38)
        public val RGB555LE: AVPixelFormat = AVPixelFormat(39)
        public val BGR565BE: AVPixelFormat = AVPixelFormat(40)
        public val BGR565LE: AVPixelFormat = AVPixelFormat(41)
        public val BGR555BE: AVPixelFormat = AVPixelFormat(42)
        public val BGR555LE: AVPixelFormat = AVPixelFormat(43)
        public val VAAPI: AVPixelFormat = AVPixelFormat(44)
        public val YUV420P16LE: AVPixelFormat = AVPixelFormat(45)
        public val YUV420P16BE: AVPixelFormat = AVPixelFormat(46)
        public val YUV422P16LE: AVPixelFormat = AVPixelFormat(47)
        public val YUV422P16BE: AVPixelFormat = AVPixelFormat(48)
        public val YUV444P16LE: AVPixelFormat = AVPixelFormat(49)
        public val YUV444P16BE: AVPixelFormat = AVPixelFormat(50)
        public val DXVA2_VLD: AVPixelFormat = AVPixelFormat(51)
        public val RGB444LE: AVPixelFormat = AVPixelFormat(52)
        public val RGB444BE: AVPixelFormat = AVPixelFormat(53)
        public val BGR444LE: AVPixelFormat = AVPixelFormat(54)
        public val BGR444BE: AVPixelFormat = AVPixelFormat(55)
        public val YA8: AVPixelFormat = AVPixelFormat(56)
        public val Y400A: AVPixelFormat = AVPixelFormat(YA8.num)
        public val GRAY8A: AVPixelFormat = AVPixelFormat(YA8.num)
        public val BGR48BE: AVPixelFormat = AVPixelFormat(YA8.num + 1)
        public val BGR48LE: AVPixelFormat = AVPixelFormat(YA8.num + 2)
        public val YUV420P9BE: AVPixelFormat = AVPixelFormat(YA8.num + 3)
        public val YUV420P9LE: AVPixelFormat = AVPixelFormat(YA8.num + 4)
        public val YUV420P10BE: AVPixelFormat = AVPixelFormat(YA8.num + 5)
        public val YUV420P10LE: AVPixelFormat = AVPixelFormat(YA8.num + 6)
        public val YUV422P10BE: AVPixelFormat = AVPixelFormat(YA8.num + 7)
        public val YUV422P10LE: AVPixelFormat = AVPixelFormat(YA8.num + 8)
        public val YUV444P9BE: AVPixelFormat = AVPixelFormat(YA8.num + 9)
        public val YUV444P9LE: AVPixelFormat = AVPixelFormat(YA8.num + 10)
        public val YUV444P10BE: AVPixelFormat = AVPixelFormat(YA8.num + 11)
        public val YUV444P10LE: AVPixelFormat = AVPixelFormat(YA8.num + 12)
        public val YUV422P9BE: AVPixelFormat = AVPixelFormat(YA8.num + 13)
        public val YUV422P9LE: AVPixelFormat = AVPixelFormat(YA8.num + 14)
        public val GBRP: AVPixelFormat = AVPixelFormat(YA8.num + 15)
        public val GBR24P: AVPixelFormat = AVPixelFormat(GBRP.num)
        public val GBRP9BE: AVPixelFormat = AVPixelFormat(GBRP.num + 1)
        public val GBRP9LE: AVPixelFormat = AVPixelFormat(GBRP.num + 2)
        public val GBRP10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 3)
        public val GBRP10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 4)
        public val GBRP16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 5)
        public val GBRP16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 6)
        public val YUVA422P: AVPixelFormat = AVPixelFormat(GBRP.num + 7)
        public val YUVA444P: AVPixelFormat = AVPixelFormat(GBRP.num + 8)
        public val YUVA420P9BE: AVPixelFormat = AVPixelFormat(GBRP.num + 9)
        public val YUVA420P9LE: AVPixelFormat = AVPixelFormat(GBRP.num + 10)
        public val YUVA422P9BE: AVPixelFormat = AVPixelFormat(GBRP.num + 11)
        public val YUVA422P9LE: AVPixelFormat = AVPixelFormat(GBRP.num + 12)
        public val YUVA444P9BE: AVPixelFormat = AVPixelFormat(GBRP.num + 13)
        public val YUVA444P9LE: AVPixelFormat = AVPixelFormat(GBRP.num + 14)
        public val YUVA420P10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 15)
        public val YUVA420P10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 16)
        public val YUVA422P10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 17)
        public val YUVA422P10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 18)
        public val YUVA444P10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 19)
        public val YUVA444P10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 20)
        public val YUVA420P16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 21)
        public val YUVA420P16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 22)
        public val YUVA422P16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 23)
        public val YUVA422P16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 24)
        public val YUVA444P16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 25)
        public val YUVA444P16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 26)
        public val VDPAU: AVPixelFormat = AVPixelFormat(GBRP.num + 27)
        public val XYZ12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 28)
        public val XYZ12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 29)
        public val NV16: AVPixelFormat = AVPixelFormat(GBRP.num + 30)
        public val NV20LE: AVPixelFormat = AVPixelFormat(GBRP.num + 31)
        public val NV20BE: AVPixelFormat = AVPixelFormat(GBRP.num + 32)
        public val RGBA64BE: AVPixelFormat = AVPixelFormat(GBRP.num + 33)
        public val RGBA64LE: AVPixelFormat = AVPixelFormat(GBRP.num + 34)
        public val BGRA64BE: AVPixelFormat = AVPixelFormat(GBRP.num + 35)
        public val BGRA64LE: AVPixelFormat = AVPixelFormat(GBRP.num + 36)
        public val YVYU422: AVPixelFormat = AVPixelFormat(GBRP.num + 37)
        public val YA16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 38)
        public val YA16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 39)
        public val GBRAP: AVPixelFormat = AVPixelFormat(GBRP.num + 40)
        public val GBRAP16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 41)
        public val GBRAP16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 42)
        public val QSV: AVPixelFormat = AVPixelFormat(GBRP.num + 43)
        public val MMAL: AVPixelFormat = AVPixelFormat(GBRP.num + 44)
        public val D3D11VA_VLD: AVPixelFormat = AVPixelFormat(GBRP.num + 45)
        public val CUDA: AVPixelFormat = AVPixelFormat(GBRP.num + 46)
        public val PIX_FMT_0RGB: AVPixelFormat = AVPixelFormat(GBRP.num + 47)
        public val RGB0: AVPixelFormat = AVPixelFormat(GBRP.num + 48)
        public val PIX_FMT_0BGR: AVPixelFormat = AVPixelFormat(GBRP.num + 49)
        public val BGR0: AVPixelFormat = AVPixelFormat(GBRP.num + 50)
        public val YUV420P12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 51)
        public val YUV420P12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 52)
        public val YUV420P14BE: AVPixelFormat = AVPixelFormat(GBRP.num + 53)
        public val YUV420P14LE: AVPixelFormat = AVPixelFormat(GBRP.num + 54)
        public val YUV422P12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 55)
        public val YUV422P12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 56)
        public val YUV422P14BE: AVPixelFormat = AVPixelFormat(GBRP.num + 57)
        public val YUV422P14LE: AVPixelFormat = AVPixelFormat(GBRP.num + 58)
        public val YUV444P12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 59)
        public val YUV444P12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 60)
        public val YUV444P14BE: AVPixelFormat = AVPixelFormat(GBRP.num + 61)
        public val YUV444P14LE: AVPixelFormat = AVPixelFormat(GBRP.num + 62)
        public val GBRP12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 63)
        public val GBRP12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 64)
        public val GBRP14BE: AVPixelFormat = AVPixelFormat(GBRP.num + 65)
        public val GBRP14LE: AVPixelFormat = AVPixelFormat(GBRP.num + 66)
        public val YUVJ411P: AVPixelFormat = AVPixelFormat(GBRP.num + 67)
        public val BAYER_BGGR8: AVPixelFormat = AVPixelFormat(GBRP.num + 68)
        public val BAYER_RGGB8: AVPixelFormat = AVPixelFormat(GBRP.num + 69)
        public val BAYER_GBRG8: AVPixelFormat = AVPixelFormat(GBRP.num + 70)
        public val BAYER_GRBG8: AVPixelFormat = AVPixelFormat(GBRP.num + 71)
        public val BAYER_BGGR16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 72)
        public val BAYER_BGGR16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 73)
        public val BAYER_RGGB16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 74)
        public val BAYER_RGGB16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 75)
        public val BAYER_GBRG16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 76)
        public val BAYER_GBRG16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 77)
        public val BAYER_GRBG16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 78)
        public val BAYER_GRBG16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 79)
        public val XVMC: AVPixelFormat = AVPixelFormat(GBRP.num + 80)
        public val YUV440P10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 81)
        public val YUV440P10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 82)
        public val YUV440P12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 83)
        public val YUV440P12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 84)
        public val AYUV64LE: AVPixelFormat = AVPixelFormat(GBRP.num + 85)
        public val AYUV64BE: AVPixelFormat = AVPixelFormat(GBRP.num + 86)
        public val VIDEOTOOLBOX: AVPixelFormat = AVPixelFormat(GBRP.num + 87)
        public val P010LE: AVPixelFormat = AVPixelFormat(GBRP.num + 88)
        public val P010BE: AVPixelFormat = AVPixelFormat(GBRP.num + 89)
        public val GBRAP12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 90)
        public val GBRAP12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 91)
        public val GBRAP10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 92)
        public val GBRAP10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 93)
        public val MEDIACODEC: AVPixelFormat = AVPixelFormat(GBRP.num + 94)
        public val GRAY12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 95)
        public val GRAY12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 96)
        public val GRAY10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 97)
        public val GRAY10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 98)
        public val P016LE: AVPixelFormat = AVPixelFormat(GBRP.num + 99)
        public val P016BE: AVPixelFormat = AVPixelFormat(GBRP.num + 100)
        public val D3D11: AVPixelFormat = AVPixelFormat(GBRP.num + 101)
        public val GRAY9BE: AVPixelFormat = AVPixelFormat(GBRP.num + 102)
        public val GRAY9LE: AVPixelFormat = AVPixelFormat(GBRP.num + 103)
        public val GBRPF32BE: AVPixelFormat = AVPixelFormat(GBRP.num + 104)
        public val GBRPF32LE: AVPixelFormat = AVPixelFormat(GBRP.num + 105)
        public val GBRAPF32BE: AVPixelFormat = AVPixelFormat(GBRP.num + 106)
        public val GBRAPF32LE: AVPixelFormat = AVPixelFormat(GBRP.num + 107)
        public val DRM_PRIME: AVPixelFormat = AVPixelFormat(GBRP.num + 108)
        public val OPENCL: AVPixelFormat = AVPixelFormat(GBRP.num + 109)
        public val GRAY14BE: AVPixelFormat = AVPixelFormat(GBRP.num + 110)
        public val GRAY14LE: AVPixelFormat = AVPixelFormat(GBRP.num + 111)
        public val GRAYF32BE: AVPixelFormat = AVPixelFormat(GBRP.num + 112)
        public val GRAYF32LE: AVPixelFormat = AVPixelFormat(GBRP.num + 113)
        public val YUVA422P12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 114)
        public val YUVA422P12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 115)
        public val YUVA444P12BE: AVPixelFormat = AVPixelFormat(GBRP.num + 116)
        public val YUVA444P12LE: AVPixelFormat = AVPixelFormat(GBRP.num + 117)
        public val NV24: AVPixelFormat = AVPixelFormat(GBRP.num + 118)
        public val NV42: AVPixelFormat = AVPixelFormat(GBRP.num + 119)
        public val VULKAN: AVPixelFormat = AVPixelFormat(GBRP.num + 120)
        public val Y210BE: AVPixelFormat = AVPixelFormat(GBRP.num + 121)
        public val Y210LE: AVPixelFormat = AVPixelFormat(GBRP.num + 122)
        public val X2RGB10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 123)
        public val X2RGB10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 124)
        public val X2BGR10LE: AVPixelFormat = AVPixelFormat(GBRP.num + 125)
        public val X2BGR10BE: AVPixelFormat = AVPixelFormat(GBRP.num + 126)
        public val P210BE: AVPixelFormat = AVPixelFormat(GBRP.num + 127)
        public val P210LE: AVPixelFormat = AVPixelFormat(GBRP.num + 128)
        public val P410BE: AVPixelFormat = AVPixelFormat(GBRP.num + 129)
        public val P410LE: AVPixelFormat = AVPixelFormat(GBRP.num + 130)
        public val P216BE: AVPixelFormat = AVPixelFormat(GBRP.num + 131)
        public val P216LE: AVPixelFormat = AVPixelFormat(GBRP.num + 132)
        public val P416BE: AVPixelFormat = AVPixelFormat(GBRP.num + 133)
        public val P416LE: AVPixelFormat = AVPixelFormat(GBRP.num + 134)
        public val VUYA: AVPixelFormat = AVPixelFormat(GBRP.num + 135)
        public val RGBAF16BE: AVPixelFormat = AVPixelFormat(GBRP.num + 136)
        public val RGBAF16LE: AVPixelFormat = AVPixelFormat(GBRP.num + 137)
        public val VUYX: AVPixelFormat = AVPixelFormat(GBRP.num + 138)
        public val P012LE: AVPixelFormat = AVPixelFormat(GBRP.num + 139)
        public val P012BE: AVPixelFormat = AVPixelFormat(GBRP.num + 140)
        public val Y212BE: AVPixelFormat = AVPixelFormat(GBRP.num + 141)
        public val Y212LE: AVPixelFormat = AVPixelFormat(GBRP.num + 142)
        public val XV30BE: AVPixelFormat = AVPixelFormat(GBRP.num + 143)
        public val XV30LE: AVPixelFormat = AVPixelFormat(GBRP.num + 144)
        public val XV36BE: AVPixelFormat = AVPixelFormat(GBRP.num + 145)
        public val XV36LE: AVPixelFormat = AVPixelFormat(GBRP.num + 146)
        public val RGBF32BE: AVPixelFormat = AVPixelFormat(GBRP.num + 147)
        public val RGBF32LE: AVPixelFormat = AVPixelFormat(GBRP.num + 148)
        public val RGBAF32BE: AVPixelFormat = AVPixelFormat(GBRP.num + 149)
        public val RGBAF32LE: AVPixelFormat = AVPixelFormat(GBRP.num + 150)
        public val P212BE: AVPixelFormat = AVPixelFormat(GBRP.num + 151)
        public val P212LE: AVPixelFormat = AVPixelFormat(GBRP.num + 152)
        public val P412BE: AVPixelFormat = AVPixelFormat(GBRP.num + 153)
        public val P412LE: AVPixelFormat = AVPixelFormat(GBRP.num + 154)
        public val GBRAP14BE: AVPixelFormat = AVPixelFormat(GBRP.num + 155)
        public val GBRAP14LE: AVPixelFormat = AVPixelFormat(GBRP.num + 156)
        public val NB: AVPixelFormat = AVPixelFormat(GBRP.num + 157)
    }
}