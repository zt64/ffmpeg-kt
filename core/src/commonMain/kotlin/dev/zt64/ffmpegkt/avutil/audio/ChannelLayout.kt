package dev.zt64.ffmpegkt.avutil.audio

public expect class NativeAVChannelLayout

/**
 * Represents the channel layout of audio data.
 *
 * @property native
 */
public expect value class ChannelLayout internal constructor(internal val native: NativeAVChannelLayout) {
    public val order: ChannelOrder
    public val nbChannels: Int

    public constructor(channels: Int)

    public fun copyTo(dst: ChannelLayout)

    public companion object {
        public val STEREO: ChannelLayout
    }
}