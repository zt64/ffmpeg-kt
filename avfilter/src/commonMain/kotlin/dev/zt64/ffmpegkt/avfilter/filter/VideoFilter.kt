package dev.zt64.ffmpegkt.avfilter.filter

import dev.zt64.ffmpegkt.avutil.PixelFormat

/**
 * TODO
 *
 * @constructor
 *
 * @param name
 *
 * See also: https://ffmpeg.org/ffmpeg-filters.html
 */
public sealed class VideoFilter(name: String) : Filter(name) {
    /**
     * Add region of interest to frame.
     */
    public data class AddROI(
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int,
    ) : VideoFilter("addroi")

    /**
     * Extract an alpha channel as a grayscale image component.
     */
    public data object AlphaExtract : VideoFilter("alphaextract")

    /**
     * Copy the luma value of the second input into the alpha channel of the first input.
     */
    public data object AlphaMerge : VideoFilter("alphamerge")

    /**
     * Amplify changes between successive video frames.
     */
    public data class Amplify(
        val radius: Int = 2,
        val factor: Int = 2,
        val threshold: Int = 10,
        val tolerance: Int = 0,
        val low: Int = 65535,
        val high: Int = 65535,
        val planes: Int? = null,
    ) : VideoFilter("amplify")

    /**
     * Render ASS subtitles onto input video using the libass library.
     */
    public data class ASS(val shaping: Shaping = Shaping.AUTO) : VideoFilter("ass") {
        public enum class Shaping {
            AUTO,
            SIMPLE,
            COMPLEX
        }
    }

    /**
     * Apply an Adaptive Temporal Averaging Denoiser.
     */
    public data class ATADenoise() : VideoFilter("atadenoise")

    /**
     * Apply Average Blur filter.
     */
    public data class AvgBlur(
        val width: Int,
        val height: Int
    ) : VideoFilter("avgblur")

    /**
     * Apply average blur filter
     */
    public data class AvgBlurOpencl() : VideoFilter("avgblur_opencl")

    /**
     * Turns a static background into transparency.
     */
    public data class BackgroundKey() : VideoFilter("backgroundkey")

    /**
     * Compute bounding box for each frame.
     */
    public data class BBox(val minVal: Int = 16) : VideoFilter("bbox")

    /**
     * Benchmark part of a filtergraph.
     */
    public data class Bench() : VideoFilter("bench")

    /**
     * Apply Bilateral filter.
     */
    public data class Bilateral() : VideoFilter("bilateral")

    /**
     * GPU accelerated bilateral filter
     */
    public data class BilateralCuda() : VideoFilter("bilateral_cuda")

    /**
     * Measure bit plane noise.
     */
    public data class BitPlaneNoise() : VideoFilter("bitplanenoise")

    /**
     * Detect video intervals that are (almost) black.
     */
    public data class BlackDetect() : VideoFilter("blackdetect")

    /**
     * Detect frames that are (almost) black.
     */
    public data class BlackFrame() : VideoFilter("blackframe")

    /**
     * Blend two video frames into each other.
     */
    public data class Blend() : VideoFilter("blend")

    /**
     * Blockdetect filter.
     */
    public data class Blockdetect() : VideoFilter("blockdetect")

    /**
     * Blurdetect filter.
     */
    public data class Blurdetect() : VideoFilter("blurdetect")

    /**
     * Block-Matching 3D denoiser.
     */
    public data class BM3D() : VideoFilter("bm3d")

    /**
     * Blur the input.
     */
    public data class BoxBlur() : VideoFilter("boxblur")

    /**
     * Apply boxblur filter to input video
     */
    public data class boxblurOpencl() : VideoFilter("boxblur_opencl")

    /**
     * Deinterlace the input image.
     */
    public data class Bwdif() : VideoFilter("bwdif")

    /**
     * Deinterlace CUDA frames
     */
    public data class BwdifCuda() : VideoFilter("bwdif_cuda")

    /**
     * Contrast Adaptive Sharpen.
     */
    public data class CAS(val strength: Int = 0, val planes: Int? = null) : VideoFilter("cas")

    /**
     * Repack CEA-708 closed caption metadata
     */
    public data class CCREPack() : VideoFilter("ccrepack")

    /**
     * Turns a certain color range into gray.
     */
    public data class ChromaHold(
        val color: Long,
        val similarity: Float = 0.01f,
        val blend: Float = 0.0f,
        val yuv: Boolean = false,
    ) : VideoFilter("chromahold")

    /**
     * Turns a certain color into transparency. Operates on YUV colors.
     */
    public data class Chromakey() : VideoFilter("chromakey")

    /**
     * GPU accelerated chromakey filter
     */
    public data class ChromakeyCuda() : VideoFilter("chromakey_cuda")

    /**
     * Reduce chrominance noise.
     */
    public data class ChromaNR() : VideoFilter("chromanr")

    /**
     * Shift chroma.
     */
    public data class ChromaShift() : VideoFilter("chromashift")

    /**
     * Video CIE scope.
     */
    public data class CIEScope() : VideoFilter("ciescope")

    /**
     * Visualize information about some codecs.
     */
    public data class CodecView() : VideoFilter("codecview")

    /**
     * Adjust the color balance.
     */
    public data class ColorBalance() : VideoFilter("colorbalance")

    /**
     * Adjust colors by mixing color channels.
     */
    public data class ColorChannelMixer() : VideoFilter("colorchannelmixer")

    /**
     * Adjust color contrast between RGB components.
     */
    public data class ColorContrast() : VideoFilter("colorcontrast")

    /**
     * Adjust color white balance selectively for blacks and whites.
     */
    public data class Colorcorrect() : VideoFilter("colorcorrect")

    /**
     * Overlay a solid color on the video stream.
     */
    public data class Colorize(
        val hue: Int = 0,
        val saturation: Float = 0.5f,
        val lightness: Float = 0.5f,
        val mix: Float = 1.0f
    ) : VideoFilter("colorize")

    /**
     * Turns a certain color into transparency. Operates on RGB colors.
     */
    public data class Colorkey() : VideoFilter("colorkey")

    /**
     * Turns a certain color into transparency. Operates on RGB colors.
     */
    public data class ColorkeyOpencl() : VideoFilter("colorkey_opencl")

    /**
     * Turns a certain color range into gray. Operates on RGB colors.
     */
    public data class Colorhold() : VideoFilter("colorhold")

    /**
     * Adjust the color levels.
     */
    public data class ColorLevels() : VideoFilter("colorlevels")

    /**
     * Apply custom Color Maps to video stream.
     */
    public data class ColorMap() : VideoFilter("colormap")

    /**
     * Convert color matrix.
     */
    public data class Colormatrix() : VideoFilter("colormatrix")

    /**
     * Convert between colorspaces.
     */
    public data class Colorspace() : VideoFilter("colorspace")

    /**
     * CUDA accelerated video color converter
     */
    public data class ColorspaceCuda() : VideoFilter("colorspace_cuda")

    /**
     * Adjust color temperature of video.
     */
    public data class ColorTemperature(
        val temperature: Int = 6500,
        val mix: Float = 1f,
        val pl: Float = 0f
    ) : VideoFilter("colortemperature")

    /**
     * Apply convolution filter.
     */
    public data class Convolution() : VideoFilter("convolution")

    /**
     * Apply convolution mask to input video
     */
    public data class ConvolutionOpencl() : VideoFilter("convolution_opencl")

    /**
     * Convolve first video stream with second video stream.
     */
    public data class Convolve(
        val planes: Int,
        val impulse: String
    ) : VideoFilter("convolve")

    /**
     * Copy the input video unchanged to the output.
     */
    public data object Copy : VideoFilter("copy")

    /**
     * Calculate the correlation between two video streams.
     */
    public data object Corr : VideoFilter("corr")

    /**
     * Find and cover a user specified object.
     */
    public data class CoverRect() : VideoFilter("cover_rect")

    /**
     * Crop the input video.
     */
    public data class Crop() : VideoFilter("crop")

    /**
     * Auto-detect crop size.
     */
    public data class Cropdetect() : VideoFilter("cropdetect")

    /**
     * Delay filtering to match a cue.
     */
    public data class Cue(
        val cue: Int = 0,
        val preroll: Int = 0,
        val buffer: Int = 0
    ) : VideoFilter("cue")

    /**
     * Adjust components curves.
     */
    public data class Curves(
        val preset: Preset = Preset.NONE,
    ) : VideoFilter("curves") {
        public enum class Preset {
            NONE,
            COLOR_NEGATIVE,
            COLOR_PROCESS,
            DARKER,
            LIGHTER,
            INCREASE_CONTRAST,
            LINEAR_CONTRAST,
            MEDIUM_CONTRAST,
            STRONG_CONTRAST,
            NEGATIVE,
            VINTAGE
        }
    }

    /**
     * Video data analysis.
     */
    public data class DataScope() : VideoFilter("datascope")

    /**
     * Apply Directional Blur filter.
     */
    public data class DBlur(
        val angle: Int = 45,
        val radius: Int = 5,
        val planes: Int? = null
    ) : VideoFilter("dblur")

    /**
     * Denoise frames using 2D DCT.
     */
    public data class Dctdnoiz() : VideoFilter("dctdnoiz")

    /**
     * Debands video.
     */
    public data class Deband() : VideoFilter("deband")

    /**
     * Deblock video.
     */
    public data class Deblock() : VideoFilter("deblock")

    /**
     * Decimate frames (post field matching filter).
     */
    public data class Decimate() : VideoFilter("decimate")

    /**
     * Deconvolve first video stream with second video stream.
     */
    public data class Deconvolve() : VideoFilter("deconvolve")

    /**
     * Reduce cross-luminance and cross-color.
     */
    public data class Dedot() : VideoFilter("dedot")

    /**
     * Apply deflate effect.
     */
    public data class Deflate() : VideoFilter("deflate")

    /**
     * Remove temporal frame luminance variations.
     */
    public data class Deflicker() : VideoFilter("deflicker")

    /**
     * Quick Sync Video "deinterlacing"
     */
    public data class DeinterlaceQsv() : VideoFilter("deinterlace_qsv")

    /**
     * Deinterlacing of VAAPI surfaces
     */
    public data class DeinterlaceVaapi() : VideoFilter("deinterlace_vaapi")

    /**
     * Remove judder produced by pullup.
     */
    public data class Dejudder() : VideoFilter("dejudder")

    /**
     * Remove logo from input video.
     */
    public data class Delogo() : VideoFilter("delogo")

    /**
     * VAAPI VPP for de-noise
     */
    public data class DenoiseVaapi() : VideoFilter("denoise_vaapi")

    /**
     * Apply derain filter to the input.
     */
    public data class Derain() : VideoFilter("derain")

    /**
     * Stabilize shaky video.
     */
    public data class Deshake() : VideoFilter("deshake")

    /**
     * Feature-point based video stabilization filter
     */
    public data class DeshakeOpencl() : VideoFilter("deshake_opencl")

    /**
     * Despill video.
     */
    public data class Despill() : VideoFilter("despill")

    /**
     * Apply an inverse telecine pattern.
     */
    public data class Detelecine() : VideoFilter("detelecine")

    /**
     * Apply dilation effect.
     */
    public data class Dilation() : VideoFilter("dilation")

    /**
     * Apply dilation effect
     */
    public data class DilationOpencl() : VideoFilter("dilation_opencl")

    /**
     * Displace pixels.
     */
    public data class Displace() : VideoFilter("displace")

    /**
     * Apply DNN classify filter to the input.
     */
    public data class DnnClassify() : VideoFilter("dnn_classify")

    /**
     * Apply DNN detect filter to the input.
     */
    public data class DnnDetect() : VideoFilter("dnn_detect")

    /**
     * Apply DNN processing filter to the input.
     */
    public data class DnnProcessing() : VideoFilter("dnn_processing")

    /**
     * Weave input video fields into double number of frames.
     */
    public data class Doubleweave() : VideoFilter("doubleweave")

    /**
     * Draw a colored box on the input video.
     */
    public data class Drawbox() : VideoFilter("drawbox")

    /**
     * Draw a graph using input video metadata.
     */
    public data class Drawgraph() : VideoFilter("drawgraph")

    /**
     * Draw a colored grid on the input video.
     */
    public data class Drawgrid() : VideoFilter("drawgrid")

    /**
     * Draw text on top of video frames using libfreetype library.
     */
    public data class Drawtext() : VideoFilter("drawtext")

    /**
     * Detect and draw edge.
     */
    public data class Edgedetect() : VideoFilter("edgedetect")

    /**
     * Apply a posterize effect, using the ELBG algorithm.
     */
    public data class ELBG() : VideoFilter("elbg")

    /**
     * Measure video frames entropy.
     */
    public data class Entropy(val mode: Mode = Mode.NORMAL) : VideoFilter("entropy") {
        public enum class Mode {
            NORMAL,
            DIFF
        }
    }

    /**
     * Scale the input using EPX algorithm.
     */
    public data class Epx(val n: Int = 3) : VideoFilter("epx")

    /**
     * Adjust brightness, contrast, gamma, and saturation.
     */
    public data class Eq() : VideoFilter("eq")

    /**
     * Apply erosion effect.
     */
    public data class Erosion() : VideoFilter("erosion")

    /**
     * Apply erosion effect
     */
    public data class ErosionOpencl() : VideoFilter("erosion_opencl")

    /**
     * Apply Edge Slope Tracing deinterlace.
     */
    public data class Estdif() : VideoFilter("estdif")

    /**
     * Adjust exposure of the video stream.
     */
    public data class Exposure(
        val exposure: Float = 0f,
        val black: Float = 0f
    ) : VideoFilter("exposure")

    public data class ExtractPlanes(val planes: List<Component>) : VideoFilter("extractplanes")
    /**
     * Fade in/out input video.
     */
    public data class Fade() : VideoFilter("fade")

    public data class Feedback(
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int,
    ) : VideoFilter("feedback")

    /**
     * Denoise frames using 3D FFT.
     */
    public data class Fftdnoiz() : VideoFilter("fftdnoiz")

    /**
     * Apply arbitrary expressions to pixels in frequency domain.
     */
    public data class Fftfilt() : VideoFilter("fftfilt")

    /**
     * Extract a field from the input video.
     */
    public data class Field() : VideoFilter("field")

    /**
     * Field matching using hints.
     */
    public data class FieldHint() : VideoFilter("fieldhint")

    /**
     * Field matching for inverse telecine.
     */
    public data class FieldMatch() : VideoFilter("fieldmatch")

    /**
     * Set the field order.
     */
    public data class FieldOrder() : VideoFilter("fieldorder")

    /**
     * Fill borders of the input video.
     */
    public data class FillBorders() : VideoFilter("fillborders")

    /**
     * Find a user specified object.
     */
    public data class FindRect() : VideoFilter("find_rect")

    /**
     * Fill area with same color with another color.
     */
    public data class FloodFill() : VideoFilter("floodfill")

    /**
     * Convert the input video to one of the specified pixel formats.
     */
    public data class Format(
        val pixelFormats: List<PixelFormat>,
    ) : VideoFilter("format")

    /**
     * Force constant framerate.
     */
    public data class Fps() : VideoFilter("fps")

    /**
     * Generate a frame packed stereoscopic video.
     */
    public data class FramePack(val format: Format = Format.SBS) : VideoFilter("framepack") {
        public enum class Format {
            SBS,
            TAB,
            LINES,
            COLUMNS,
            FRAME_SEQ
        }
    }

    /**
     * Upsamples or downsamples progressive source between specified frame rates.
     */
    public data class Framerate() : VideoFilter("framerate")

    /**
     * Select one frame every N frames.
     */
    public data class FrameStep(val step: Int = 0) : VideoFilter("framestep")

    /**
     * Detects frozen video input.
     */
    public data class FreezeDetect() : VideoFilter("freezedetect")

    /**
     * Freeze video frames.
     */
    public data class FreezeFrames() : VideoFilter("freezeframes")

    /**
     * Apply a frei0r effect.
     */
    public data class Frei0r() : VideoFilter("frei0r")

    /**
     * Apply Fast Simple Post-processing filter.
     */
    public data class Fspp() : VideoFilter("fspp")

    /**
     * Synchronize video frames from external source.
     */
    public data class Fsync(val filename: String) : VideoFilter("fsync")

    /**
     * Apply Gaussian Blur filter.
     */
    public data class Gblur() : VideoFilter("gblur")

    /**
     * Apply generic equation to each pixel.
     */
    public data class Geq() : VideoFilter("geq")

    /**
     * Debands video quickly using gradients.
     */
    public data class GradFun() : VideoFilter("gradfun")

    /**
     * Show various filtergraph stats.
     */
    public data class GraphMonitor() : VideoFilter("graphmonitor")

    /**
     * Adjust white balance using LAB gray world algorithm
     */
    public data object GrayWorld : VideoFilter("grayworld")

    /**
     * Estimates scene illumination by grey edge assumption.
     */
    public data class GreyEdge() : VideoFilter("greyedge")

    /**
     * Apply Guided filter.
     */
    public data class Guided() : VideoFilter("guided")

    /**
     * Adjust colors using a Hald CLUT.
     */
    public data class Haldclut() : VideoFilter("haldclut")

    /**
     * Horizontally flip the input video.
     */
    public data object HFlip : VideoFilter("hflip")

    /**
     * Apply global color histogram equalization.
     */
    public data class HistEq() : VideoFilter("histeq")

    /**
     * Compute and draw a histogram.
     */
    public data class Histogram() : VideoFilter("histogram")

    /**
     * Apply a High Quality 3D Denoiser.
     */
    public data class HQDN3D() : VideoFilter("hqdn3d")

    /**
     * Scale the input by 2, 3 or 4 using the hq*x magnification algorithm.
     */
    public data class Hqx(val scale: Int = 3) : VideoFilter("hqx")

    /**
     * Stack video inputs horizontally.
     */
    public data class HStack(val inputs: Int, val shortest: Boolean = true) : VideoFilter("hstack")

    /**
     * Turns a certain HSV range into gray.
     */
    public data class HSVHold() : VideoFilter("hsvhold")

    /**
     * Turns a certain HSV range into transparency. Operates on YUV colors.
     */
    public data class HSVKey() : VideoFilter("hsvkey")

    /**
     * Adjust the hue and saturation of the input video.
     */
    public data class Hue() : VideoFilter("hue")

    /**
     * Apply hue-saturation-intensity adjustments.
     */
    public data class HueSaturation() : VideoFilter("huesaturation")

    /**
     * Download a hardware frame to a normal frame
     */
    public data class Hwdownload() : VideoFilter("hwdownload")

    /**
     * Map hardware frames
     */
    public data class Hwmap() : VideoFilter("hwmap")

    /**
     * Upload a normal frame to a hardware frame
     */
    public data class Hwupload() : VideoFilter("hwupload")

    /**
     * Upload a system memory frame to a CUDA device.
     */
    public data class HwuploadCuda() : VideoFilter("hwupload_cuda")

    /**
     * Grow first stream into second stream by connecting components.
     */
    public data class Hysteresis() : VideoFilter("hysteresis")

    /**
     * Calculate the Identity between two video streams.
     */
    public data class Identity() : VideoFilter("identity")

    /**
     * Interlace detect Filter.
     */
    public data class Idet() : VideoFilter("idet")

    /**
     * Deinterleave or interleave fields.
     */
    public data class Il() : VideoFilter("il")

    /**
     * Apply inflate effect.
     */
    public data class Inflate() : VideoFilter("inflate")

    /**
     * Convert progressive video into interlaced.
     */
    public data class Interlace() : VideoFilter("interlace")

    /**
     * Temporally interleave video inputs.
     */
    public data class Interleave() : VideoFilter("interleave")

    /**
     * Apply kernel deinterlacing to the input.
     */
    public data class KernDeint() : VideoFilter("kerndeint")

    /**
     * Apply kirsch operator.
     */
    public data class Kirsch() : VideoFilter("kirsch")

    /**
     * Slowly update darker pixels.
     */
    public data class Lagfun() : VideoFilter("lagfun")

    /**
     * Report video filtering latency.
     */
    public data class Latency() : VideoFilter("latency")

    /**
     * Rectify the image by correcting for lens distortion.
     */
    public data class Lenscorrection() : VideoFilter("lenscorrection")

    /**
     * Apply various GPU filters from libplacebo
     */
    public data class LibPlacebo() : VideoFilter("libplacebo")

    /**
     * Calculate the VMAF between two video streams.
     */
    public data class LibVMAF() : VideoFilter("libvmaf")

    /**
     * Apply filtering with limiting difference.
     */
    public data class LimitDiff() : VideoFilter("limitdiff")

    /**
     * Limit pixels components to the specified range.
     */
    public data class Limiter() : VideoFilter("limiter")

    /**
     * Loop video frames.
     */
    public data class Loop(
        val loop: Int = 0,
        val size: Int = 0,
        val start: Int = 0,
        val time: Int = 0
    ) : VideoFilter("loop")

    /**
     * Turns a certain luma into transparency.
     */
    public data class LumaKey(
        val threshold: Int = 0,
        val tolerance: Float = 0.01f,
        val softness: Int = 0
    ) : VideoFilter("lumakey")

    /**
     * Compute and apply a lookup table to the RGB/YUV input video.
     */
    public data class Lut() : VideoFilter("lut")

    /**
     * Adjust colors using a 1D LUT.
     */
    public data class Lut1d() : VideoFilter("lut1d")

    /**
     * Compute and apply a lookup table from two video inputs.
     */
    public data class Lut2() : VideoFilter("lut2")

    /**
     * Adjust colors using a 3D LUT.
     */
    public data class Lut3d() : VideoFilter("lut3d")

    /**
     * Compute and apply a lookup table to the RGB input video.
     */
    public data class Lutrgb() : VideoFilter("lutrgb")

    /**
     * Compute and apply a lookup table to the YUV input video.
     */
    public data class Lutyuv() : VideoFilter("lutyuv")

    /**
     * Clamp first stream with second stream and third stream.
     */
    public data class MaskedClamp() : VideoFilter("maskedclamp")

    /**
     * Apply filtering with maximum difference of two streams.
     */
    public data class MaskedMax() : VideoFilter("maskedmax")

    /**
     * Merge first stream with second stream using third stream as mask.
     */
    public data class MaskedMerge() : VideoFilter("maskedmerge")

    /**
     * Apply filtering with minimum difference of two streams.
     */
    public data class MaskedMin() : VideoFilter("maskedmin")

    /**
     * Pick pixels comparing absolute difference of two streams with threshold.
     */
    public data class MaskedThreshold() : VideoFilter("maskedthreshold")

    /**
     * Create Mask.
     */
    public data class MaskFun() : VideoFilter("maskfun")

    /**
     * Apply motion compensating deinterlacing.
     */
    public data class MCDEInt() : VideoFilter("mcdeint")

    /**
     * Apply Median filter.
     */
    public data class Median() : VideoFilter("median")

    /**
     * Merge planes.
     */
    public data class MergePlanes() : VideoFilter("mergeplanes")

    /**
     * Generate motion vectors.
     */
    public data class MEstimate(
        val method: Method = Method.ESA,
        val mbSize: Int = 16,
        val search: Int = 7,
    ) : VideoFilter("mestimate") {
        public enum class Method {
            ESA,
            TSS,
            TDLS,
            NTSS,
            FSS,
            DS,
            HEXBS,
            EPZS,
            UMH
        }
    }

    /**
     * Manipulate video frame metadata.
     */
    public data class Metadata() : VideoFilter("metadata")

    /**
     * Apply Midway Equalization.
     */
    public data class MidEqualizer() : VideoFilter("midequalizer")

    /**
     * Frame rate conversion using Motion Interpolation.
     */
    public data class MInterpolate() : VideoFilter("minterpolate")

    /**
     * Mix video inputs.
     */
    public data class Mix() : VideoFilter("mix")

    /**
     * Convert video to gray using custom color filter.
     */
    public data class Monochrome(
        val cb: Int = 0,
        val cr: Int = 0,
        val size: Int = 1,
        val high: Int = 0
    ) : VideoFilter("monochrome")

    /**
     * Apply Morphological filter.
     */
    public data class Morpho(val mode: Mode = Mode.ERODE) : VideoFilter("morpho") {
        public enum class Mode {
            ERODE,
            DILATE,
            OPEN,
            CLOSE,
            GRADIENT,
            TOPHAT,
            BLACKHAT
        }
    }

    /**
     * Remove near-duplicate frames.
     */
    public data class Mpdecimate() : VideoFilter("mpdecimate")

    /**
     * Calculate the MSAD between two video streams.
     */
    public data class MSAD() : VideoFilter("msad")

    /**
     * Multiply first video stream with second video stream.
     */
    public data class Multiply(
        val scale: Int = 1,
        val offset: Float = 0.5f
    ) : VideoFilter("multiply")

    /**
     * Negate input video.
     */
    public data class Negate(val components: List<Component>) : VideoFilter("negate")

    /**
     * Non-local means denoiser.
     */
    public data class NLMeans() : VideoFilter("nlmeans")

    /**
     * Non-local means denoiser through OpenCL
     */
    public data class NLMeansOpencl() : VideoFilter("nlmeans_opencl")

    /**
     * Apply neural network edge directed interpolation intra-only deinterlacer.
     */
    public data class NNEDI() : VideoFilter("nnedi")

    /**
     * Force libavfilter not to use any of the specified pixel formats for the input to the next filter.
     */
    public data class NoFormat(val pixelFormats: List<PixelFormat>) : VideoFilter("noformat")

    /**
     * Add noise.
     */
    public data class Noise() : VideoFilter("noise")

    /**
     * Normalize RGB video.
     */
    public data class Normalize() : VideoFilter("normalize")

    /**
     * Pass the source unchanged to the output.
     */
    public data object Null : VideoFilter("null")

    /**
     * 2D Video Oscilloscope.
     */
    public data class Oscilloscope() : VideoFilter("oscilloscope")

    /**
     * Overlay a video source on top of the input.
     */
    public data class Overlay(
        val x: Int = 0,
        val y: Int = 0,
        val eofAction: EOFAction,
        val eval: Eval,
        val shortest: Boolean
    ) : VideoFilter("overlay") {
        public enum class Eval {
            INIT,
            FRAME
        }
    }

    /**
     * Overlay one video on top of another
     */
    public data class OverlayOpencl() : VideoFilter("overlay_opencl")

    /**
     * Quick Sync Video overlay.
     */
    public data class OverlayQsv() : VideoFilter("overlay_qsv")

    /**
     * Overlay one video on top of another
     */
    public data class OverlayVaapi() : VideoFilter("overlay_vaapi")

    /**
     * Overlay one video on top of another using CUDA
     */
    public data class OverlayCuda() : VideoFilter("overlay_cuda")

    /**
     * Denoise using wavelets.
     */
    public data class Owdenoise() : VideoFilter("owdenoise")

    /**
     * Pad the input video.
     */
    public data class Pad() : VideoFilter("pad")

    /**
     * Pad the input video.
     */
    public data class PadOpencl() : VideoFilter("pad_opencl")

    /**
     * Find the optimal palette for a given stream.
     */
    public data class Palettegen() : VideoFilter("palettegen")

    /**
     * Use a palette to downsample an input video stream.
     */
    public data class Paletteuse() : VideoFilter("paletteuse")

    /**
     * Set permissions for the output video frame.
     */
    public data class Perms() : VideoFilter("perms")

    /**
     * Correct the perspective of video.
     */
    public data class Perspective() : VideoFilter("perspective")

    /**
     * Phase shift fields.
     */
    public data class Phase() : VideoFilter("phase")

    /**
     * Filter out photosensitive epilepsy seizure-inducing flashes.
     */
    public data class Photosensitivity() : VideoFilter("photosensitivity")

    /**
     * Test pixel format definitions.
     */
    public data class PixDesctest() : VideoFilter("pixdesctest")

    /**
     * Pixelize video.
     */
    public data class Pixelize(
        val width: Int = 16,
        val height: Int = 16,
        val mode: Mode = Mode.AVG,
        val planes: Int? = null
    ) : VideoFilter("pixelize") {
        public enum class Mode {
            AVG,
            MIN,
            MAX,
        }
    }

    /**
     * Pixel data analysis.
     */
    public data class PixScope() : VideoFilter("pixscope")

    /**
     * Filter video using libpostproc.
     */
    public data class Pp() : VideoFilter("pp")

    /**
     * Apply Postprocessing 7 filter.
     */
    public data class Pp7() : VideoFilter("pp7")

    /**
     * PreMultiply first stream with first plane of second stream.
     */
    public data class Premultiply(
        val inPlace: Boolean = false
    ) : VideoFilter("premultiply")

    /**
     * Apply prewitt operator.
     */
    public data class Prewitt() : VideoFilter("prewitt")

    /**
     * Apply prewitt operator
     */
    public data class PrewittOpencl() : VideoFilter("prewitt_opencl")

    /**
     * ProcAmp (color balance) adjustments for hue, saturation, brightness, contrast
     */
    public data class ProcampVaapi() : VideoFilter("procamp_vaapi")

    /**
     * Filter video using an OpenCL program
     */
    public data class ProgramOpencl() : VideoFilter("program_opencl")

    /**
     * Make pseudocolored video frames.
     */
    public data class PseudoColor(
        val c0: String,
        val c1: String,
        val c2: String,
        val c3: String,
        val index: Int = 0,
        val preset: LUT? = null,
        val opacity: Float = 1f
    ) : VideoFilter("pseudocolor") {
        public enum class LUT {
            MAGMA,
            INFERNO,
            PLASMA,
            VIRIDIS,
            TURBO,
            CIVIDIS,
            RANGE1,
            RANGE2,
            SHADOWS,
            HIGHLIGHTS,
            SOLAR,
            NOMINAL,
            PREFERRED,
            TOTAL,
            SPECTRAL,
            COOL,
            HEAT,
            FIERY,
            BLUES,
            GREEN,
            HELIX
        }
    }

    /**
     * Calculate the PSNR between two video streams.
     */
    public data class Psnr() : VideoFilter("psnr")

    /**
     * Pullup from field sequence to frames.
     */
    public data class Pullup() : VideoFilter("pullup")

    /**
     * Change video quantization parameters.
     */
    public data class Qp(val qp: Expression) : VideoFilter("qp")

    /**
     * Return random frames.
     */
    public data class Random(
        val frames: Int = 30,
        val seed: Int = -1
    ) : VideoFilter("random")

    /**
     * Read EIA-608 Closed Caption codes from input video and write them to frame metadata.
     */
    public data class ReadEIA608() : VideoFilter("readeia608")

    /**
     * Read vertical interval timecode and write it to frame metadata.
     */
    public data class ReadVITC() : VideoFilter("readvitc")

    /**
     * Slow down filtering to match realtime.
     */
    public data class Realtime() : VideoFilter("realtime")

    /**
     * Remap pixels.
     */
    public data class Remap(val format: Format = Format.COLOR, val fill: Long) : VideoFilter("remap") {
        public enum class Format {
            COLOR,
            GRAY
        }
    }

    /**
     * Remap pixels using OpenCL.
     */
    public data class RemapOpencl() : VideoFilter("remap_opencl")

    /**
     * Remove grain.
     */
    public data class RemoveGrain() : VideoFilter("removegrain")

    /**
     * Remove a TV logo based on a mask image.
     */
    public data class RemoveLogo(val filename: String) : VideoFilter("removelogo")

    /**
     * Hard repeat fields based on MPEG repeat field flag.
     */
    public data object RepeatFields : VideoFilter("repeatfields")

    /**
     * Reverse a clip.
     */
    public data object Reverse : VideoFilter("reverse")

    /**
     * Shift RGBA.
     */
    public data class RGBAShift(
        val rh: Int = 0,
        val rv: Int = 0,
        val gh: Int = 0,
        val gv: Int = 0,
        val bh: Int = 0,
        val bv: Int = 0,
        val ah: Int = 0,
        val av: Int = 0,
        val edge: Edge = Edge.DEFAULT
    ) : VideoFilter("rgbashift") {
        public enum class Edge {
            SMEAR,
            DEFAULT,
            WARP
        }
    }

    /**
     * Apply roberts cross operator.
     */
    public data class Roberts() : VideoFilter("roberts")

    /**
     * Apply roberts operator
     */
    public data class RobertsOpencl() : VideoFilter("roberts_opencl")

    /**
     * Rotate the input image.
     */
    public data class Rotate() : VideoFilter("rotate")

    /**
     * Apply shape adaptive blur.
     */
    public data class Sab() : VideoFilter("sab")

    /**
     * Scale the input video size and/or convert the image format.
     */
    public data class Scale() : VideoFilter("scale")

    /**
     * GPU accelerated video resizer
     */
    public data class ScaleCuda() : VideoFilter("scale_cuda")

    /**
     * Quick Sync Video "scaling and format conversion"
     */
    public data class ScaleQsv() : VideoFilter("scale_qsv")

    /**
     * Scale to/from VAAPI surfaces.
     */
    public data class ScaleVaapi() : VideoFilter("scale_vaapi")
    ..C scale2ref         VV->VV     Scale the input video size and/or convert the image format to the given reference.
    /**
     * Detect video scene change
     */
    public data class Scdet() : VideoFilter("scdet")

    /**
     * Apply scharr operator.
     */
    public data class Scharr() : VideoFilter("scharr")

    /**
     * Scroll input video.
     */
    public data class Scroll() : VideoFilter("scroll")
    /**
     * Apply CMYK adjustments to specific color ranges.
     */
    public data class Selectivecolor() : VideoFilter("selectivecolor")

    /**
     * Send commands to filters.
     */
    public data class SendCmd() : VideoFilter("sendcmd")

    /**
     * Split input video frames into fields.
     */
    public data class SeparateFields() : VideoFilter("separatefields")

    /**
     * Set the frame display aspect ratio.
     */
    public data class SetDar() : VideoFilter("setdar")

    /**
     * Force field for the output video frame.
     */
    public data class SetField() : VideoFilter("setfield")

    /**
     * Force field, or color property for the output video frame.
     */
    public data class SetParams() : VideoFilter("setparams") {
        public enum class Field {
            AUTO,
            BFF,
            TFF,
            PROG
        }

        public enum class Range {
            AUTO,
            UNSPECIFIED,
            LIMITED,
            FULL
        }
    }

    /**
     * Set PTS for the output video frame.
     */
    public data class SetPTS() : VideoFilter("setpts")

    /**
     * Force color range for the output video frame.
     */
    public data class Setrange() : VideoFilter("setrange")

    /**
     * Set the pixel sample aspect ratio.
     */
    public data class Setsar() : VideoFilter("setsar")

    /**
     * Set timebase for the video output link.
     */
    public data class Settb() : VideoFilter("settb")

    /**
     * VAAPI VPP for sharpness
     */
    public data class SharpnessVaapi() : VideoFilter("sharpness_vaapi")

    /**
     * Shear transform the input image.
     */
    public data class Shear() : VideoFilter("shear")

    /**
     * Show textual information for each video frame.
     */
    public data class Showinfo() : VideoFilter("showinfo")

    /**
     * Display frame palette.
     */
    public data class Showpalette() : VideoFilter("showpalette")

    /**
     * Shuffle video frames.
     */
    public data class Shuffleframes() : VideoFilter("shuffleframes")

    /**
     * Shuffle video pixels.
     */
    public data class Shufflepixels() : VideoFilter("shufflepixels")

    /**
     * Shuffle video planes.
     */
    public data class Shuffleplanes() : VideoFilter("shuffleplanes")

    /**
     * Manipulate video frame side data.
     */
    public data class Sidedata() : VideoFilter("sidedata")

    /**
     * Generate statistics from video analysis.
     */
    public data class Signalstats() : VideoFilter("signalstats")

    /**
     * Calculate the MPEG-7 video signature
     */
    public data class Signature() : VideoFilter("signature")

    /**
     * Calculate spatial information (SI) and temporal information (TI).
     */
    public data class Siti() : VideoFilter("siti")

    /**
     * Blur the input video without impacting the outlines.
     */
    public data class Smartblur() : VideoFilter("smartblur")

    /**
     * Apply sobel operator.
     */
    public data class Sobel() : VideoFilter("sobel")

    /**
     * Apply sobel operator
     */
    public data class SobelOpencl() : VideoFilter("sobel_opencl")

    /**
     * Apply a simple post processing filter.
     */
    public data class Spp() : VideoFilter("spp")

    /**
     * Apply DNN-based image super resolution to the input.
     */
    public data class Sr() : VideoFilter("sr")

    /**
     * Calculate the SSIM between two video streams.
     */
    public data class Ssim() : VideoFilter("ssim")

    /**
     * Calculate the SSIM between two 360 video streams.
     */
    public data class Ssim360() : VideoFilter("ssim360")

    /**
     * Convert video stereoscopic 3D view.
     */
    public data class Stereo3d() : VideoFilter("stereo3d")

    /**
     * Render text subtitles onto input video using the libass library.
     */
    public data class Subtitles() : VideoFilter("subtitles")

    /**
     * Scale the input by 2x using the Super2xSaI pixel art algorithm.
     */
    public data class Super2xsai() : VideoFilter("super2xsai")

    /**
     * Swap 2 rectangular objects in video.
     */
    public data class Swaprect() : VideoFilter("swaprect")

    /**
     * Swap U and V components.
     */
    public data class Swapuv() : VideoFilter("swapuv")

    /**
     * Blend successive frames.
     */
    public data class Tblend() : VideoFilter("tblend")

    /**
     * Apply a telecine pattern.
     */
    public data class Telecine() : VideoFilter("telecine")

    /**
     * Compute and draw a temporal histogram.
     */
    public data class Thistogram() : VideoFilter("thistogram")

    /**
     * Threshold first video stream using other video streams.
     */
    public data class Threshold() : VideoFilter("threshold")

    /**
     * Select the most representative frame in a given sequence of consecutive frames.
     */
    public data class Thumbnail() : VideoFilter("thumbnail")

    /**
     * Select the most representative frame in a given sequence of consecutive frames.
     */
    public data class ThumbnailCuda() : VideoFilter("thumbnail_cuda")

    /**
     * Tile several successive frames together.
     */
    public data class Tile() : VideoFilter("tile")

    /**
     * Generate a tilt-and-shift'd video.
     */
    public data class Tiltandshift() : VideoFilter("tiltandshift")

    /**
     * Perform temporal field interlacing.
     */
    public data class Tinterlace() : VideoFilter("tinterlace")

    /**
     * Compute and apply a lookup table from two successive frames.
     */
    public data class Tlut2() : VideoFilter("tlut2")

    /**
     * Pick median pixels from successive frames.
     */
    public data class Tmedian() : VideoFilter("tmedian")

    /**
     * Apply Temporal Midway Equalization.
     */
    public data class Tmidequalizer() : VideoFilter("tmidequalizer")

    /**
     * Mix successive video frames.
     */
    public data class Tmix() : VideoFilter("tmix")

    /**
     * Conversion to/from different dynamic ranges.
     */
    public data class Tonemap() : VideoFilter("tonemap")

    /**
     * Perform HDR to SDR conversion with tonemapping.
     */
    public data class TonemapOpencl() : VideoFilter("tonemap_opencl")

    /**
     * VAAPI VPP for tone-mapping
     */
    public data class TonemapVaapi() : VideoFilter("tonemap_vaapi")

    /**
     * Temporarily pad video frames.
     */
    public data class Tpad() : VideoFilter("tpad")

    /**
     * Transpose input video.
     */
    public data class Transpose() : VideoFilter("transpose")

    /**
     * Transpose input video
     */
    public data class TransposeOpencl() : VideoFilter("transpose_opencl")

    /**
     * VAAPI VPP for transpose
     */
    public data class TransposeVaapi() : VideoFilter("transpose_vaapi")

    /**
     * Pick one continuous section from the input, drop the rest.
     */
    public data class Trim() : VideoFilter("trim")

    /**
     * UnPreMultiply first stream with first plane of second stream.
     */
    public data class Unpremultiply() : VideoFilter("unpremultiply")

    /**
     * Sharpen or blur the input video.
     */
    public data class Unsharp() : VideoFilter("unsharp")

    /**
     * Apply unsharp mask to input video
     */
    public data class UnsharpOpencl() : VideoFilter("unsharp_opencl")

    /**
     * Untile a frame into a sequence of frames.
     */
    public data class Untile() : VideoFilter("untile")

    /**
     * Apply Ultra Simple / Slow Post-processing filter.
     */
    public data class Uspp() : VideoFilter("uspp")

    /**
     * Convert 360 projection of video.
     */
    public data class V360() : VideoFilter("v360")

    /**
     * Apply a Wavelet based Denoiser.
     */
    public data class Vaguedenoiser() : VideoFilter("vaguedenoiser")

    /**
     * Apply Variable Blur filter.
     */
    public data class Varblur() : VideoFilter("varblur")

    /**
     * Video vectorscope.
     */
    public data class Vectorscope() : VideoFilter("vectorscope")

    /**
     * Flip the input video vertically.
     */
    public data class Vflip() : VideoFilter("vflip")

    /**
     * Variable frame rate detect filter.
     */
    public data class Vfrdet() : VideoFilter("vfrdet")

    /**
     * Boost or alter saturation.
     */
    public data class Vibrance() : VideoFilter("vibrance")

    /**
     * Extract relative transformations, pass 1 of 2 for stabilization (see vidstabtransform for pass 2).
     */
    public data class Vidstabdetect() : VideoFilter("vidstabdetect")

    /**
     * Transform the frames, pass 2 of 2 for stabilization (see vidstabdetect for pass 1).
     */
    public data class Vidstabtransform() : VideoFilter("vidstabtransform")

    /**
     * Calculate the VIF between two video streams.
     */
    public data class Vif() : VideoFilter("vif")

    /**
     * Make or reverse a vignette effect.
     */
    public data class Vignette() : VideoFilter("vignette")

    /**
     * Calculate the VMAF Motion score.
     */
    public data class Vmafmotion() : VideoFilter("vmafmotion")

    /**
     * Quick Sync Video "VPP"
     */
    public data class VppQsv() : VideoFilter("vpp_qsv")

    /**
     * Stack video inputs vertically.
     */
    public data class Vstack() : VideoFilter("vstack")

    /**
     * Apply Martin Weston three field deinterlace.
     */
    public data class W3fdif() : VideoFilter("w3fdif")

    /**
     * Video waveform monitor.
     */
    public data class Waveform() : VideoFilter("waveform")

    /**
     * Weave input video fields into frames.
     */
    public data class Weave() : VideoFilter("weave")

    /**
     * Scale the input using xBR algorithm.
     */
    public data class Xbr() : VideoFilter("xbr")

    /**
     * Cross-correlate first video stream with second video stream.
     */
    public data class Xcorrelate() : VideoFilter("xcorrelate")

    /**
     * Cross fade one video with another video.
     */
    public data class Xfade() : VideoFilter("xfade")

    /**
     * Cross fade one video with another video.
     */
    public data class XfadeOpencl() : VideoFilter("xfade_opencl")

    /**
     * Pick median pixels from several video inputs.
     */
    public data class Xmedian() : VideoFilter("xmedian")

    /**
     * Stack video inputs into custom layout.
     */
    public data class Xstack() : VideoFilter("xstack")

    /**
     * Deinterlace the input image.
     */
    public data class Yadif() : VideoFilter("yadif")

    /**
     * Deinterlace CUDA frames
     */
    public data class YadifCuda() : VideoFilter("yadif_cuda")

    /**
     * Yet another edge preserving blur filter.
     */
    public data class Yaepblur() : VideoFilter("yaepblur")

    /**
     * Apply Zoom & Pan effect.
     */
    public data class Zoompan() : VideoFilter("zoompan")

    /**
     * Apply resizing, colorspace and bit depth conversion.
     */
    public data class Zscale() : VideoFilter("zscale")

    /**
     * "VA-API" hstack
     */
    public data class HstackVaapi() : VideoFilter("hstack_vaapi")

    /**
     * "VA-API" vstack
     */
    public data class VstackVaapi() : VideoFilter("vstack_vaapi")

    /**
     * "VA-API" xstack
     */
    public data class XstackVaapi() : VideoFilter("xstack_vaapi")

    /**
     * "Quick Sync Video" hstack
     */
    public data class HstackQSV() : VideoFilter("hstack_qsv")

    /**
     * "Quick Sync Video" vstack
     */
    public data class VstackQSV() : VideoFilter("vstack_qsv")

    /**
     * "Quick Sync Video" xstack
     */
    public data class XstackQSV() : VideoFilter("xstack_qsv")

    /**
     * Generate all RGB colors.
     */
    public data class AllRGB() : VideoFilter("allrgb")

    /**
     * Generate all yuv colors.
     */
    public data class Allyuv() : VideoFilter("allyuv")

    /**
     * Create pattern generated by an elementary cellular automaton.
     */
    public data class CellAuto() : VideoFilter("cellauto")

    /**
     * Provide an uniformly colored input.
     */
    public data class Color() : VideoFilter("color")

    /**
     * Generate color checker chart.
     */
    public data class Colorchart() : VideoFilter("colorchart")

    /**
     * Generate colors spectrum.
     */
    public data class Colorspectrum() : VideoFilter("colorspectrum")

    /**
     * Generate a frei0r source.
     */
    public data class Frei0rSrc() : VideoFilter("frei0r_src")

    /**
     * Draw a gradients.
     */
    public data class Gradients() : VideoFilter("gradients")

    /**
     * Provide an identity Hald CLUT.
     */
    public data class Haldclutsrc() : VideoFilter("haldclutsrc")

    /**
     * Create life.
     */
    public data class Life() : VideoFilter("life")

    /**
     * Render a Mandelbrot fractal.
     */
    public data class Mandelbrot() : VideoFilter("mandelbrot")

    /**
     * Generate various test pattern.
     */
    public data class Mptestsrc() : VideoFilter("mptestsrc")

    /**
     * Null video source, return unprocessed video frames.
     */
    public data class Nullsrc() : VideoFilter("nullsrc")

    /**
     * Generate video using an OpenCL program
     */
    public data class Openclsrc() : VideoFilter("openclsrc")

    /**
     * Generate PAL 75% color bars.
     */
    public data class Pal75bars() : VideoFilter("pal75bars")

    /**
     * Generate PAL 100% color bars.
     */
    public data class Pal100bars() : VideoFilter("pal100bars")

    /**
     * Generate RGB test pattern.
     */
    public data class Rgbtestsrc() : VideoFilter("rgbtestsrc")

    /**
     * Render a Sierpinski fractal.
     */
    public data class Sierpinski() : VideoFilter("sierpinski")

    /**
     * Generate SMPTE color bars.
     */
    public data class Smptebars() : VideoFilter("smptebars")

    /**
     * Generate SMPTE HD color bars.
     */
    public data class Smptehdbars() : VideoFilter("smptehdbars")

    /**
     * Generate test pattern.
     */
    public data class Testsrc() : VideoFilter("testsrc")

    /**
     * Generate another test pattern.
     */
    public data class Testsrc2() : VideoFilter("testsrc2")

    /**
     * Generate YUV test pattern.
     */
    public data class Yuvtestsrc() : VideoFilter("yuvtestsrc")

    /**
     * Generate zone-plate.
     */
    public data class Zoneplate() : VideoFilter("zoneplate")
    ... nullsink          V->|       Do absolutely nothing with the input video.
    /**
     * Convert input audio to 3d scope video output.
     */
    public data class A3dscope() : VideoFilter("a3dscope")

    /**
     * Convert input audio to audio bit scope video output.
     */
    public data class Abitscope() : VideoFilter("abitscope")

    /**
     * Draw a graph using input audio metadata.
     */
    public data class Adrawgraph() : VideoFilter("adrawgraph")

    /**
     * Show various filtergraph stats.
     */
    public data class Agraphmonitor() : VideoFilter("agraphmonitor")

    /**
     * Convert input audio to histogram video output.
     */
    public data class Ahistogram() : VideoFilter("ahistogram")
    // ... aphasemeter       A->N       Convert input audio to phase meter video output.
    /**
     * Convert input audio to vectorscope video output.
     */
    public data class Avectorscope() : VideoFilter("avectorscope")
    // ..C concat            N->N       Concatenate audio and video streams.
    /**
     * Convert input audio to a CQT (Constant/Clamped Q Transform) spectrum video output.
     */
    public data class Showcqt() : VideoFilter("showcqt")

    /**
     * Convert input audio to a CWT (Continuous Wavelet Transform) spectrum video output.
     */
    public data class ShowCWT() : VideoFilter("showcwt")

    /**
     * Convert input audio to a frequencies video output.
     */
    public data class ShowFreqs() : VideoFilter("showfreqs")

    /**
     * Convert input audio to a spatial video output.
     */
    public data class ShowSpatial() : VideoFilter("showspatial")

    /**
     * Convert input audio to a spectrum video output.
     */
    public data class ShowSpectrum() : VideoFilter("showspectrum")

    /**
     * Convert input audio to a spectrum video output single picture.
     */
    public data class ShowSpectrumPic() : VideoFilter("showspectrumpic")

    /**
     * Convert input audio volume to video output.
     */
    public data class ShowVolume() : VideoFilter("showvolume")

    /**
     * Convert input audio to a video output.
     */
    public data class ShowWaves() : VideoFilter("showwaves")

    /**
     * Convert input audio to a video output single picture.
     */
    public data class ShowWavespic() : VideoFilter("showwavespic")
}

... spectrumsynth     VV->A      Convert input spectrum videos to audio output.
..C avsynctest        |->AV      Generate an Audio Video Sync Test.
..C amovie            |->N       Read audio from a movie source.
..C movie             |->N       Read from a movie source.
... abuffer           |->A       Buffer audio frames, and make them accessible to the filterchain.
/**
 * Buffer video frames, and make them accessible to the filterchain.
 */
public data class buffer() : VideoFilter("buffer")
... abuffersink       A->|       Buffer audio frames, and make them available to the end of the filter graph.
... buffersink        V->|       Buffer video frames, and make them available to the end of the filter graph.