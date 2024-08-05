package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avcodec.AVCodecID
import dev.zt64.ffmpegkt.avutil.AVClass

internal typealias NativeAVOutputFormat = org.bytedeco.ffmpeg.avformat.AVOutputFormat

public actual class AVOutputFormat(public val native: NativeAVOutputFormat) {
    public actual val name: String = native.name().string
    public actual val longName: String = native.long_name().string
    public actual val mimeType: String = native.mime_type().string
    public actual val extensions: String = native.extensions().string
    public actual val audioCodec: AVCodecID = AVCodecID(native.audio_codec())
    public actual val videoCodec: AVCodecID = AVCodecID(native.video_codec())
    public actual val subtitleCodec: AVCodecID = AVCodecID(native.subtitle_codec())
    public actual val flags: Int = native.flags()
    public actual val codecTag: List<AVCodecTag> = emptyList()
    public actual val privClass: AVClass = AVClass(native.priv_class())
}