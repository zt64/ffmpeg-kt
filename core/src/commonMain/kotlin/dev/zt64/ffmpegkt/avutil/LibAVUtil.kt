package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.FfmpegLibrary

/**
 * A wrapper object for the FFmpeg libavutil library.
 */
public expect object LibAVUtil : FfmpegLibrary {
    /**
     * Returns the version number of the libavutil library.
     * @return The library version integer.
     */
    public override fun version(): Int

    /**
     * Returns the configuration string used to build the libavutil library.
     * @return The build configuration string.
     */
    public override fun configuration(): String

    /**
     * Returns the license of the libavutil library.
     * @return The license string.
     */
    public override fun license(): String

    /**
     * Returns a string containing detailed version information.
     * @return The version information string.
     */
    public fun versionInfo(): String

    /**
     * Gets the fundamental time base unit used internally by FFmpeg.
     * This is equivalent to `AV_TIME_BASE_Q`.
     * @return The rational number representing the time base.
     */
    public fun getTimeBaseQ(): Rational

    /**
     * Converts an FFmpeg error code into a human-readable string.
     *
     * @param error The integer error code (usually a negative value).
     * @return A string describing the error.
     */
    public fun errorToString(error: Int): String

    /* Samples */
    public fun samplesFillArrays(
        audioData: ByteArray,
        linesize: IntArray,
        buffer: ByteArray,
        channels: Int,
        samples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int

    public fun samplesAlloc(
        audioData: ByteArray,
        linesize: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int

    public fun samplesAllocArrayAndSamples(
        audioData: Array<Array<ByteArray>>,
        linesizes: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: SampleFormat,
        align: Int
    ): Int

    public fun samplesCopy(
        dst: ByteArray,
        src: ByteArray,
        dstOffset: Int,
        srcOffset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: SampleFormat
    ): Int

    public fun samplesSetSilence(
        audioData: ByteArray,
        offset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: SampleFormat
    ): Int

    /**
     * Allocate a new image.
     *
     * @param pointers
     * @param linesizes
     * @param width
     * @param height
     * @param pixFmt
     * @param align
     * @return
     */
    public fun imageAlloc(
        pointers: ByteArray,
        linesizes: IntArray,
        width: Int,
        height: Int,
        pixFmt: PixelFormat,
        align: Int
    ): Int
}

public val LibAVUtil.logging: Logging get() = Logging