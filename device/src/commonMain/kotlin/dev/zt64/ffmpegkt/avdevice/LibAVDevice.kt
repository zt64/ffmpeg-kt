package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.container.AVInputFormat
import dev.zt64.ffmpegkt.container.AVOutputFormat

public expect object LibAVDevice : FfmpegLibrary {
    public val audioInputDevices: List<AVInputFormat>
    public val audioOutputDevices: List<AVOutputFormat>
    public val videoInputDevices: List<AVInputFormat>
    public val videoOutputDevices: List<AVOutputFormat>

    public override fun version(): Int
    public override fun configuration(): String
    public override fun license(): String
}