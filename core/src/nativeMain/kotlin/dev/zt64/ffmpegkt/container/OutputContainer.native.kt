package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.stream.Stream
import ffmpeg.avformat_alloc_output_context2
import ffmpeg.avio_context_free

public actual class OutputContainer(ctx: NativeAVFormatContext2) : Container(ctx) {
    public actual constructor(
        format: AVOutputFormat?,
        formatName: String?,
        filename: String
    ) : this(NativeAVFormatContext2()) {
        avformat_alloc_output_context2(native, format?.native, formatName, filename).checkError()
    }

    public actual fun addStream(stream: Stream) {
    }

    actual override fun close() {
        if (native.pb != null) avio_context_free(native.pb)

        TODO("Not yet implemented")
    }
}