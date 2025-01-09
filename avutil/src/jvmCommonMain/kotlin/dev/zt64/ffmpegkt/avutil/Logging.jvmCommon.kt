package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.valueOf
import org.bytedeco.ffmpeg.avutil.LogCallback
import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.ffmpeg.global.avutil.*
import org.bytedeco.javacpp.BytePointer

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

    public actual fun setCallback(callback: (LogLevel, String) -> Unit) {
        setLogCallback(object : LogCallback() {
            override fun call(level: Int, msg: BytePointer) {
                callback(valueOf(level), msg.string.orEmpty())
            }
        })
    }

    public actual fun restoreDefaultCallback() {
        av_log_set_callback(avutil.av_log_default_callback())
    }

    public actual fun log(level: LogLevel, message: String) {
        av_log(null, level.rawValue, message)
    }
}