package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.valueOf
import ffmpeg.*
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlin.concurrent.AtomicReference

public actual object Logging {
    public actual var level: LogLevel
        get() = valueOf(av_log_get_level())
        set(value) {
            av_log_set_level(value.rawValue)
        }

    public actual var flags: Int
        get() = av_log_get_flags()
        set(value) {
            av_log_set_flags(value)
        }

    private val globalCallback = AtomicReference<((LogLevel, String) -> Unit)?>(null)

    public actual fun setCallback(callback: (LogLevel, String) -> Unit) {
        globalCallback.value = callback

        av_log_set_callback2(
            staticCFunction { ptr, level, fmt, args ->
                globalCallback.value?.invoke(valueOf(level), fmt?.toKString().orEmpty())
            }
        )
    }

    public actual fun restoreDefaultCallback() {
        globalCallback.value = null
        av_log_set_callback2(av_log_default_callback2())
    }

    public actual fun log(level: LogLevel, message: String) {
        av_log(null, level.rawValue, message)
    }
}