package dev.zt64.ffmpegkt.avutil

/**
 * Log level used by FFmpeg logging component
 */
public enum class LogLevel(public val value: Int) {
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