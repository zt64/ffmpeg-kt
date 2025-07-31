package dev.zt64.ffmpegkt.codec

import dev.zt64.ffmpegkt.avutil.*
import dev.zt64.ffmpegkt.avutil.hw.HWDeviceContext

internal const val ERROR_EOF = -541478725
internal const val ERROR_AGAIN = -11

/**
 * A wrapper around the native `AVCodecContext`.
 * This class serves as a base for encoders and decoders, providing common properties and methods
 * for handling media codecs.
 *
 * @property codec The underlying [Codec] associated with this context.
 */
public expect abstract class CodecContext(codec: Codec, hwAccel: HWDeviceContext? = null) : AutoCloseable {
    internal val codec: Codec

    /** The codec tag. */
    public var codecTag: Int

    /** The media type of the codec (e.g., audio, video). */
    public var codecType: MediaType

    /** The ID of the codec. */
    public var codecId: CodecID

    /** The average bitrate of the encoded output. */
    public var bitrate: Long

    /** The allowed variance in the bitrate. */
    public var bitRateTolerance: Int

    /** Codec-specific flags. */
    public var flags: Int

    /**
     * The fundamental time unit (in seconds) in terms of which frame timestamps are represented.
     */
    public var timeBase: Rational

    /**
     * The number of threads to be used for processing.
     * - For encoding, this is set by the user.
     * - For decoding, this is also set by the user.
     */
    public var threadCount: Int

    /** The threading mode for the codec context.
     */
    public var threadType: Int

    /**
     * A counter for the number of frames processed.
     */
    public var frameNum: Long

    /**
     * Indicates whether the codec context is open and ready for use.
     *
     * @return `true` if the codec context is open, `false` otherwise.
     */
    public val isOpen: Boolean

    /**
     * Initializes and opens the codec context.
     * This method must be called before any encoding or decoding operations can be performed.
     *
     * @param options A [Dictionary] of options to configure the codec.
     */
    public fun open(options: Dictionary? = null)

    /**
     * Flushes the internal buffers of the codec context.
     * This should be called when seeking or at the end of the stream to ensure all buffered
     * frames/packets are processed.
     */
    public fun flushBuffers()

    /**
     * Sends a [Frame] to the encoder for encoding.
     *
     * @param frame The frame to be encoded. A null frame flushes the encoder.
     */
    protected fun sendFrame(frame: Frame?)

    /**
     * Sends a [Packet] to the decoder for decoding.
     *
     * @param packet The packet to be decoded. A null packet flushes the decoder.
     */
    protected fun sendPacket(packet: Packet?)

    /**
     * Receives an encoded [Packet] from the encoder.
     *
     * @return The encoded packet, or null if no more packets are available.
     */
    protected fun receivePacket(): Packet?

    protected fun receivePackets(): List<Packet>

    /**
     * Receives a decoded [Frame] from the decoder.
     *
     * @return The decoded frame, or null if no more frames are available.
     */
    protected abstract fun decode(): Frame?

    /**
     * Closes the codec context and releases all associated resources.
     * This method must be called when the context is no longer needed to prevent memory leaks.
     */
    public override fun close()
}