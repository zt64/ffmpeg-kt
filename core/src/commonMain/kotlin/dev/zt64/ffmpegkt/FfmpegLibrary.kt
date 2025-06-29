package dev.zt64.ffmpegkt

/**
 * A common interface for FFmpeg library wrappers.
 */
public interface FfmpegLibrary {
    /**
     * Retrieves the version number of the FFmpeg library.
     *
     * The version is typically an integer that can be decoded to a major.minor.micro format.
     *
     * @return The library version as an integer.
     */
    public fun version(): Int

    /**
     * Retrieves the configuration string used to build the FFmpeg library.
     *
     * This string provides details about the compile-time options and enabled features.
     *
     * @return The build configuration string.
     */
    public fun configuration(): String

    /**
     * Retrieves the license under which the FFmpeg library is distributed.
     *
     * @return The license string (e.g., "LGPL version 2.1 or later").
     */
    public fun license(): String
}