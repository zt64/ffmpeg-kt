package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.FfmpegLibrary

/**
 * A wrapper object for the FFmpeg libavformat library.
 */
public expect object LibAVFormat : FfmpegLibrary {
    /**
     * Returns the version number of the libavformat library.
     * @return The library version integer.
     */
    public override fun version(): Int

    /**
     * Returns the configuration string used to build the libavformat library.
     * @return The build configuration string.
     */
    public override fun configuration(): String

    /**
     * Returns the license of the libavformat library.
     * @return The license string.
     */
    public override fun license(): String

    /**
     * Initializes networking capabilities for FFmpeg.
     *
     * This function must be called once at the beginning of the application if it
     * intends to use any network protocols (e.g., HTTP, RTSP).
     *
     * @return 0 on success, a negative error code on failure.
     */
    public fun networkInit(): Int

    /**
     * Deinitializes networking capabilities.
     *
     * This function should be called once at the end of the application to clean up
     * network resources.
     *
     * @return 0 on success, a negative error code on failure.
     */
    public fun networkDeinit(): Int
}