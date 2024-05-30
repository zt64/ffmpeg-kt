package dev.zt64.ffmpegkt.avutil

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AVUtilTest {
    @Test
    fun testVersionInfo() {
        println(AVUtil.versionInfo())
    }

    @Test
    fun testGetMediaTypeString() {
        assertNull(
            actual = AVUtil.getMediaTypeString(AVMediaType.UNKNOWN),
            message = "Unknown media type should return null"
        )

        assertEquals(
            expected = "video",
            actual = AVUtil.getMediaTypeString(AVMediaType.VIDEO),
            message = "Video media type should return 'video'"
        )

        assertEquals(
            expected = "audio",
            actual = AVUtil.getMediaTypeString(AVMediaType.AUDIO),
            message = "Audio media type should return 'audio'"
        )

        assertEquals(
            expected = "data",
            actual = AVUtil.getMediaTypeString(AVMediaType.DATA),
            message = "Data media type should return 'data'"
        )

        assertEquals(
            expected = "subtitle",
            actual = AVUtil.getMediaTypeString(AVMediaType.SUBTITLE),
            message = "Subtitle media type should return 'subtitle'"
        )

        assertEquals(
            expected = "attachment",
            actual = AVUtil.getMediaTypeString(AVMediaType.ATTACHMENT),
            message = "Attachment media type should return 'attachment'"
        )
    }

    @Test
    fun testGetPictureTypeChar() {
        assertEquals(
            expected = 'I',
            actual = AVUtil.getPictureTypeChar(AVPictureType.I),
            message = "Picture type I should return 'I'"
        )

        assertEquals(
            expected = 'P',
            actual = AVUtil.getPictureTypeChar(AVPictureType.P),
            message = "Picture type P should return 'P'"
        )

        assertEquals(
            expected = 'B',
            actual = AVUtil.getPictureTypeChar(AVPictureType.B),
            message = "Picture type B should return 'B'"
        )

        assertEquals(
            expected = 'S',
            actual = AVUtil.getPictureTypeChar(AVPictureType.S),
            message = "Picture type S should return 'S'"
        )

        assertEquals(
            expected = 'i',
            actual = AVUtil.getPictureTypeChar(AVPictureType.SI),
            message = "Picture type SI should return 'SI'"
        )

        assertEquals(
            expected = 'p',
            actual = AVUtil.getPictureTypeChar(AVPictureType.SP),
            message = "Picture type SP should return 'SP'"
        )

        assertEquals(
            expected = 'b',
            actual = AVUtil.getPictureTypeChar(AVPictureType.BI),
            message = "Picture type BI should return 'BI'"
        )
    }
}