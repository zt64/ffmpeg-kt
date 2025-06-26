package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.container.AVInputFormat
import dev.zt64.ffmpegkt.container.AVOutputFormat
import org.bytedeco.ffmpeg.global.avdevice.*
import org.bytedeco.javacpp.Pointer

public actual object LibAVDevice : FfmpegLibrary {
    public actual val audioInputDevices: List<AVInputFormat>
        get() = buildDeviceList(::av_input_audio_device_next, ::AVInputFormat)

    public actual val audioOutputDevices: List<AVOutputFormat>
        get() = buildDeviceList(::av_output_audio_device_next, ::AVOutputFormat)

    public actual val videoInputDevices: List<AVInputFormat>
        get() = buildDeviceList(::av_input_video_device_next, ::AVInputFormat)

    public actual val videoOutputDevices: List<AVOutputFormat>
        get() = buildDeviceList(::av_output_video_device_next, ::AVOutputFormat)

    public actual override fun version(): Int = avdevice_version()
    public actual override fun configuration(): String = avdevice_configuration().string
    public actual override fun license(): String = avdevice_license().string

    private inline fun <P : Pointer, W> buildDeviceList(
        crossinline next: (prev: P?) -> P?,
        crossinline wrap: (pointer: P) -> W
    ): List<W> = buildList {
        var current = next(null)
        while (current != null) {
            add(wrap(current))
            current = next(current)
        }
    }
}