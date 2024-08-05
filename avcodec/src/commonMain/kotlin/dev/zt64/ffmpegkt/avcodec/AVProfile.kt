package dev.zt64.ffmpegkt.avcodec

import kotlin.jvm.JvmInline

public data class AVProfile(public val profile: Int, public val name: String)

@JvmInline
public value class AVProfileType(public val value: Int) {
    public companion object {
        public val UNKNOWN: Int = -99
        public val RESERVED: Int = -100
        public val AAC_MAIN: Int = 0
        public val AAC_LOW: Int = 1
        public val AAC_SSR: Int = 2
        public val AAC_LTP: Int = 3
        public val AAC_HE: Int = 4
        public val AAC_HE_V2: Int = 28
        public val AAC_LD: Int = 22
        public val AAC_ELD: Int = 38
        public val MPEG2_AAC_LOW: Int = 128
        public val MPEG2_AAC_HE: Int = 131
        public val DNXHD: Int = 0
        public val DNXHR_LB: Int = 1
        public val DNXHR_SQ: Int = 2
        public val DNXHR_HQ: Int = 3
        public val DNXHR_HQX: Int = 4
        public val DNXHR_444: Int = 5
        public val DTS: Int = 20
        public val DTS_ES: Int = 30
        public val DTS_96_24: Int = 40
        public val DTS_HD_HRA: Int = 50
        public val DTS_HD_MA: Int = 60
        public val DTS_EXPRESS: Int = 70
        public val DTS_HD_MA_X: Int = 61
        public val DTS_HD_MA_X_IMAX: Int = 62
        public val EAC3_DDP_ATMOS: Int = 30
        public val TRUEHD_ATMOS: Int = 30
        public val MPEG2_422: Int = 0
        public val MPEG2_HIGH: Int = 1
        public val MPEG2_SS: Int = 2
        public val MPEG2_SNR_SCALABLE: Int = 3
        public val MPEG2_MAIN: Int = 4
        public val MPEG2_SIMPLE: Int = 5
        public val H264_CONSTRAINED: Int = 1 shl 9
        public val H264_INTRA: Int = 1 shl 11
        public val H264_BASELINE: Int = 66
        public val H264_CONSTRAINED_BASELINE: Int = 66 or H264_CONSTRAINED
        public val H264_MAIN: Int = 77
        public val H264_EXTENDED: Int = 88
        public val H264_HIGH: Int = 100
        public val H264_HIGH_10: Int = 110
        public val H264_HIGH_10_INTRA: Int = 110 or H264_INTRA
        public val H264_MULTIVIEW_HIGH: Int = 118
        public val H264_HIGH_422: Int = 122
        public val H264_HIGH_422_INTRA: Int = 122 or H264_INTRA
        public val H264_STEREO_HIGH: Int = 128
        public val H264_HIGH_444: Int = 144
        public val H264_HIGH_444_PREDICTIVE: Int = 244
        public val H264_HIGH_444_INTRA: Int = 244 or H264_INTRA
        public val H264_CAVLC_444: Int = 44
        public val VC1_SIMPLE: Int = 0
        public val VC1_MAIN: Int = 1
        public val VC1_COMPLEX: Int = 2
        public val VC1_ADVANCED: Int = 3
        public val MPEG4_SIMPLE: Int = 0
        public val MPEG4_SIMPLE_SCALABLE: Int = 1
        public val MPEG4_CORE: Int = 2
        public val MPEG4_MAIN: Int = 3
        public val MPEG4_N_BIT: Int = 4
        public val MPEG4_SCALABLE_TEXTURE: Int = 5
        public val MPEG4_SIMPLE_FACE_ANIMATION: Int = 6
        public val MPEG4_BASIC_ANIMATED_TEXTURE: Int = 7
        public val MPEG4_HYBRID: Int = 8
        public val MPEG4_ADVANCED_REAL_TIME: Int = 9
        public val MPEG4_CORE_SCALABLE: Int = 10
        public val MPEG4_ADVANCED_CODING: Int = 11
        public val MPEG4_ADVANCED_CORE: Int = 12
        public val MPEG4_ADVANCED_SCALABLE_TEXTURE: Int = 13
        public val MPEG4_SIMPLE_STUDIO: Int = 14
        public val MPEG4_ADVANCED_SIMPLE: Int = 15
        public val JPEG2000_CSTREAM_RESTRICTION_0: Int = 1
        public val JPEG2000_CSTREAM_RESTRICTION_1: Int = 2
        public val JPEG2000_CSTREAM_NO_RESTRICTION: Int = 32768
        public val JPEG2000_DCINEMA_2K: Int = 3
        public val JPEG2000_DCINEMA_4K: Int = 4
        public val VP9_0: Int = 0
        public val VP9_1: Int = 1
        public val VP9_2: Int = 2
        public val VP9_3: Int = 3
        public val HEVC_MAIN: Int = 1
        public val HEVC_MAIN_10: Int = 2
        public val HEVC_MAIN_STILL_PICTURE: Int = 3
        public val HEVC_REXT: Int = 4
        public val HEVC_SCC: Int = 9
        public val VVC_MAIN_10: Int = 1
        public val VVC_MAIN_10_444: Int = 33
        public val AV1_MAIN: Int = 0
        public val AV1_HIGH: Int = 1
        public val AV1_PROFESSIONAL: Int = 2
        public val MJPEG_HUFFMAN_BASELINE_DCT: Int = 0xc0
        public val MJPEG_HUFFMAN_EXTENDED_SEQUENTIAL_DCT: Int = 0xc1
        public val MJPEG_HUFFMAN_PROGRESSIVE_DCT: Int = 0xc2
        public val MJPEG_HUFFMAN_LOSSLESS: Int = 0xc3
        public val MJPEG_JPEG_LS: Int = 0xf7
        public val SBC_MSBC: Int = 1
        public val PRORES_PROXY: Int = 0
        public val PRORES_LT: Int = 1
        public val PRORES_STANDARD: Int = 2
        public val PRORES_HQ: Int = 3
        public val PRORES_4444: Int = 4
        public val PRORES_XQ: Int = 5
        public val ARIB_PROFILE_A: Int = 0
        public val ARIB_PROFILE_C: Int = 1
        public val KLVA_SYNC: Int = 0
        public val KLVA_ASYNC: Int = 1
        public val EVC_BASELINE: Int = 0
        public val EVC_MAIN: Int = 1
    }
}