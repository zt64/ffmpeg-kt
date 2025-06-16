package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.FfmpegLibrary

public expect object LibAVUtil : FfmpegLibrary {
    public fun versionInfo(): String

    public fun getTimeBaseQ(): Rational

    /**
     * Convert an error code into a string.
     *
     * @param error
     * @return error string
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

public val LibAVUtil.logging get() = Logging