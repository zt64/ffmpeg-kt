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
        val height: Int
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
        val planes: Int? = null
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
    public data object ATADenoise : VideoFilter("atadenoise")

    /**
     * Apply Average Blur filter.
     */
    public data class AvgBlur(val width: Int, val height: Int) : VideoFilter("avgblur")

    /**
     * Apply average blur filter
     */
    public data object AvgBlurOpencl : VideoFilter("avgblur_opencl")

    /**
     * Turns a static background into transparency.
     */
    public data object BackgroundKey : VideoFilter("backgroundkey")

    /**
     * Compute bounding box for each frame.
     */
    public data class BBox(val minVal: Int = 16) : VideoFilter("bbox")

    /**
     * Benchmark part of a filtergraph.
     */
    public data object Bench : VideoFilter("bench")

    /**
     * Apply Bilateral filter.
     */
    public data object Bilateral : VideoFilter("bilateral")

    /**
     * GPU accelerated bilateral filter
     */
    public data object BilateralCuda : VideoFilter("bilateral_cuda")

    /**
     * Measure bit plane noise.
     */
    public data object BitPlaneNoise : VideoFilter("bitplanenoise")

    /**
     * Detect video intervals that are (almost) black.
     */
    public data object BlackDetect : VideoFilter("blackdetect")

    /**
     * Detect frames that are (almost) black.
     */
    public data object BlackFrame : VideoFilter("blackframe")

    /**
     * Blend two video frames into each other.
     */
    public data object Blend : VideoFilter("blend")

    /**
     * Blockdetect filter.
     */
    public data object Blockdetect : VideoFilter("blockdetect")

    /**
     * Blurdetect filter.
     */
    public data object Blurdetect : VideoFilter("blurdetect")

    /**
     * Block-Matching 3D denoiser.
     */
    public data object BM3D : VideoFilter("bm3d")

    /**
     * Blur the input.
     */
    public data object BoxBlur : VideoFilter("boxblur")

    /**
     * Apply boxblur filter to input video
     */
    public data object BoxBlurOpencl : VideoFilter("boxblur_opencl")

    /**
     * Deinterlace the input image.
     */
    public data object Bwdif : VideoFilter("bwdif")

    /**
     * Deinterlace CUDA frames
     */
    public data object BwdifCuda : VideoFilter("bwdif_cuda")

    /**
     * Contrast Adaptive Sharpen.
     */
    public data class CAS(val strength: Int = 0, val planes: Int? = null) : VideoFilter("cas")

    /**
     * Repack CEA-708 closed caption metadata
     */
    public data object CCREPack : VideoFilter("ccrepack")

    /**
     * Turns a certain color range into gray.
     */
    public data class ChromaHold(
        val color: Long,
        val similarity: Float = 0.01f,
        val blend: Float = 0.0f,
        val yuv: Boolean = false
    ) : VideoFilter("chromahold")

    /**
     * Turns a certain color into transparency. Operates on YUV colors.
     */
    public data object Chromakey : VideoFilter("chromakey")

    /**
     * GPU accelerated chromakey filter
     */
    public data object ChromakeyCuda : VideoFilter("chromakey_cuda")

    /**
     * Reduce chrominance noise.
     */
    public data object ChromaNR : VideoFilter("chromanr")

    /**
     * Shift chroma.
     */
    public data object ChromaShift : VideoFilter("chromashift")

    /**
     * Video CIE scope.
     */
    public data object CIEScope : VideoFilter("ciescope")

    /**
     * Visualize information about some codecs.
     */
    public data object CodecView : VideoFilter("codecview")

    /**
     * Adjust the color balance.
     */
    public data object ColorBalance : VideoFilter("colorbalance")

    /**
     * Adjust colors by mixing color channels.
     */
    public data object ColorChannelMixer : VideoFilter("colorchannelmixer")

    /**
     * Adjust color contrast between RGB components.
     */
    public data object ColorContrast : VideoFilter("colorcontrast")

    /**
     * Adjust color white balance selectively for blacks and whites.
     */
    public data object Colorcorrect : VideoFilter("colorcorrect")

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
    public data object Colorkey : VideoFilter("colorkey")

    /**
     * Turns a certain color into transparency. Operates on RGB colors.
     */
    public data object ColorkeyOpencl : VideoFilter("colorkey_opencl")

    /**
     * Turns a certain color range into gray. Operates on RGB colors.
     */
    public data object Colorhold : VideoFilter("colorhold")

    /**
     * Adjust the color levels.
     */
    public data object ColorLevels : VideoFilter("colorlevels")

    /**
     * Apply custom Color Maps to video stream.
     */
    public data object ColorMap : VideoFilter("colormap")

    /**
     * Convert color matrix.
     */
    public data object Colormatrix : VideoFilter("colormatrix")

    /**
     * Convert between colorspaces.
     */
    public data object Colorspace : VideoFilter("colorspace")

    /**
     * CUDA accelerated video color converter
     */
    public data object ColorspaceCuda : VideoFilter("colorspace_cuda")

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
    public data object Convolution : VideoFilter("convolution")

    /**
     * Apply convolution mask to input video
     */
    public data object ConvolutionOpencl : VideoFilter("convolution_opencl")

    /**
     * Convolve first video stream with second video stream.
     */
    public data class Convolve(val planes: Int, val impulse: String) : VideoFilter("convolve")

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
    public data object CoverRect : VideoFilter("cover_rect")

    /**
     * Crop the input video.
     */
    public data object Crop : VideoFilter("crop")

    /**
     * Auto-detect crop size.
     */
    public data object Cropdetect : VideoFilter("cropdetect")

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
    public data class Curves(val preset: Preset = Preset.NONE) : VideoFilter("curves") {
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
    public data object DataScope : VideoFilter("datascope")

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
    public data object Dctdnoiz : VideoFilter("dctdnoiz")

    /**
     * Debands video.
     */
    public data object Deband : VideoFilter("deband")

    /**
     * Deblock video.
     */
    public data object Deblock : VideoFilter("deblock")

    /**
     * Decimate frames (post field matching filter).
     */
    public data object Decimate : VideoFilter("decimate")

    /**
     * Deconvolve first video stream with second video stream.
     */
    public data object Deconvolve : VideoFilter("deconvolve")

    /**
     * Reduce cross-luminance and cross-color.
     */
    public data object Dedot : VideoFilter("dedot")

    /**
     * Apply deflate effect.
     */
    public data object Deflate : VideoFilter("deflate")

    /**
     * Remove temporal frame luminance variations.
     */
    public data object Deflicker : VideoFilter("deflicker")

    /**
     * Quick Sync Video "deinterlacing"
     */
    public data object DeinterlaceQsv : VideoFilter("deinterlace_qsv")

    /**
     * Deinterlacing of VAAPI surfaces
     */
    public data object DeinterlaceVaapi : VideoFilter("deinterlace_vaapi")

    /**
     * Remove judder produced by pullup.
     */
    public data object Dejudder : VideoFilter("dejudder")

    /**
     * Remove logo from input video.
     */
    public data object Delogo : VideoFilter("delogo")

    /**
     * VAAPI VPP for de-noise
     */
    public data object DenoiseVaapi : VideoFilter("denoise_vaapi")

    /**
     * Apply derain filter to the input.
     */
    public data object Derain : VideoFilter("derain")

    /**
     * Stabilize shaky video.
     */
    public data object Deshake : VideoFilter("deshake")

    /**
     * Feature-point based video stabilization filter
     */
    public data object DeshakeOpencl : VideoFilter("deshake_opencl")

    /**
     * Despill video.
     */
    public data object Despill : VideoFilter("despill")

    /**
     * Apply an inverse telecine pattern.
     */
    public data object Detelecine : VideoFilter("detelecine")

    /**
     * Apply dilation effect.
     */
    public data object Dilation : VideoFilter("dilation")

    /**
     * Apply dilation effect
     */
    public data object DilationOpencl : VideoFilter("dilation_opencl")

    /**
     * Displace pixels.
     */
    public data object Displace : VideoFilter("displace")

    /**
     * Apply DNN classify filter to the input.
     */
    public data object DnnClassify : VideoFilter("dnn_classify")

    /**
     * Apply DNN detect filter to the input.
     */
    public data object DnnDetect : VideoFilter("dnn_detect")

    /**
     * Apply DNN processing filter to the input.
     */
    public data object DnnProcessing : VideoFilter("dnn_processing")

    /**
     * Weave input video fields into double number of frames.
     */
    public data object Doubleweave : VideoFilter("doubleweave")

    /**
     * Draw a colored box on the input video.
     */
    public data object Drawbox : VideoFilter("drawbox")

    /**
     * Draw a graph using input video metadata.
     */
    public data object Drawgraph : VideoFilter("drawgraph")

    /**
     * Draw a colored grid on the input video.
     */
    public data object Drawgrid : VideoFilter("drawgrid")

    /**
     * Draw text on top of video frames using libfreetype library.
     */
    public data object Drawtext : VideoFilter("drawtext")

    /**
     * Detect and draw edge.
     */
    public data object Edgedetect : VideoFilter("edgedetect")

    /**
     * Apply a posterize effect, using the ELBG algorithm.
     */
    public data object ELBG : VideoFilter("elbg")

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
    public data object Eq : VideoFilter("eq")

    /**
     * Apply erosion effect.
     */
    public data object Erosion : VideoFilter("erosion")

    /**
     * Apply erosion effect
     */
    public data object ErosionOpencl : VideoFilter("erosion_opencl")

    /**
     * Apply Edge Slope Tracing deinterlace.
     */
    public data object Estdif : VideoFilter("estdif")

    /**
     * Adjust exposure of the video stream.
     */
    public data class Exposure(val exposure: Float = 0f, val black: Float = 0f) : VideoFilter("exposure")

    public data class ExtractPlanes(val planes: List<Component>) : VideoFilter("extractplanes")

    /**
     * Fade in/out input video.
     */
    public data object Fade : VideoFilter("fade")

    public data class Feedback(
        val x: Int,
        val y: Int,
        val width: Int,
        val height: Int
    ) : VideoFilter("feedback")

    /**
     * Denoise frames using 3D FFT.
     */
    public data object Fftdnoiz : VideoFilter("fftdnoiz")

    /**
     * Apply arbitrary expressions to pixels in frequency domain.
     */
    public data object Fftfilt : VideoFilter("fftfilt")

    /**
     * Extract a field from the input video.
     */
    public data object Field : VideoFilter("field")

    /**
     * Field matching using hints.
     */
    public data object FieldHint : VideoFilter("fieldhint")

    /**
     * Field matching for inverse telecine.
     */
    public data object FieldMatch : VideoFilter("fieldmatch")

    /**
     * Set the field order.
     */
    public data object FieldOrder : VideoFilter("fieldorder")

    /**
     * Fill borders of the input video.
     */
    public data object FillBorders : VideoFilter("fillborders")

    /**
     * Find a user specified object.
     */
    public data object FindRect : VideoFilter("find_rect")

    /**
     * Fill area with same color with another color.
     */
    public data object FloodFill : VideoFilter("floodfill")

    /**
     * Convert the input video to one of the specified pixel formats.
     */
    public data class Format(val pixelFormats: List<PixelFormat>) : VideoFilter("format")

    /**
     * Force constant framerate.
     */
    public data object Fps : VideoFilter("fps")

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
    public data object Framerate : VideoFilter("framerate")

    /**
     * Select one frame every N frames.
     */
    public data class FrameStep(val step: Int = 0) : VideoFilter("framestep")

    /**
     * Detects frozen video input.
     */
    public data object FreezeDetect : VideoFilter("freezedetect")

    /**
     * Freeze video frames.
     */
    public data object FreezeFrames : VideoFilter("freezeframes")

    /**
     * Apply a frei0r effect.
     */
    public data object Frei0r : VideoFilter("frei0r")

    /**
     * Apply Fast Simple Post-processing filter.
     */
    public data object Fspp : VideoFilter("fspp")

    /**
     * Synchronize video frames from external source.
     */
    public data class Fsync(val filename: String) : VideoFilter("fsync")

    /**
     * Apply Gaussian Blur filter.
     */
    public data object Gblur : VideoFilter("gblur")

    /**
     * Apply generic equation to each pixel.
     */
    public data object Geq : VideoFilter("geq")

    /**
     * Debands video quickly using gradients.
     */
    public data object GradFun : VideoFilter("gradfun")

    /**
     * Show various filtergraph stats.
     */
    public data object GraphMonitor : VideoFilter("graphmonitor")

    /**
     * Adjust white balance using LAB gray world algorithm
     */
    public data object GrayWorld : VideoFilter("grayworld")

    /**
     * Estimates scene illumination by grey edge assumption.
     */
    public data object GreyEdge : VideoFilter("greyedge")

    /**
     * Apply Guided filter.
     */
    public data object Guided : VideoFilter("guided")

    /**
     * Adjust colors using a Hald CLUT.
     */
    public data object Haldclut : VideoFilter("haldclut")

    /**
     * Horizontally flip the input video.
     */
    public data object HFlip : VideoFilter("hflip")

    /**
     * Apply global color histogram equalization.
     */
    public data object HistEq : VideoFilter("histeq")

    /**
     * Compute and draw a histogram.
     */
    public data object Histogram : VideoFilter("histogram")

    /**
     * Apply a High Quality 3D Denoiser.
     */
    public data object HQDN3D : VideoFilter("hqdn3d")

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
    public data object HSVHold : VideoFilter("hsvhold")

    /**
     * Turns a certain HSV range into transparency. Operates on YUV colors.
     */
    public data object HSVKey : VideoFilter("hsvkey")

    /**
     * Adjust the hue and saturation of the input video.
     */
    public data object Hue : VideoFilter("hue")

    /**
     * Apply hue-saturation-intensity adjustments.
     */
    public data object HueSaturation : VideoFilter("huesaturation")

    /**
     * Download a hardware frame to a normal frame
     */
    public data object Hwdownload : VideoFilter("hwdownload")

    /**
     * Map hardware frames
     */
    public data object Hwmap : VideoFilter("hwmap")

    /**
     * Upload a normal frame to a hardware frame
     */
    public data object Hwupload : VideoFilter("hwupload")

    /**
     * Upload a system memory frame to a CUDA device.
     */
    public data object HwuploadCuda : VideoFilter("hwupload_cuda")

    /**
     * Grow first stream into second stream by connecting components.
     */
    public data object Hysteresis : VideoFilter("hysteresis")

    /**
     * Calculate the Identity between two video streams.
     */
    public data object Identity : VideoFilter("identity")

    /**
     * Interlace detect Filter.
     */
    public data object Idet : VideoFilter("idet")

    /**
     * Deinterleave or interleave fields.
     */
    public data object Il : VideoFilter("il")

    /**
     * Apply inflate effect.
     */
    public data object Inflate : VideoFilter("inflate")

    /**
     * Convert progressive video into interlaced.
     */
    public data object Interlace : VideoFilter("interlace")

    /**
     * Temporally interleave video inputs.
     */
    public data object Interleave : VideoFilter("interleave")

    /**
     * Apply kernel deinterlacing to the input.
     */
    public data object KernDeint : VideoFilter("kerndeint")

    /**
     * Apply kirsch operator.
     */
    public data object Kirsch : VideoFilter("kirsch")

    /**
     * Slowly update darker pixels.
     */
    public data object Lagfun : VideoFilter("lagfun")

    /**
     * Report video filtering latency.
     */
    public data object Latency : VideoFilter("latency")

    /**
     * Rectify the image by correcting for lens distortion.
     */
    public data object Lenscorrection : VideoFilter("lenscorrection")

    /**
     * Apply various GPU filters from libplacebo
     */
    public data object LibPlacebo : VideoFilter("libplacebo")

    /**
     * Calculate the VMAF between two video streams.
     */
    public data object LibVMAF : VideoFilter("libvmaf")

    /**
     * Apply filtering with limiting difference.
     */
    public data object LimitDiff : VideoFilter("limitdiff")

    /**
     * Limit pixels components to the specified range.
     */
    public data object Limiter : VideoFilter("limiter")

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
    public data object Lut : VideoFilter("lut")

    /**
     * Adjust colors using a 1D LUT.
     */
    public data object Lut1d : VideoFilter("lut1d")

    /**
     * Compute and apply a lookup table from two video inputs.
     */
    public data object Lut2 : VideoFilter("lut2")

    /**
     * Adjust colors using a 3D LUT.
     */
    public data object Lut3d : VideoFilter("lut3d")

    /**
     * Compute and apply a lookup table to the RGB input video.
     */
    public data object Lutrgb : VideoFilter("lutrgb")

    /**
     * Compute and apply a lookup table to the YUV input video.
     */
    public data object Lutyuv : VideoFilter("lutyuv")

    /**
     * Clamp first stream with second stream and third stream.
     */
    public data object MaskedClamp : VideoFilter("maskedclamp")

    /**
     * Apply filtering with maximum difference of two streams.
     */
    public data object MaskedMax : VideoFilter("maskedmax")

    /**
     * Merge first stream with second stream using third stream as mask.
     */
    public data object MaskedMerge : VideoFilter("maskedmerge")

    /**
     * Apply filtering with minimum difference of two streams.
     */
    public data object MaskedMin : VideoFilter("maskedmin")

    /**
     * Pick pixels comparing absolute difference of two streams with threshold.
     */
    public data object MaskedThreshold : VideoFilter("maskedthreshold")

    /**
     * Create Mask.
     */
    public data object MaskFun : VideoFilter("maskfun")

    /**
     * Apply motion compensating deinterlacing.
     */
    public data object MCDEInt : VideoFilter("mcdeint")

    /**
     * Apply Median filter.
     */
    public data object Median : VideoFilter("median")

    /**
     * Merge planes.
     */
    public data object MergePlanes : VideoFilter("mergeplanes")

    /**
     * Generate motion vectors.
     */
    public data class MEstimate(
        val method: Method = Method.ESA,
        val mbSize: Int = 16,
        val search: Int = 7
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
    public data object Metadata : VideoFilter("metadata")

    /**
     * Apply Midway Equalization.
     */
    public data object MidEqualizer : VideoFilter("midequalizer")

    /**
     * Frame rate conversion using Motion Interpolation.
     */
    public data object MInterpolate : VideoFilter("minterpolate")

    /**
     * Mix video inputs.
     */
    public data object Mix : VideoFilter("mix")

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
    public data object Mpdecimate : VideoFilter("mpdecimate")

    /**
     * Calculate the MSAD between two video streams.
     */
    public data object MSAD : VideoFilter("msad")

    /**
     * Multiply first video stream with second video stream.
     */
    public data class Multiply(val scale: Int = 1, val offset: Float = 0.5f) : VideoFilter("multiply")

    /**
     * Negate input video.
     */
    public data class Negate(val components: List<Component>) : VideoFilter("negate")

    /**
     * Non-local means denoiser.
     */
    public data object NLMeans : VideoFilter("nlmeans")

    /**
     * Non-local means denoiser through OpenCL
     */
    public data object NLMeansOpencl : VideoFilter("nlmeans_opencl")

    /**
     * Apply neural network edge directed interpolation intra-only deinterlacer.
     */
    public data object NNEDI : VideoFilter("nnedi")

    /**
     * Force libavfilter not to use any of the specified pixel formats for the input to the next filter.
     */
    public data class NoFormat(val pixelFormats: List<PixelFormat>) : VideoFilter("noformat")

    /**
     * Add noise.
     */
    public data object Noise : VideoFilter("noise")

    /**
     * Normalize RGB video.
     */
    public data object Normalize : VideoFilter("normalize")

    /**
     * Pass the source unchanged to the output.
     */
    public data object Null : VideoFilter("null")

    /**
     * 2D Video Oscilloscope.
     */
    public data object Oscilloscope : VideoFilter("oscilloscope")

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
    public data object OverlayOpencl : VideoFilter("overlay_opencl")

    /**
     * Quick Sync Video overlay.
     */
    public data object OverlayQsv : VideoFilter("overlay_qsv")

    /**
     * Overlay one video on top of another
     */
    public data object OverlayVaapi : VideoFilter("overlay_vaapi")

    /**
     * Overlay one video on top of another using CUDA
     */
    public data object OverlayCuda : VideoFilter("overlay_cuda")

    /**
     * Denoise using wavelets.
     */
    public data object Owdenoise : VideoFilter("owdenoise")

    /**
     * Pad the input video.
     */
    public data object Pad : VideoFilter("pad")

    /**
     * Pad the input video.
     */
    public data object PadOpencl : VideoFilter("pad_opencl")

    /**
     * Find the optimal palette for a given stream.
     */
    public data object Palettegen : VideoFilter("palettegen")

    /**
     * Use a palette to downsample an input video stream.
     */
    public data object Paletteuse : VideoFilter("paletteuse")

    /**
     * Set permissions for the output video frame.
     */
    public data object Perms : VideoFilter("perms")

    /**
     * Correct the perspective of video.
     */
    public data object Perspective : VideoFilter("perspective")

    /**
     * Phase shift fields.
     */
    public data object Phase : VideoFilter("phase")

    /**
     * Filter out photosensitive epilepsy seizure-inducing flashes.
     */
    public data object Photosensitivity : VideoFilter("photosensitivity")

    /**
     * Test pixel format definitions.
     */
    public data object PixDesctest : VideoFilter("pixdesctest")

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
            MAX
        }
    }

    /**
     * Pixel data analysis.
     */
    public data object PixScope : VideoFilter("pixscope")

    /**
     * Filter video using libpostproc.
     */
    public data object Pp : VideoFilter("pp")

    /**
     * Apply Postprocessing 7 filter.
     */
    public data object Pp7 : VideoFilter("pp7")

    /**
     * PreMultiply first stream with first plane of second stream.
     */
    public data class Premultiply(val inPlace: Boolean = false) : VideoFilter("premultiply")

    /**
     * Apply prewitt operator.
     */
    public data object Prewitt : VideoFilter("prewitt")

    /**
     * Apply prewitt operator
     */
    public data object PrewittOpencl : VideoFilter("prewitt_opencl")

    /**
     * ProcAmp (color balance) adjustments for hue, saturation, brightness, contrast
     */
    public data object ProcampVaapi : VideoFilter("procamp_vaapi")

    /**
     * Filter video using an OpenCL program
     */
    public data object ProgramOpencl : VideoFilter("program_opencl")

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
    public data object Psnr : VideoFilter("psnr")

    /**
     * Pullup from field sequence to frames.
     */
    public data object Pullup : VideoFilter("pullup")

    /**
     * Change video quantization parameters.
     */
    public data class Qp(val qp: Expression) : VideoFilter("qp")

    /**
     * Return random frames.
     */
    public data class Random(val frames: Int = 30, val seed: Int = -1) : VideoFilter("random")

    /**
     * Read EIA-608 Closed Caption codes from input video and write them to frame metadata.
     */
    public data object ReadEIA608 : VideoFilter("readeia608")

    /**
     * Read vertical interval timecode and write it to frame metadata.
     */
    public data object ReadVITC : VideoFilter("readvitc")

    /**
     * Slow down filtering to match realtime.
     */
    public data object Realtime : VideoFilter("realtime")

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
    public data object RemapOpencl : VideoFilter("remap_opencl")

    /**
     * Remove grain.
     */
    public data object RemoveGrain : VideoFilter("removegrain")

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
    public data object Roberts : VideoFilter("roberts")

    /**
     * Apply roberts operator
     */
    public data object RobertsOpencl : VideoFilter("roberts_opencl")

    /**
     * Rotate the input image.
     */
    public data object Rotate : VideoFilter("rotate")

    /**
     * Apply shape adaptive blur.
     */
    public data object Sab : VideoFilter("sab")

    /**
     * Scale the input video size and/or convert the image format.
     */
    public data object Scale : VideoFilter("scale")

    /**
     * GPU accelerated video resizer
     */
    public data object ScaleCuda : VideoFilter("scale_cuda")

    /**
     * Quick Sync Video "scaling and format conversion"
     */
    public data object ScaleQsv : VideoFilter("scale_qsv")

    /**
     * Scale to/from VAAPI surfaces.
     */
    public data object ScaleVaapi : VideoFilter("scale_vaapi")
    // ..C scale2ref         VV->VV     Scale the input video size and/or convert the image format to the given reference.
    /**
     * Detect video scene change
     */
    public data object Scdet : VideoFilter("scdet")

    /**
     * Apply scharr operator.
     */
    public data object Scharr : VideoFilter("scharr")

    /**
     * Scroll input video.
     */
    public data object Scroll : VideoFilter("scroll")

    /**
     * Apply CMYK adjustments to specific color ranges.
     */
    public data object Selectivecolor : VideoFilter("selectivecolor")

    /**
     * Send commands to filters.
     */
    public data object SendCmd : VideoFilter("sendcmd")

    /**
     * Split input video frames into fields.
     */
    public data object SeparateFields : VideoFilter("separatefields")

    /**
     * Set the frame display aspect ratio.
     */
    public data object SetDar : VideoFilter("setdar")

    /**
     * Force field for the output video frame.
     */
    public data object SetField : VideoFilter("setfield")

    /**
     * Force field, or color property for the output video frame.
     */
    public data object SetParams : VideoFilter("setparams") {
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
    public data object SetPTS : VideoFilter("setpts")

    /**
     * Force color range for the output video frame.
     */
    public data object Setrange : VideoFilter("setrange")

    /**
     * Set the pixel sample aspect ratio.
     */
    public data object Setsar : VideoFilter("setsar")

    /**
     * Set timebase for the video output link.
     */
    public data object Settb : VideoFilter("settb")

    /**
     * VAAPI VPP for sharpness
     */
    public data object SharpnessVaapi : VideoFilter("sharpness_vaapi")

    /**
     * Shear transform the input image.
     */
    public data object Shear : VideoFilter("shear")

    /**
     * Show textual information for each video frame.
     */
    public data object Showinfo : VideoFilter("showinfo")

    /**
     * Display frame palette.
     */
    public data object Showpalette : VideoFilter("showpalette")

    /**
     * Shuffle video frames.
     */
    public data object Shuffleframes : VideoFilter("shuffleframes")

    /**
     * Shuffle video pixels.
     */
    public data object Shufflepixels : VideoFilter("shufflepixels")

    /**
     * Shuffle video planes.
     */
    public data object Shuffleplanes : VideoFilter("shuffleplanes")

    /**
     * Manipulate video frame side data.
     */
    public data object Sidedata : VideoFilter("sidedata")

    /**
     * Generate statistics from video analysis.
     */
    public data object Signalstats : VideoFilter("signalstats")

    /**
     * Calculate the MPEG-7 video signature
     */
    public data object Signature : VideoFilter("signature")

    /**
     * Calculate spatial information (SI) and temporal information (TI).
     */
    public data object Siti : VideoFilter("siti")

    /**
     * Blur the input video without impacting the outlines.
     */
    public data object Smartblur : VideoFilter("smartblur")

    /**
     * Apply sobel operator.
     */
    public data object Sobel : VideoFilter("sobel")

    /**
     * Apply sobel operator
     */
    public data object SobelOpencl : VideoFilter("sobel_opencl")

    /**
     * Apply a simple post processing filter.
     */
    public data object Spp : VideoFilter("spp")

    /**
     * Apply DNN-based image super resolution to the input.
     */
    public data object Sr : VideoFilter("sr")

    /**
     * Calculate the SSIM between two video streams.
     */
    public data object Ssim : VideoFilter("ssim")

    /**
     * Calculate the SSIM between two 360 video streams.
     */
    public data object Ssim360 : VideoFilter("ssim360")

    /**
     * Convert video stereoscopic 3D view.
     */
    public data object Stereo3d : VideoFilter("stereo3d")

    /**
     * Render text subtitles onto input video using the libass library.
     */
    public data object Subtitles : VideoFilter("subtitles")

    /**
     * Scale the input by 2x using the Super2xSaI pixel art algorithm.
     */
    public data object Super2xsai : VideoFilter("super2xsai")

    /**
     * Swap 2 rectangular objects in video.
     */
    public data object Swaprect : VideoFilter("swaprect")

    /**
     * Swap U and V components.
     */
    public data object Swapuv : VideoFilter("swapuv")

    /**
     * Blend successive frames.
     */
    public data object Tblend : VideoFilter("tblend")

    /**
     * Apply a telecine pattern.
     */
    public data object Telecine : VideoFilter("telecine")

    /**
     * Compute and draw a temporal histogram.
     */
    public data object Thistogram : VideoFilter("thistogram")

    /**
     * Threshold first video stream using other video streams.
     */
    public data object Threshold : VideoFilter("threshold")

    /**
     * Select the most representative frame in a given sequence of consecutive frames.
     */
    public data object Thumbnail : VideoFilter("thumbnail")

    /**
     * Select the most representative frame in a given sequence of consecutive frames.
     */
    public data object ThumbnailCuda : VideoFilter("thumbnail_cuda")

    /**
     * Tile several successive frames together.
     */
    public data object Tile : VideoFilter("tile")

    /**
     * Generate a tilt-and-shift'd video.
     */
    public data object Tiltandshift : VideoFilter("tiltandshift")

    /**
     * Perform temporal field interlacing.
     */
    public data object Tinterlace : VideoFilter("tinterlace")

    /**
     * Compute and apply a lookup table from two successive frames.
     */
    public data object Tlut2 : VideoFilter("tlut2")

    /**
     * Pick median pixels from successive frames.
     */
    public data object Tmedian : VideoFilter("tmedian")

    /**
     * Apply Temporal Midway Equalization.
     */
    public data object Tmidequalizer : VideoFilter("tmidequalizer")

    /**
     * Mix successive video frames.
     */
    public data object Tmix : VideoFilter("tmix")

    /**
     * Conversion to/from different dynamic ranges.
     */
    public data object Tonemap : VideoFilter("tonemap")

    /**
     * Perform HDR to SDR conversion with tonemapping.
     */
    public data object TonemapOpencl : VideoFilter("tonemap_opencl")

    /**
     * VAAPI VPP for tone-mapping
     */
    public data object TonemapVaapi : VideoFilter("tonemap_vaapi")

    /**
     * Temporarily pad video frames.
     */
    public data object Tpad : VideoFilter("tpad")

    /**
     * Transpose input video.
     */
    public data object Transpose : VideoFilter("transpose")

    /**
     * Transpose input video
     */
    public data object TransposeOpencl : VideoFilter("transpose_opencl")

    /**
     * VAAPI VPP for transpose
     */
    public data object TransposeVaapi : VideoFilter("transpose_vaapi")

    /**
     * Pick one continuous section from the input, drop the rest.
     */
    public data object Trim : VideoFilter("trim")

    /**
     * UnPreMultiply first stream with first plane of second stream.
     */
    public data object Unpremultiply : VideoFilter("unpremultiply")

    /**
     * Sharpen or blur the input video.
     */
    public data object Unsharp : VideoFilter("unsharp")

    /**
     * Apply unsharp mask to input video
     */
    public data object UnsharpOpencl : VideoFilter("unsharp_opencl")

    /**
     * Untile a frame into a sequence of frames.
     */
    public data object Untile : VideoFilter("untile")

    /**
     * Apply Ultra Simple / Slow Post-processing filter.
     */
    public data object Uspp : VideoFilter("uspp")

    /**
     * Convert 360 projection of video.
     */
    public data object V360 : VideoFilter("v360")

    /**
     * Apply a Wavelet based Denoiser.
     */
    public data object Vaguedenoiser : VideoFilter("vaguedenoiser")

    /**
     * Apply Variable Blur filter.
     */
    public data object Varblur : VideoFilter("varblur")

    /**
     * Video vectorscope.
     */
    public data object Vectorscope : VideoFilter("vectorscope")

    /**
     * Flip the input video vertically.
     */
    public data object Vflip : VideoFilter("vflip")

    /**
     * Variable frame rate detect filter.
     */
    public data object Vfrdet : VideoFilter("vfrdet")

    /**
     * Boost or alter saturation.
     */
    public data object Vibrance : VideoFilter("vibrance")

    /**
     * Extract relative transformations, pass 1 of 2 for stabilization (see vidstabtransform for pass 2).
     */
    public data object Vidstabdetect : VideoFilter("vidstabdetect")

    /**
     * Transform the frames, pass 2 of 2 for stabilization (see vidstabdetect for pass 1).
     */
    public data object Vidstabtransform : VideoFilter("vidstabtransform")

    /**
     * Calculate the VIF between two video streams.
     */
    public data object Vif : VideoFilter("vif")

    /**
     * Make or reverse a vignette effect.
     */
    public data object Vignette : VideoFilter("vignette")

    /**
     * Calculate the VMAF Motion score.
     */
    public data object Vmafmotion : VideoFilter("vmafmotion")

    /**
     * Quick Sync Video "VPP"
     */
    public data object VppQsv : VideoFilter("vpp_qsv")

    /**
     * Stack video inputs vertically.
     */
    public data object Vstack : VideoFilter("vstack")

    /**
     * Apply Martin Weston three field deinterlace.
     */
    public data object W3fdif : VideoFilter("w3fdif")

    /**
     * Video waveform monitor.
     */
    public data object Waveform : VideoFilter("waveform")

    /**
     * Weave input video fields into frames.
     */
    public data object Weave : VideoFilter("weave")

    /**
     * Scale the input using xBR algorithm.
     */
    public data object Xbr : VideoFilter("xbr")

    /**
     * Cross-correlate first video stream with second video stream.
     */
    public data object Xcorrelate : VideoFilter("xcorrelate")

    /**
     * Cross fade one video with another video.
     */
    public data object Xfade : VideoFilter("xfade")

    /**
     * Cross fade one video with another video.
     */
    public data object XfadeOpencl : VideoFilter("xfade_opencl")

    /**
     * Pick median pixels from several video inputs.
     */
    public data object Xmedian : VideoFilter("xmedian")

    /**
     * Stack video inputs into custom layout.
     */
    public data object Xstack : VideoFilter("xstack")

    /**
     * Deinterlace the input image.
     */
    public data object Yadif : VideoFilter("yadif")

    /**
     * Deinterlace CUDA frames
     */
    public data object YadifCuda : VideoFilter("yadif_cuda")

    /**
     * Yet another edge preserving blur filter.
     */
    public data object Yaepblur : VideoFilter("yaepblur")

    /**
     * Apply Zoom & Pan effect.
     */
    public data object Zoompan : VideoFilter("zoompan")

    /**
     * Apply resizing, colorspace and bit depth conversion.
     */
    public data object Zscale : VideoFilter("zscale")

    /**
     * "VA-API" hstack
     */
    public data object HstackVaapi : VideoFilter("hstack_vaapi")

    /**
     * "VA-API" vstack
     */
    public data object VstackVaapi : VideoFilter("vstack_vaapi")

    /**
     * "VA-API" xstack
     */
    public data object XstackVaapi : VideoFilter("xstack_vaapi")

    /**
     * "Quick Sync Video" hstack
     */
    public data object HstackQSV : VideoFilter("hstack_qsv")

    /**
     * "Quick Sync Video" vstack
     */
    public data object VstackQSV : VideoFilter("vstack_qsv")

    /**
     * "Quick Sync Video" xstack
     */
    public data object XstackQSV : VideoFilter("xstack_qsv")

    /**
     * Generate all RGB colors.
     */
    public data object AllRGB : VideoFilter("allrgb")

    /**
     * Generate all yuv colors.
     */
    public data object Allyuv : VideoFilter("allyuv")

    /**
     * Create pattern generated by an elementary cellular automaton.
     */
    public data object CellAuto : VideoFilter("cellauto")

    /**
     * Provide an uniformly colored input.
     */
    public data object Color : VideoFilter("color")

    /**
     * Generate color checker chart.
     */
    public data object Colorchart : VideoFilter("colorchart")

    /**
     * Generate colors spectrum.
     */
    public data object Colorspectrum : VideoFilter("colorspectrum")

    /**
     * Generate a frei0r source.
     */
    public data object Frei0rSrc : VideoFilter("frei0r_src")

    /**
     * Draw a gradients.
     */
    public data object Gradients : VideoFilter("gradients")

    /**
     * Provide an identity Hald CLUT.
     */
    public data object Haldclutsrc : VideoFilter("haldclutsrc")

    /**
     * Create life.
     */
    public data object Life : VideoFilter("life")

    /**
     * Render a Mandelbrot fractal.
     */
    public data object Mandelbrot : VideoFilter("mandelbrot")

    /**
     * Generate various test pattern.
     */
    public data object Mptestsrc : VideoFilter("mptestsrc")

    /**
     * Null video source, return unprocessed video frames.
     */
    public data object Nullsrc : VideoFilter("nullsrc")

    /**
     * Generate video using an OpenCL program
     */
    public data object Openclsrc : VideoFilter("openclsrc")

    /**
     * Generate PAL 75% color bars.
     */
    public data object Pal75bars : VideoFilter("pal75bars")

    /**
     * Generate PAL 100% color bars.
     */
    public data object Pal100bars : VideoFilter("pal100bars")

    /**
     * Generate RGB test pattern.
     */
    public data object Rgbtestsrc : VideoFilter("rgbtestsrc")

    /**
     * Render a Sierpinski fractal.
     */
    public data object Sierpinski : VideoFilter("sierpinski")

    /**
     * Generate SMPTE color bars.
     */
    public data object Smptebars : VideoFilter("smptebars")

    /**
     * Generate SMPTE HD color bars.
     */
    public data object Smptehdbars : VideoFilter("smptehdbars")

    /**
     * Generate test pattern.
     */
    public data object Testsrc : VideoFilter("testsrc")

    /**
     * Generate another test pattern.
     */
    public data object Testsrc2 : VideoFilter("testsrc2")

    /**
     * Generate YUV test pattern.
     */
    public data object Yuvtestsrc : VideoFilter("yuvtestsrc")

    /**
     * Generate zone-plate.
     */
    public data object Zoneplate : VideoFilter("zoneplate")
    // ... nullsink          V->|       Do absolutely nothing with the input video.
    /**
     * Convert input audio to 3d scope video output.
     */
    public data object A3dscope : VideoFilter("a3dscope")

    /**
     * Convert input audio to audio bit scope video output.
     */
    public data object Abitscope : VideoFilter("abitscope")

    /**
     * Draw a graph using input audio metadata.
     */
    public data object Adrawgraph : VideoFilter("adrawgraph")

    /**
     * Show various filtergraph stats.
     */
    public data object Agraphmonitor : VideoFilter("agraphmonitor")

    /**
     * Convert input audio to histogram video output.
     */
    public data object Ahistogram : VideoFilter("ahistogram")
    // ... aphasemeter       A->N       Convert input audio to phase meter video output.
    /**
     * Convert input audio to vectorscope video output.
     */
    public data object Avectorscope : VideoFilter("avectorscope")
    // ..C concat            N->N       Concatenate audio and video streams.
    /**
     * Convert input audio to a CQT (Constant/Clamped Q Transform) spectrum video output.
     */
    public data object Showcqt : VideoFilter("showcqt")

    /**
     * Convert input audio to a CWT (Continuous Wavelet Transform) spectrum video output.
     */
    public data object ShowCWT : VideoFilter("showcwt")

    /**
     * Convert input audio to a frequencies video output.
     */
    public data object ShowFreqs : VideoFilter("showfreqs")

    /**
     * Convert input audio to a spatial video output.
     */
    public data object ShowSpatial : VideoFilter("showspatial")

    /**
     * Convert input audio to a spectrum video output.
     */
    public data object ShowSpectrum : VideoFilter("showspectrum")

    /**
     * Convert input audio to a spectrum video output single picture.
     */
    public data object ShowSpectrumPic : VideoFilter("showspectrumpic")

    /**
     * Convert input audio volume to video output.
     */
    public data object ShowVolume : VideoFilter("showvolume")

    /**
     * Convert input audio to a video output.
     */
    public data object ShowWaves : VideoFilter("showwaves")

    /**
     * Convert input audio to a video output single picture.
     */
    public data object ShowWavespic : VideoFilter("showwavespic")
}