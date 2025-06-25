package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.AudioFrame
import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.VideoFrame

public sealed interface Decoder {
    // TODO: Figure out way to close this
    public val parser: CodecParserContext

    /**
     * Decode a packet into a list of frames.
     *
     * @param packet The packet to decode.
     * @return A list of frames decoded from the packet. If the packet is null, it indicates the end of the stream.
     */
    public fun decode(packet: Packet?): List<Frame>
}

/**
 * An audio decoder for converting [Packet]s into [AudioFrame]s.
 *
 * @constructor Create a new audio decoder with the given codec.
 * @param codec The codec to use for decoding.
 */
public class AudioDecoder(codec: Codec) : AudioCodecContext(codec), Decoder {
    /**
     * A parser for handling raw codec data.
     * It is created on-demand and its lifecycle is tied to this decoder.
     */
    public override val parser: CodecParserContext by lazy { CodecParserContext(this) }

    /**
     * Decodes a packet into a list of audio frames.
     *
     * @param packet The [Packet] to decode. If null, the decoder is flushed, and any delayed frames are returned.
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
 * A video decoder for converting [Packet]s into [VideoFrame]s.
 *
 * @constructor Create a new video decoder with the given codec.
 * @param codec The codec to use for decoding.
 */
public class VideoDecoder(codec: Codec) : VideoCodecContext(codec), Decoder {
    /**
     * A parser for handling raw codec data.
     * It is created on-demand and its lifecycle is tied to this decoder.
     */
    public override val parser: CodecParserContext by lazy { CodecParserContext(this) }

    /**
     * Decodes a packet into a list of video frames.
     *
     * @param packet The [Packet] to decode. If null, the decoder is flushed, and any delayed frames are returned.
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