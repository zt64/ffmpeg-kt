package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.container.AVInputFormat
import dev.zt64.ffmpegkt.container.AVOutputFormat
import ffmpeg.avdevice_configuration
import ffmpeg.avdevice_license
import ffmpeg.avdevice_version
import kotlinx.cinterop.toKString

public actual object LibAVDevice : FfmpegLibrary {
    public actual val audioInputDevices: List<AVInputFormat>
        get() = TODO()
    public actual val audioOutputDevices: List<AVOutputFormat>
        get() = TODO()
    public actual val videoInputDevices: List<AVInputFormat>
        get() = TODO()
    public actual val videoOutputDevices: List<AVOutputFormat>
        get() = TODO()

    public actual override fun version(): Int = avdevice_version().toInt()
    public actual override fun configuration(): String = avdevice_configuration()?.toKString().orEmpty()
    public actual override fun license(): String = avdevice_license()?.toKString().orEmpty()
}