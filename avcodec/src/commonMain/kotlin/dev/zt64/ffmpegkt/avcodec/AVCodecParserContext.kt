package dev.zt64.ffmpegkt.avcodec

public expect class AVCodecParserContext : AutoCloseable

public expect val AVCodecParserContext.parser: AVCodecParser

public expect val AVCodecParserContext.frameOffset: Long

public expect fun AVCodecParserContext(codec: AVCodecID): AVCodecParserContext