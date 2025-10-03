package dev.zt64.ffmpegkt.avutil.audio

import kotlin.jvm.JvmInline

@JvmInline
public value class ChannelOrder(internal val num: Int) {
    public companion object {
        /**
         * Only the channel count is specified, without any further information
         * about the channel order.
         */
        public val UNSPECIFIED: ChannelOrder = ChannelOrder(0)

        /**
         * The native channel order, i.e. the channels are in the same order in
         * which they are defined in the AVChannel enum. This supports up to 63
         * different channels.
         */
        public val NATIVE: ChannelOrder = ChannelOrder(1)

        /**
         * The channel order does not correspond to any other predefined order and
         * is stored as an explicit map. For example, this could be used to support
         * layouts with 64 or more channels, or with empty/skipped (AV_CHAN_UNUSED)
         * channels at arbitrary positions.
         */
        public val CUSTOM: ChannelOrder = ChannelOrder(2)

        /**
         * The audio is represented as the decomposition of the sound field into
         * spherical harmonics. Each channel corresponds to a single expansion
         * component. Channels are ordered according to ACN (Ambisonic Channel
         * Number).
         *
         * The channel with the index n in the stream contains the spherical
         * harmonic of degree l and order m given by
         * @code{.unparsed}
         *   l   = floor(sqrt(n)),
         *   m   = n - l * (l + 1).
         * @endcode
         *
         * Conversely given a spherical harmonic of degree l and order m, the
         * corresponding channel index n is given by
         * @code{.unparsed}
         *   n = l * (l + 1) + m.
         * @endcode
         *
         * Normalization is assumed to be SN3D (Schmidt Semi-Normalization)
         * as defined in AmbiX format $ 2.1.
         */
        public val AMBISONIC: ChannelOrder = ChannelOrder(0)

        /**
         * Number of channel orders, not part of ABI/API
         */
        public val NB: ChannelOrder = ChannelOrder(0)
    }
}