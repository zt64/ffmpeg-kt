package dev.zt64.ffmpegkt.avcodec

import org.bytedeco.ffmpeg.global.avcodec.av_parser_init

public actual typealias AVCodecParserContext = org.bytedeco.ffmpeg.avcodec.AVCodecParserContext

public actual inline val AVCodecParserContext.parser: AVCodecParser
    get() = org.bytedeco.ffmpeg.avcodec.AVCodecParser(parser())

public actual inline val AVCodecParserContext.frameOffset: Long
    get() = frame_offset()

public actual fun AVCodecParserContext(codec: AVCodecID): AVCodecParserContext {
    return av_parser_init(codec.num)
}