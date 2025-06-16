package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.Dictionary
import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.toNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import dev.zt64.ffmpegkt.codec.Packet
import ffmpeg.*
import kotlinx.cinterop.ptr

public actual class InputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
    public actual inline var startTime: Long
        get() = native.start_time
        set(value) {
            native.start_time = value
        }
    public actual inline var duration: Long
        get() = native.duration
        set(value) {
            native.duration = value
        }
    public actual inline var bitRate: Long
        get() = native.bit_rate
        set(value) {
            native.bit_rate = value
        }

    init {
        val options: Dictionary? = null
        avformat_find_stream_info(ctx, options?.toNative()).checkTrue()
    }

    public actual fun demux(): List<Packet> {
        val packets = mutableListOf<Packet>()
        while (true) {
            val packet = try {
                Packet().apply { av_read_frame(this@InputContainer.native, native) }
            } catch (e: Exception) {
                break
            }

            packets += packet
        }
        return packets
    }

    public actual fun decode(): List<Frame> {
        return demux().map { packet -> packet.decode() }
    }

    public actual fun seek(offset: Int) {
        val streamIndex: Int = -1 // TODO: Expose stream index as parameter?
        val flags = AVSEEK_FLAG_ANY or AVSEEK_FLAG_BACKWARD

        av_seek_frame(native.ptr, streamIndex, offset.toLong(), flags).checkError()
    }

    actual override fun close() {
        // avformat_close_input(native) // TODO: Why does this segfault?
    }
}