package dev.zt64.ffmpegkt.avutil.hw

import ffmpeg.AVHWFramesContext

public actual class HWFrameContext(
    public val native: AVHWFramesContext
) {
    public actual var width: Int
        get() = native.width
        set(value) {
            native.width = value
        }

    public actual var height: Int
        get() = native.height
        set(value) {
            native.height = value
        }

    public actual var format: Int
        get() = native.format
        set(value) {
            native.format = value
        }

    public actual var swFormat: Int
        get() = native.sw_format
        set(value) {
            native.sw_format = value
        }
}