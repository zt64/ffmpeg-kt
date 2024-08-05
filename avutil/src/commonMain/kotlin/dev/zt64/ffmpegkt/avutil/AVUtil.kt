package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.FfmpegLibrary

public expect object AVUtil : FfmpegLibrary {
    public fun versionInfo(): String

    public fun getMediaTypeString(type: AVMediaType): String?

    public fun getPictureTypeChar(type: AVPictureType): Char

    public fun getTimeBaseQ(): AVRational

    public fun setLogLevel(level: Int)

    public fun errorToString(error: Int): String

    /* Samples */
    public fun samplesFillArrays(
        audioData: ByteArray,
        linesize: IntArray,
        buffer: ByteArray,
        channels: Int,
        samples: Int,
        sampleFmt: AVSampleFormat,
        align: Int
    ): Int

    public fun samplesAlloc(
        audioData: ByteArray,
        linesize: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: AVSampleFormat,
        align: Int
    ): Int

    public fun samplesAllocArrayAndSamples(
        audioData: ByteArray,
        linesizes: IntArray,
        nbChannels: Int,
        nbSamples: Int,
        sampleFmt: AVSampleFormat,
        align: Int
    ): Int

    public fun samplesCopy(
        dst: ByteArray,
        src: ByteArray,
        dstOffset: Int,
        srcOffset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: AVSampleFormat
    ): Int

    public fun samplesSetSilence(
        audioData: ByteArray,
        offset: Int,
        nbSamples: Int,
        nbChannels: Int,
        sampleFmt: AVSampleFormat
    ): Int

    public fun imageAlloc(
        pointers: ByteArray,
        linesizes: IntArray,
        width: Int,
        height: Int,
        pixFmt: AVPixelFormat,
        align: Int
    ): Int
}