package dev.zt64.ffmpegkt.avutil

import kotlin.test.Test
import kotlin.test.assertEquals

class LoggingTest {
    @Test
    fun testCallback() {
        var callbackCalled = false

        LibAVUtil.logging.setCallback { level, message ->
            if (callbackCalled) {
                throw IllegalStateException("Callback was already called")
            }

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