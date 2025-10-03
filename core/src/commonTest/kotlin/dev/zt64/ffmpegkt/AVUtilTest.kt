package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.audio.SampleFormat
import dev.zt64.ffmpegkt.avutil.audio.altSampleFmt
import dev.zt64.ffmpegkt.avutil.audio.bytesPerSample
import dev.zt64.ffmpegkt.avutil.audio.isPlanar
import dev.zt64.ffmpegkt.avutil.audio.name
import kotlin.test.Test
import kotlin.test.assertEquals

class AVUtilTest {
    @Test
    fun testVersionInfo() {
        println(LibAVUtil.versionInfo())
    }

    @Test
    fun testDictionary() {
        val dictionary: Dictionary = mapOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3",
            "key4" to "value4",
            "key5" to "value5"
        )

        val toNative = dictionary.toNative()
        val fromNative = Dictionary(toNative)

        assertEquals(
            expected = dictionary,
            actual = fromNative,
            message = "Dictionary should be the same after converting to and from native"
        )
    }

    @Test
    fun testSampleFormat() {
        val sampleFormat = SampleFormat("s16")
        assertEquals(
            expected = "s16",
            actual = sampleFormat.name,
            message = "Sample format name should be 's16'"
        )
        assertEquals(
            expected = 2,
            actual = sampleFormat.bytesPerSample,
            message = "Sample format bytes per sample should be 2"
        )
        assertEquals(
            expected = false,
            actual = sampleFormat.isPlanar,
            message = "Sample format should not be planar"
        )
        assertEquals(
            expected = SampleFormat.Companion.S16,
            actual = SampleFormat.Companion.S16.altSampleFmt(0),
            message = "Alternate sample format should be S16"
        )
    }
}