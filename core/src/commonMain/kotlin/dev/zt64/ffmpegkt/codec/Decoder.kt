package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.AudioFrame
import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.VideoFrame

/**
 * A sealed interface representing a decoder that converts compressed [Packet]s into raw [Frame]s.
 * This provides a unified API for both audio and video decoding operations.
 */
public sealed interface Decoder {
    // TODO: Figure out way to close this
    /**
     * The codec parser context associated with this decoder.
     *
     * A parser is used to split raw codec data into packets, which is useful when
     * processing elementary streams that are not in a container format.
     * The parser is lazily initialized on first access.
     */
    public val parser: CodecParserContext

    /**
     * Decodes a single [Packet] into one or more [Frame]s.
     *
     * This is a high-level convenience function that handles sending a packet and
     * receiving all resulting frames.
     *
     * @param packet The packet to decode. Passing `null` flushes the decoder,
     *               returning any internally buffered frames.
     * @return A list of frames decoded from the packet. The list may be empty if
     *         the decoder needs more packets to produce a frame.
     */
    public fun decode(packet: Packet?): List<Frame>
}

/**
 * An audio decoder for converting compressed audio [Packet]s into raw [AudioFrame]s.
 *
 * This class wraps an underlying FFmpeg audio `AVCodecContext` and provides a
 * simplified API for the decoding process.
 *
 * @param codec The audio [Codec] to use for decoding. It must be a decoder.
 * @constructor Creates a new audio decoder with the specified codec.
 */
public class AudioDecoder(codec: Codec) : AudioCodecContext(codec), Decoder {
    /**
     * A parser for handling raw audio codec data.
     * It is created on-demand when first accessed and its lifecycle is tied to this decoder.
     */
    public override val parser: CodecParserContext by lazy { CodecParserContext(this) }

    /**
     * Creates a new audio decoder by finding a registered decoder for the given [CodecID].
     *
     * @param codecID The ID of the desired audio codec.
     * @throws IllegalArgumentException if no decoder is found for the specified ID.
     */
    public constructor(codecID: CodecID) : this(Codec.findDecoder(codecID) ?: throw IllegalArgumentException("Codec not found: $codecID"))

    /**
     * Decodes a single audio [Packet] into one or more [AudioFrame]s.
     *
     * @param packet The [Packet] to decode. If `null`, the decoder is flushed, and any delayed frames are returned.
     * @return A list of decoded [AudioFrame]s.
     */
    public override fun decode(packet: Packet?): List<AudioFrame> {
        sendPacket(packet)

        return buildList {
            while (true) {
                val frame = decode() ?: break
                add(frame)
            }
        }
    }
}

/**
 * A video decoder for converting compressed video [Packet]s into raw [VideoFrame]s.
 *
 * This class wraps an underlying FFmpeg video `AVCodecContext` and provides a
 * simplified API for the decoding process.
 *
 * @param codec The video [Codec] to use for decoding. It must be a decoder.
 * @constructor Creates a new video decoder with the specified codec.
 */
public class VideoDecoder(codec: Codec) : VideoCodecContext(codec), Decoder {
    /**
     * A parser for handling raw video codec data.
     * It is created on-demand when first accessed and its lifecycle is tied to this decoder.
     */
    public override val parser: CodecParserContext by lazy { CodecParserContext(this) }

    /**
     * Creates a new video decoder by finding a registered decoder for the given [CodecID].
     *
     * @param codecID The ID of the desired video codec.
     * @throws IllegalArgumentException if no decoder is found for the specified ID.
     */
    // TODO: Implement proper checks for codec support
    public constructor(codecID: CodecID) : this(Codec.findDecoder(codecID) ?: throw IllegalArgumentException("Codec not found: $codecID"))

    /**
     * Decodes a single video [Packet] into one or more [VideoFrame]s.
     *
     * @param packet The [Packet] to decode. If `null`, the decoder is flushed, and any delayed frames are returned.
     * @return A list of decoded [VideoFrame]s.
     */
    public override fun decode(packet: Packet?): List<VideoFrame> {
        sendPacket(packet)

        return buildList {
            while (true) {
                val frame = decode() ?: break
                add(frame)
            }
        }
    }
}