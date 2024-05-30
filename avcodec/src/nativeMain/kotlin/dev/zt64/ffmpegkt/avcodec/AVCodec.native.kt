package dev.zt64.ffmpegkt.avcodec

import kotlinx.cinterop.CPointerVar

@Suppress(
    "ACTUAL_TYPE_ALIAS_NOT_TO_CLASS",
    "ACTUAL_WITHOUT_EXPECT",
    "ACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION",
    "INCOMPATIBLE_TYPES"
)
public actual typealias AVCodec = CPointerVar<ffmpeg.AVCodec>