package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.container.InputFormat
import dev.zt64.ffmpegkt.container.OutputFormat
import org.bytedeco.ffmpeg.global.avdevice.*
import org.bytedeco.javacpp.Pointer

public actual object LibAVDevice : FfmpegLibrary {
    public actual val audioInputDevices: List<InputFormat>
        get() = buildDeviceList(::av_input_audio_device_next, ::InputFormat)

    public actual val audioOutputDevices: List<OutputFormat>
        get() = buildDeviceList(::av_output_audio_device_next, ::OutputFormat)

    public actual val videoInputDevices: List<InputFormat>
        get() = buildDeviceList(::av_input_video_device_next, ::InputFormat)

    public actual val videoOutputDevices: List<OutputFormat>
        get() = buildDeviceList(::av_output_video_device_next, ::OutputFormat)

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