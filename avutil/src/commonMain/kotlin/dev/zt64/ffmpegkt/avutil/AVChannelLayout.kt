package dev.zt64.ffmpegkt.avutil

public expect class NativeAVChannelLayout

/**
 * Represents the channel layout of audio data.
 *
 * @property native
 */
public expect value class AVChannelLayout internal constructor(internal val native: NativeAVChannelLayout) {
    public val order: AVChannelOrder
    public val nbChannels: Int

    public fun copyTo(dst: AVChannelLayout)

    public companion object {
        public val STEREO: AVChannelLayout
    }
}