package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.RawRepresentable

public expect object Logging {
    public var level: LogLevel
    public var flags: Int

    public fun setCallback(callback: (LogLevel, String) -> Unit)
    public fun restoreDefaultCallback()

    public fun log(level: LogLevel, message: String)
}

/**
 * Log level used by FFmpeg logging component
 */
public enum class LogLevel(override val rawValue: Int) : RawRepresentable<Int> {
    QUIET(-8),
    PANIC(0),
    FATAL(8),
    ERROR(16),
    WARNING(24),
    INFO(32),
    VERBOSE(40),
    DEBUG(48),
    TRACE(56)
}