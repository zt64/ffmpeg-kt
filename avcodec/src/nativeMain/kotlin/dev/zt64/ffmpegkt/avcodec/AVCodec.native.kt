package dev.zt64.ffmpegkt.avcodec

import kotlinx.cinterop.CPointerVar

public actual typealias NativeAVCodec = ffmpeg.AVCodec

@Suppress(
    "ACTUAL_TYPE_ALIAS_NOT_TO_CLASS",
    "ACTUAL_WITHOUT_EXPECT",
    "ACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION",
    "INCOMPATIBLE_TYPES"
)
public actual class AVCodec(public val ptr: CPointerVar<ffmpeg.AVCodec>)