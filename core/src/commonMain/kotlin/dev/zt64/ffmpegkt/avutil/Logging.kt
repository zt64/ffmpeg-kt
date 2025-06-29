package dev.zt64.ffmpegkt.avutil

import dev.zt64.ffmpegkt.RawRepresentable

/**
 * Provides control over FFmpeg's internal logging system.
 *
 * This object allows setting the global logging level, redirecting log output
 * to a custom callback, and manually logging messages through FFmpeg.
 */
public expect object Logging {
    /**
     * The current global logging level.
     *
     * Setting this property changes the verbosity of FFmpeg's logging output.
     * Any messages with a level less severe than this will be suppressed.
     */
    public var level: LogLevel

    /**
     * Flags to control the behavior of the logging system.
     * For example, can be used to control printing of the log level prefix.
     */
    public var flags: Int

    /**
     * Sets a custom callback function to handle FFmpeg log messages.
     *
     * This redirects all of FFmpeg's log output to your own function, allowing for
     * custom formatting or integration with other logging frameworks.
     *
     * @param callback A lambda that takes a [LogLevel] and the log message [String].
     */
    public fun setCallback(callback: (LogLevel, String) -> Unit)

    /**
     * Restores the default FFmpeg logging callback.
     *
     * This will make FFmpeg print its logs to stderr again, which is the default behavior.
     */
    public fun restoreDefaultCallback()

    /**
     * Logs a message through the FFmpeg logging system.
     *
     * The message will be processed according to the current log level and callback.
     *
     * @param level The severity level of the message.
     * @param message The string message to log.
     */
    public fun log(level: LogLevel, message: String)
}

/**
 * Represents the severity level of a log message in FFmpeg.
 *
 * @property rawValue The integer value corresponding to the FFmpeg constant.
 */
public enum class LogLevel(override val rawValue: Int) : RawRepresentable<Int> {
    /** Print no output. */
    QUIET(-8),

    /** A severe error that requires immediate action. */
    PANIC(0),

    /** A critical error that will likely lead to program termination. */
    FATAL(8),

    /** A recoverable error. */
    ERROR(16),

    /** A potential issue that does not prevent normal operation. */
    WARNING(24),

    /** General informational messages about progress or state. */
    INFO(32),

    /** More detailed informational messages. */
    VERBOSE(40),

    /** Messages useful for debugging. */
    DEBUG(48),

    /** The most detailed logging, for deep troubleshooting. */
    TRACE(56)
}