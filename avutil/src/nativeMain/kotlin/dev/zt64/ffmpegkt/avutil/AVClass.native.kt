package dev.zt64.ffmpegkt.avutil

import ffmpeg.AVClass
import kotlinx.cinterop.CPointer

public actual class AVClass(internal val native: CPointer<AVClass>)