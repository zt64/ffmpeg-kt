package dev.zt64.ffmpegkt.avutil.hw

public expect class HWFrameContext {
    public var width: Int
    public var height: Int
    public var format: Int
    public var swFormat: Int
}