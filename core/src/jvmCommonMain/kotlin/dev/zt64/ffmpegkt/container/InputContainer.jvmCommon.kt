package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.Frame
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.avutil.util.checkTrue
import dev.zt64.ffmpegkt.codec.Packet
import org.bytedeco.ffmpeg.avcodec.AVPacket
import org.bytedeco.ffmpeg.avutil.AVDictionary
import org.bytedeco.ffmpeg.global.avformat.*

public actual class InputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
    public actual inline val startTime: Long
        get() = native.start_time()
    public actual inline val duration: Long
        get() = native.duration()
    public actual inline val bitRate: Long
        get() = native.bit_rate()

    init {
        val options: AVDictionary? = null
        avformat_find_stream_info(ctx, options).checkTrue()
    }

    public actual fun demux(): List<Packet> {
        return buildList {
            while (true) {
                try {
                    val packet = Packet(AVPacket().apply { av_read_frame(this@InputContainer.native, this) })
                    add(packet)
                } catch (_: Exception) {
                    break
                }
            }
        }
    }

    public actual fun decode(): List<Frame> {
        return demux().map { packet -> packet.decode() }
    }

    public actual fun seek(offset: Int) {
        val streamIndex: Int = -1 // TODO: Expose stream index as parameter?
        val flags = AVSEEK_FLAG_ANY or AVSEEK_FLAG_BACKWARD

        av_seek_frame(native, streamIndex, offset.toLong(), flags).checkError()
    }

    actual override fun close() {
        // avformat_close_input(native) // TODO: Why does this segfault?
    }
}