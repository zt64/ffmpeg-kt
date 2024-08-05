package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVMediaType
import kotlin.jvm.JvmInline

@JvmInline
public value class AVCodecID(public val num: Int) {
    @Suppress("ktlint:standard:backing-property-naming", "ObjectPropertyName")
    public companion object {
        private var iter = 0
        private inline fun id(offset: Int? = null): AVCodecID {
            return AVCodecID(offset?.also { iter = it } ?: iter.also { iter++ }).also {
            }
        }

        public val NONE: AVCodecID = id()

        /* video codecs */
        public val MPEG1VIDEO: AVCodecID = id()

        /** preferred ID for MPEG-1/2 video decoding */
        public val MPEG2VIDEO: AVCodecID = id()
        public val H261: AVCodecID = id()
        public val H263: AVCodecID = id()
        public val RV10: AVCodecID = id()
        public val RV20: AVCodecID = id()
        public val MJPEG: AVCodecID = id()
        public val MJPEGB: AVCodecID = id()
        public val LJPEG: AVCodecID = id()
        public val SP5X: AVCodecID = id()
        public val JPEGLS: AVCodecID = id()
        public val MPEG4: AVCodecID = id()

        public val RAWVIDEO: AVCodecID = id()
        public val MSMPEG4V1: AVCodecID = id()
        public val MSMPEG4V2: AVCodecID = id()
        public val MSMPEG4V3: AVCodecID = id()
        public val WMV1: AVCodecID = id()
        public val WMV2: AVCodecID = id()
        public val H263P: AVCodecID = id()
        public val H263I: AVCodecID = id()
        public val FLV1: AVCodecID = id()
        public val SVQ1: AVCodecID = id()
        public val SVQ3: AVCodecID = id()
        public val DVVIDEO: AVCodecID = id()
        public val HUFFYUV: AVCodecID = id()
        public val CYUV: AVCodecID = id()
        public val H264: AVCodecID = id()
        public val INDEO3: AVCodecID = id()
        public val VP3: AVCodecID = id()
        public val THEORA: AVCodecID = id()
        public val ASV1: AVCodecID = id()
        public val ASV2: AVCodecID = id()
        public val FFV1: AVCodecID = id()
        public val _4XM: AVCodecID = id()
        public val VCR1: AVCodecID = id()
        public val CLJR: AVCodecID = id()
        public val MDEC: AVCodecID = id()
        public val ROQ: AVCodecID = id()
        public val INTERPLAY_VIDEO: AVCodecID = id()
        public val XAN_WC3: AVCodecID = id()
        public val XAN_WC4: AVCodecID = id()
        public val RPZA: AVCodecID = id()
        public val CINEPAK: AVCodecID = id()
        public val WS_VQA: AVCodecID = id()
        public val MSRLE: AVCodecID = id()
        public val MSVIDEO1: AVCodecID = id()
        public val IDCIN: AVCodecID = id()
        public val _8BPS: AVCodecID = id()
        public val SMC: AVCodecID = id()
        public val FLIC: AVCodecID = id()
        public val TRUEMOTION1: AVCodecID = id()
        public val VMDVIDEO: AVCodecID = id()
        public val MSZH: AVCodecID = id()
        public val ZLIB: AVCodecID = id()
        public val QTRLE: AVCodecID = id()
        public val SNOW: AVCodecID = id()
        public val TSCC: AVCodecID = id()
        public val ULTI: AVCodecID = id()
        public val QDRAW: AVCodecID = id()
        public val VIXL: AVCodecID = id()
        public val QPEG: AVCodecID = id()
        public val XVID: AVCodecID = id()
        public val PNG: AVCodecID = id()
        public val PPM: AVCodecID = id()
        public val PBM: AVCodecID = id()
        public val PGM: AVCodecID = id()
        public val PGMYUV: AVCodecID = id()
        public val PAM: AVCodecID = id()
        public val FFVHUFF: AVCodecID = id()
        public val RV30: AVCodecID = id()
        public val RV40: AVCodecID = id()
        public val VC1: AVCodecID = id()
        public val WMV3: AVCodecID = id()
        public val LOCO: AVCodecID = id()
        public val WNV1: AVCodecID = id()
        public val AASC: AVCodecID = id()
        public val INDEO2: AVCodecID = id()
        public val FRAPS: AVCodecID = id()
        public val TRUEMOTION2: AVCodecID = id()
        public val BMP: AVCodecID = id()
        public val CSCD: AVCodecID = id()
        public val MMVIDEO: AVCodecID = id()
        public val ZMBV: AVCodecID = id()
        public val AVS: AVCodecID = id()
        public val SMACKVIDEO: AVCodecID = id()
        public val NUV: AVCodecID = id()
        public val KMVC: AVCodecID = id()
        public val FLASHSV: AVCodecID = id()
        public val CAVS: AVCodecID = id()
        public val JPEG2000: AVCodecID = id()
        public val VMNC: AVCodecID = id()
        public val VP5: AVCodecID = id()
        public val VP6: AVCodecID = id()
        public val VP6F: AVCodecID = id()
        public val TARGA: AVCodecID = id()
        public val DSICINVIDEO: AVCodecID = id()
        public val TIERTEXSEQVIDEO: AVCodecID = id()
        public val TIFF: AVCodecID = id()
        public val GIF: AVCodecID = id()
        public val FFH264: AVCodecID = id()
        public val DXA: AVCodecID = id()
        public val DNXHD: AVCodecID = id()
        public val THP: AVCodecID = id()
        public val SGI: AVCodecID = id()
        public val C93: AVCodecID = id()
        public val BETHSOFTVID: AVCodecID = id()
        public val PTX: AVCodecID = id()
        public val TXD: AVCodecID = id()
        public val VP6A: AVCodecID = id()
        public val AMV: AVCodecID = id()
        public val VB: AVCodecID = id()
        public val PCX: AVCodecID = id()
        public val SUNRAST: AVCodecID = id()
        public val INDEO4: AVCodecID = id()
        public val INDEO5: AVCodecID = id()
        public val MIMIC: AVCodecID = id()
        public val RL2: AVCodecID = id()
        public val _8SVX_EXP: AVCodecID = id()
        public val _8SVX_FIB: AVCodecID = id()
        public val ESCAPE124: AVCodecID = id()
        public val DIRAC: AVCodecID = id()
        public val BFI: AVCodecID = id()
        public val CMV: AVCodecID = id()
        public val MOTIONPIXELS: AVCodecID = id()
        public val TGV: AVCodecID = id()
        public val TGQ: AVCodecID = id()
        public val TQI: AVCodecID = id()
        public val AURA: AVCodecID = id()
        public val AURA2: AVCodecID = id()
        public val V210X: AVCodecID = id()
        public val TMV: AVCodecID = id()
        public val V210: AVCodecID = id()
        public val DPX: AVCodecID = id()
        public val MAD: AVCodecID = id()
        public val FRWU: AVCodecID = id()
        public val FLASHSV2: AVCodecID = id()
        public val CDGRAPHICS: AVCodecID = id()
        public val R210: AVCodecID = id()
        public val ANM: AVCodecID = id()
        public val BINKVIDEO: AVCodecID = id()
        public val IFF_ILBM: AVCodecID = id()
        public val IFF_BYTERUN1: AVCodecID = id()
        public val KGV1: AVCodecID = id()
        public val YOP: AVCodecID = id()
        public val VP8: AVCodecID = id()

        /* various PCM "codecs" */
        public val PCM_S16LE: AVCodecID = id(0x10000)
        public val PCM_S16BE: AVCodecID = id()
        public val PCM_U16LE: AVCodecID = id()
        public val PCM_U16BE: AVCodecID = id()
        public val PCM_S8: AVCodecID = id()
        public val PCM_U8: AVCodecID = id()
        public val PCM_MULAW: AVCodecID = id()
        public val PCM_ALAW: AVCodecID = id()
        public val PCM_S32LE: AVCodecID = id()
        public val PCM_S32BE: AVCodecID = id()
        public val PCM_U32LE: AVCodecID = id()
        public val PCM_U32BE: AVCodecID = id()
        public val PCM_S24LE: AVCodecID = id()
        public val PCM_S24BE: AVCodecID = id()
        public val PCM_U24LE: AVCodecID = id()
        public val PCM_U24BE: AVCodecID = id()
        public val PCM_S24DAUD: AVCodecID = id()
        public val PCM_ZORK: AVCodecID = id()
        public val PCM_S16LE_PLANAR: AVCodecID = id()
        public val PCM_DVD: AVCodecID = id()
        public val PCM_F32BE: AVCodecID = id()
        public val PCM_F32LE: AVCodecID = id()
        public val PCM_F64BE: AVCodecID = id()
        public val PCM_F64LE: AVCodecID = id()
        public val PCM_BLURAY: AVCodecID = id()

        /* various ADPCM codecs */
        public val ADPCM_IMA_QT: AVCodecID = id(0x11000)
        public val ADPCM_IMA_WAV: AVCodecID = id()
        public val ADPCM_IMA_DK3: AVCodecID = id()
        public val ADPCM_IMA_DK4: AVCodecID = id()
        public val ADPCM_IMA_WS: AVCodecID = id()
        public val ADPCM_IMA_SMJPEG: AVCodecID = id()
        public val ADPCM_MS: AVCodecID = id()
        public val ADPCM_4XM: AVCodecID = id()
        public val ADPCM_XA: AVCodecID = id()
        public val ADPCM_ADX: AVCodecID = id()
        public val ADPCM_EA: AVCodecID = id()
        public val ADPCM_G726: AVCodecID = id()
        public val ADPCM_CT: AVCodecID = id()
        public val ADPCM_SWF: AVCodecID = id()
        public val ADPCM_YAMAHA: AVCodecID = id()
        public val ADPCM_SBPRO_4: AVCodecID = id()
        public val ADPCM_SBPRO_3: AVCodecID = id()
        public val ADPCM_SBPRO_2: AVCodecID = id()
        public val ADPCM_THP: AVCodecID = id()
        public val ADPCM_IMA_AMV: AVCodecID = id()
        public val ADPCM_EA_R1: AVCodecID = id()
        public val ADPCM_EA_R3: AVCodecID = id()
        public val ADPCM_EA_R2: AVCodecID = id()
        public val ADPCM_IMA_EA_SEAD: AVCodecID = id()
        public val ADPCM_IMA_EA_EACS: AVCodecID = id()
        public val ADPCM_EA_XAS: AVCodecID = id()
        public val ADPCM_EA_MAXIS_XA: AVCodecID = id()
        public val ADPCM_IMA_ISS: AVCodecID = id()

        /* AMR */
        public val AMR_NB: AVCodecID = id(0x12000)
        public val AMR_WB: AVCodecID = id()

        /* RealAudio codecs*/
        public val RA_144: AVCodecID = id(0x13000)
        public val RA_288: AVCodecID = id()

        /* various DPCM codecs */
        public val ROQ_DPCM: AVCodecID = id(0x14000)
        public val INTERPLAY_DPCM: AVCodecID = id()
        public val XAN_DPCM: AVCodecID = id()
        public val SOL_DPCM: AVCodecID = id()

        /* audio codecs */
        public val MP2: AVCodecID = id(0x15000)

        /** preferred ID for decoding MPEG audio layer 1, 2 or 3 */
        public val MP3: AVCodecID = id()
        public val AAC: AVCodecID = id()
        public val AC3: AVCodecID = id()
        public val DTS: AVCodecID = id()
        public val VORBIS: AVCodecID = id()
        public val DVAUDIO: AVCodecID = id()
        public val WMAV1: AVCodecID = id()
        public val WMAV2: AVCodecID = id()
        public val MACE3: AVCodecID = id()
        public val MACE6: AVCodecID = id()
        public val VMDAUDIO: AVCodecID = id()
        public val SONIC: AVCodecID = id()
        public val SONIC_LS: AVCodecID = id()
        public val FLAC: AVCodecID = id()
        public val MP3ADU: AVCodecID = id()
        public val MP3ON4: AVCodecID = id()
        public val SHORTEN: AVCodecID = id()
        public val ALAC: AVCodecID = id()
        public val WESTWOOD_SND1: AVCodecID = id()

        /** as in Berlin toast format */
        public val GSM: AVCodecID = id()
        public val QDM2: AVCodecID = id()
        public val COOK: AVCodecID = id()
        public val TRUESPEECH: AVCodecID = id()
        public val TTA: AVCodecID = id()
        public val SMACKAUDIO: AVCodecID = id()
        public val QCELP: AVCodecID = id()
        public val WAVPACK: AVCodecID = id()
        public val DSICINAUDIO: AVCodecID = id()
        public val IMC: AVCodecID = id()
        public val MUSEPACK7: AVCodecID = id()
        public val MLP: AVCodecID = id()

        /** As found in WAV */
        public val GSM_MS: AVCodecID = id()
        public val ATRAC3: AVCodecID = id()
        public val VOXWARE: AVCodecID = id()
        public val APE: AVCodecID = id()
        public val NELLYMOSER: AVCodecID = id()
        public val MUSEPACK8: AVCodecID = id()
        public val SPEEX: AVCodecID = id()
        public val WMAVOICE: AVCodecID = id()
        public val WMAPRO: AVCodecID = id()
        public val WMALOSSLESS: AVCodecID = id()
        public val ATRAC3P: AVCodecID = id()
        public val EAC3: AVCodecID = id()
        public val SIPR: AVCodecID = id()
        public val MP1: AVCodecID = id()
        public val TWINVQ: AVCodecID = id()
        public val TRUEHD: AVCodecID = id()
        public val MP4ALS: AVCodecID = id()
        public val ATRAC1: AVCodecID = id()
        public val BINKAUDIO_RDFT: AVCodecID = id()
        public val BINKAUDIO_DCT: AVCodecID = id()

        /* subtitle codecs */
        public val DVD_SUBTITLE: AVCodecID = id(0x17000)
        public val DVB_SUBTITLE: AVCodecID = id()
        public val TEXT: AVCodecID = id()
        public val XSUB: AVCodecID = id()
        public val SSA: AVCodecID = id()
        public val MOV_TEXT: AVCodecID = id()
        public val HDMV_PGS_SUBTITLE: AVCodecID = id()
        public val DVB_TELETEXT: AVCodecID = id()

        /* other specific kind of codecs (generally used for attachments) */
        public val TTF: AVCodecID = id(0x18000)
        public val PROBE: AVCodecID = id(0x19000)

        // _FAKE_ codec to indicate a raw MPEG-2 TS stream (only used by libavformat)
        public val MPEG2TS: AVCodecID = id(0x20000)
    }
}

public expect val AVCodecID.type: AVMediaType