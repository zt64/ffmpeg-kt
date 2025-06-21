package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avutil.LibAVUtil
import dev.zt64.ffmpegkt.avutil.LogLevel
import dev.zt64.ffmpegkt.avutil.logging
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class LoggingTest {
    @Test
    fun testCallback() {
        var callbackCalled = false

        LibAVUtil.logging.setCallback { level, message ->
            if (callbackCalled) {
                fail("Callback was already called")
            }

            @Suppress("AssignedValueIsNeverRead")
            callbackCalled = true

            println("[$level] $message")

            assertEquals(LogLevel.INFO, level, "Expected log level to be INFO")
            assertEquals("Hello, world!", message, "Expected message to be 'Hello, world!'")
        }

        logMessage()

        LibAVUtil.logging.restoreDefaultCallback()

        logMessage()
    }

    private fun logMessage() {
        LibAVUtil.logging.log(LogLevel.INFO, "Hello, world!")
    }
}