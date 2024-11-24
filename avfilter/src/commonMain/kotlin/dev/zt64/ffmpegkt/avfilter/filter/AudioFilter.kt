package dev.zt64.ffmpegkt.avfilter.filter

import dev.zt64.ffmpegkt.avutil.AVChannelLayout
import dev.zt64.ffmpegkt.avutil.AVSampleFormat
import dev.zt64.ffmpegkt.avutil.SampleFormat
import kotlin.random.Random
import kotlin.time.Duration

public sealed class AudioFilter(name: String) : Filter(name) {
    /**
     * Apply Affine Projection algorithm to the first audio stream using the second audio stream.
     */
    public data class AP(public val order: Int, public val projection: Int) : AudioFilter("aap")

    public data class Compressor(
        public val threshold: Double,
        public val ratio: Double,
        public val attack: Double,
        public val release: Double,
        public val makeup: Double,
        public val detection: Int,
        public val link: Int
    ) : AudioFilter("acompressor")

    public data class Contrast(public val contrast: Int) : AudioFilter("acontrast")

    public data class CrossFade(
        public val samples: Int,
        public val duration: Duration,
        public val overlap: Boolean,
        public val curveFirst: Curve,
        public val curveSecond: Curve
    ) : AudioFilter("acrossfade")

    public data class Crusher(
        public val bits: Int,
        public val depth: Int,
        public val mix: Double
    ) : AudioFilter("acrusher")

    public data object Cue : AudioFilter("acue")

    /**
     * TODO
     *
     * @property window window size, in milliseconds. Allowed range is from 10 to 100. Default value is 55 milliseconds. This sets size of window which will be processed at once.
     * @property overlap window overlap, in percentage of window size. Allowed range is from 50 to 95. Default value is 75 percent. Setting this to a very high value increases impulsive noise removal but makes whole process much slower.
     * @property autoRegressionOrder
     * @property threshold Allowed range is from **1 to 100**. Default value is 2. This controls the strength of impulsive noise which is going to be removed. The lower value, the more samples will be detected as impulsive noise.
     * @property burst
     * @property method
     */
    public data class DEClick(
        val window: Int = 55,
        val overlap: Int = 75,
        val autoRegressionOrder: Int = 2,
        val threshold: Int = 2,
        val burst: Int = 2,
        val method: OverlapMethod = OverlapMethod.ADD
    ) : AudioFilter("adeclick")

    /**
     * TODO: Add documentation
     *
     * @property window window size, in milliseconds. Allowed range is from 10 to 100. Default value is 55 milliseconds. This sets size of window which will be processed at once.
     * @property overlap window overlap, in percentage of window size. Allowed range is from 50 to 95.
     * @property autoRegressionOrder autoregression order, in percentage of window size. Allowed range is from 0 to 25. Default value is 8 percent. This option also controls quality of interpolated samples using neighbour good samples.
     * @property threshold Allowed range is from 1 to 100. Default value is 10. Higher values make clip detection less aggressive.
     * @property hsize size of histogram used to detect clips. Allowed range is from 100 to 9999. Higher values make clip detection less aggressive.
     * @property method
     */
    public data class DEClip(
        val window: Int = 55,
        val overlap: Int = 75,
        val autoRegressionOrder: Int = 8,
        val threshold: Int = 10,
        val hsize: Int = 1000,
        val method: OverlapMethod = OverlapMethod.ADD
    ) : AudioFilter("adeclick")

    public data class Decorrelate(val stages: Int = 6, val seed: Int = Random.nextInt()) : AudioFilter("adecorrelate")

    public data class Delay(val delays: List<Int>) : AudioFilter("adelay")

    public data class Denorm(val level: Int = -351, val type: Type = Type.DC) : AudioFilter("adenorm") {
        public enum class Type {
            DC,
            AC,
            SQUARE,
            PULSE
        }
    }

    public data object Derivative : AudioFilter("aderivative")

    public data object Integral : AudioFilter("aintegral")

    /**
     * TODO: Add documentation
     *
     * @property attack
     * @property release
     * @property channels
     */
    public data class DRC(
        val attack: Int,
        val release: Int,
        val channels: List<Int>? = null
    ) : AudioFilter("adrc")

    public data class DynamicEqualizer(val threshold: Int = 0) : AudioFilter("adynamicequalizer")

    public data class DynamicSmooth(val sensitivity: Int, val baseFreq: Int) : AudioFilter("adynamics")

    public data class Echo(val inGain: Float = 0.6f, val outGain: Float = 0.3f) : AudioFilter("aecho")

    public data class Emphasis(val levelIn: Int, val levelOut: Int) : AudioFilter("aemphasis")

    public data class Eval(val expressions: List<String>) : AudioFilter("aeval") {
        public constructor(vararg expressions: String) : this(expressions.toList())
    }

    public data class Exciter(val levelIn: Float) : AudioFilter("aexciter")

    /**
     * Fade
     *
     * @property type Type of fade, either IN or OUT
     * @property startSample
     * @property nbSamples Specify the number of samples for which the fade effect has to last. At the end of the fade-in effect the output
     * audio will have the same volume as the input audio, at the end of the fade-out transition the output audio will be silence.
     * @property startTime The time in seconds at which the fade effect has to start. If not specified, the fade effect will start at the
     * beginning of the audio stream.
     * @property duration The duration of the fade in seconds
     * @property curve The curve type of the fade effect
     */
    public data class Fade(
        val type: Type,
        val startSample: Int = 0,
        val nbSamples: Int = 44100,
        val startTime: Int = 0,
        val duration: Int? = null,
        val curve: Curve = Curve.TRI
    ) : AudioFilter("afade") {
        public enum class Type {
            IN,
            OUT
        }
    }

    /**
     * Denoise audio samples with FFT.
     *
     * @property noiseReduction
     * @property noiseFloor
     */
    public data class FFTDN(
        val noiseReduction: Float = 12f,
        val noiseFloor: Float = -50f,
        val noiseType: NoiseType = NoiseType.WHITE
    ) : AudioFilter("afftdn") {
        public enum class NoiseType {
            WHITE,
            VINYL,
            SHELLAC,
            CUSTOM
        }

        public enum class OutputMode {
            INPUT,
            OUTPUT,
            NOISE
        }
    }

    public data class FIR : AudioFilter("afir")

    public data class Format(
        val sampleFormats: List<SampleFormat>,
        val sampleRates: List<Int>,
        val channelLayouts: List<AVChannelLayout>
    ) : AudioFilter("aformat")

    public data class FreqShift(
        val shift: Float = 0f,
        val level: Float = 1f,
        val order: Int = 8
    ) : AudioFilter("afreqshift")

    public data class FWTDN : AudioFilter("afwtdn")

    public data class Gate : AudioFilter("agate")

    public data class IIR : AudioFilter("aiir")

    public data class Limiter : AudioFilter("alimiter")

    public data class LLPass : AudioFilter("allpass")

    public data class Loop(
        val loops: Int = 0,
        val size: Int = 0,
        val start: Int = 0,
        val time: Int = 0
    ) : AudioFilter("aloop")

    public data class Merge(val inputs: Int = 2) : AudioFilter("amerge")

    public data class Mixer : AudioFilter("amix")

    public data object Multiply : AudioFilter("amultiply")

    public data class NEqualizer : AudioFilter("anequalizer")

    public data class NLMDN : AudioFilter("anlmdn")

    public data class NLMF : AudioFilter("anlmf")

    public data class NLMS : AudioFilter("anlms")

    public data object Null : AudioFilter("anull")

    public data class Pad : AudioFilter("apad")

    public data class Phaser(
        val inGain: Float = 0.4f,
        val outGain: Float = 0.74f,
        val delay: Float = 3.0f,
        val decay: Float = 0.4f,
        val speed: Float = 0.5f,
        val type: ModulationType = ModulationType.TRIANGULAR
    ) : AudioFilter("aphaser")

    public data class PhaseShift : AudioFilter("aphaseshift")

    public data object PSNR : AudioFilter("apsnr")

    public data class PSYClip : AudioFilter("apsyclip")

    public data class Pulsator : AudioFilter("apulsator")

    public data class Resample : AudioFilter("aresample")

    public data object Reverse : AudioFilter("areverse")

    public data class RLS : AudioFilter("arls")

    public data class RNNDN : AudioFilter("arnndn")

    public data class SDR : AudioFilter("asdr")

    public data class SetNSamples : AudioFilter("asetnsamples")

    public data class SetRate : AudioFilter("asetrate")

    public data class ShowInfo : AudioFilter("ashowinfo")

    public data class SISDR : AudioFilter("asisdr")

    public data class SoftClip : AudioFilter("asoftclip")

    public data class SpectralStats : AudioFilter("aspecstats")

    public data class SR : AudioFilter("asr")

    public data class Stats : AudioFilter("astats")

    public data class SubBoost : AudioFilter("asubboost")

    public data class SubCut : AudioFilter("asubcut")

    public data class SuperCut : AudioFilter("asupercut")

    public data class SuperPass : AudioFilter("asuperpass")

    public data class SuperStop : AudioFilter("asuperstop")

    public data class Tempo : AudioFilter("atempo")

    public data class Tilt : AudioFilter("atilt")

    public data class Trim : AudioFilter("atrim")

    public data class XCorrelate : AudioFilter("axcorrelate")

    public data class Bandpass(val frequency: Int, val width: Int) : AudioFilter("bandpass")

    public data class Bandreject(val frequency: Int, val width: Int) : AudioFilter("bandreject")

    public data class Bass(
        val gain: Int,
        val frequency: Int,
        val width: Int
    ) : AudioFilter("bass")

    public data class LowShelf(
        val gain: Int,
        val frequency: Int,
        val width: Int
    ) : AudioFilter("lowshelf")

    public data class Biquad(
        val b0: Double,
        val b1: Double,
        val b2: Double,
        val a0: Double,
        val a1: Double,
        val a2: Double
    ) : AudioFilter("biquad")

    public data class BS2B : AudioFilter("bs2b")

    public data class ChannelMap(val channelMap: List<Int>) : AudioFilter("channelmap")

    public data class Channelsplit(val channelCount: Int) : AudioFilter("channelsplit")

    public data class Chorus : AudioFilter("chorus")

    public data class Compand : AudioFilter("compand")

    public data class CompensationDelay : AudioFilter("compensationdelay")

    public data class CrossFeed : AudioFilter("crossfeed")

    public data class Crystalizer(val intensity: Float = 2.0f, val clipping: Boolean = true) : AudioFilter("crystalizer")

    public data class DCShift(val shift: Float, val limiterGain: Float? = null) : AudioFilter("dcshift")

    public data class Deesser : AudioFilter("deesser")

    public data class DialogueEnhance : AudioFilter("dialogueenhance")

    public data class DRMeter(val length: Int = 3) : AudioFilter("drmeter")

    public data class Dynaudnorm : AudioFilter("dynaudnorm")

    public data object Earwax : AudioFilter("earwax")

    public data class Equalizer : AudioFilter("equalizer")

    public data class ExtraStero(val difference: Float = 2.5f, val clipping: Boolean = true) : AudioFilter("extrastereo")

    public data class Firequalizer : AudioFilter("firequalizer")

    public data class Flanger(
        val delay: Int = 0,
        val depth: Int = 2,
        val regen: Int = 0,
        val width: Int = 71,
        val speed: Float = 0.5f,
        val shape: ModulationType = ModulationType.SINUSOIDAL,
        val phase: Int = 25,
        val interp: Interpolation = Interpolation.LINEAR
    ) : AudioFilter("flanger") {
        public enum class Interpolation {
            LINEAR,
            QUADRATIC
        }
    }

    public data class Haas : AudioFilter("haas")

    public data class HDCD : AudioFilter("hdcd")

    public data class Headphone : AudioFilter("headphone")

    public data class Highpass : AudioFilter("highpass")

    public data class Join : AudioFilter("join")

    public data class Ladspa : AudioFilter("ladspa")

    public data class Loudnorm : AudioFilter("loudnorm")

    public data class Lowpass : AudioFilter("lowpass")

    public data class LV2 : AudioFilter("lv2")

    public data class MCompand : AudioFilter("mcompand")

    public data class Pan : AudioFilter("pan")

    public data object ReplayGain : AudioFilter("replaygain")

    public data object Resample : AudioFilter("resample")

    public data class Rubberband : AudioFilter("rubberband")

    public data class SideChainCompress : AudioFilter("sidechaincompress")

    public data class SideChainGate : AudioFilter("sidechaingate")

    public data class SilenceDetect : AudioFilter("silencedetect")

    public data class SilenceRemove : AudioFilter("silenceremove")

    public data class Sofalizer : AudioFilter("sofalizer")

    public data class SpeechNormalizer : AudioFilter("speechnormalizer")

    public data class StereoTools : AudioFilter("stereotools")

    public data class StereoWiden : AudioFilter("stereowiden")

    public data class SuperEqualizer : AudioFilter("superequalizer")

    public data class Surround : AudioFilter("surround")

    public data class TiltShelf : AudioFilter("tiltshelf")

    public data class Treble : AudioFilter("treble")

    public data class HighShelf : AudioFilter("highshelf")

    public data class Tremolo(val frequency: Float = 5.0f, val depth: Float = 0.5f) : AudioFilter("tremolo")

    public data class Vibrato(val frequency: Float = 5.0f, val depth: Float = 0.5f) : AudioFilter("vibrato")

    /**
     * Virtual bass
     *
     * @property cutoff Cutoff frequency in Hz. Default value is 250 Hz. This sets the frequency below which the bass boost will be applied.
     * @property strength Strength of the bass boost. Default value is 3. This sets the strength of the bass boost.
     */
    public data class VirtualBass(val cutoff: Int = 250, val strength: Int = 3) : AudioFilter("virtualbass")

    public data class Volume(
        val volume: String = "1.0",
        val precision: Precision = Precision.FLOAT,
        val replayGain: ReplayGain = ReplayGain.DROP,
        val replayGainPreAmp: Double = 0.0,
        val replayGainNoclip: Boolean = true,
        val eval: Eval = Eval.ONCE
    ) : AudioFilter("volume") {
        public enum class Precision {
            FIXED,
            FLOAT,
            DOUBLE
        }

        public enum class ReplayGain {
            DROP,
            IGNORE,
            TRACK,
            ALBUM
        }

        public enum class Eval {
            ONCE,
            FRAME
        }
    }

    public data class VolumeDetect(val t: Int) : AudioFilter("volumedetect")
}