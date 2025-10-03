package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.container.InputFormat
import dev.zt64.ffmpegkt.container.OutputFormat

public expect object LibAVDevice : FfmpegLibrary {
    public val audioInputDevices: List<InputFormat>
    public val audioOutputDevices: List<OutputFormat>
    public val videoInputDevices: List<InputFormat>
    public val videoOutputDevices: List<OutputFormat>

    public override fun version(): Int
    public override fun configuration(): String
    public override fun license(): String
}