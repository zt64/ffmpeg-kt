package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.FfmpegLibrary

/**
 * A wrapper object for the FFmpeg libavcodec library.
 */
public expect object LibAVCodec : FfmpegLibrary {
    /**
     * Returns the version number of the libavcodec library.
     * @return The library version integer.
     */
    public override fun version(): Int

    /**
     * Returns the configuration string used to build the libavcodec library.
     * @return The build configuration string.
     */
    public override fun configuration(): String

    /**
     * Returns the license of the libavcodec library.
     * @return The license string.
     */
    public override fun license(): String
}