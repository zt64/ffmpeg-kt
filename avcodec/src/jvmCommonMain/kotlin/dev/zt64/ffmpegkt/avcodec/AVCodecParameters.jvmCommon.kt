package dev.zt64.ffmpegkt.avcodec

import dev.zt64.ffmpegkt.avutil.AVMediaType
import dev.zt64.ffmpegkt.avutil.util.checkError
import org.bytedeco.ffmpeg.global.avcodec.avcodec_parameters_copy

public actual class AVCodecParameters(
    internal val native: org.bytedeco.ffmpeg.avcodec.AVCodecParameters
) {
    public actual val codecType: AVMediaType = AVMediaType(native.codec_type())
    public actual val codecId: AVCodecID = AVCodecID(native.codec_id())
    public actual var codecTag: Int
        get() = native.codec_tag()
        set(value) {
            native.codec_tag(value)
        }

    public actual fun copyTo(target: AVCodecParameters) {
        avcodec_parameters_copy(target.native, native).checkError()
    }
}