package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avformat.AVInputFormat
import dev.zt64.ffmpegkt.avformat.AVOutputFormat
import org.bytedeco.ffmpeg.global.avdevice.*

public actual object LibAVDevice : FfmpegLibrary {
    public actual val audioInputDevices: List<AVInputFormat>
        get() {
            return buildList {
                var device = av_input_audio_device_next(lastOrNull()?.native)
                while (device != null) {
                    add(AVInputFormat(device))
                    device = av_input_audio_device_next(device)
                }
            }
        }
    public actual val audioOutputDevices: List<AVOutputFormat>
        get() {
            return buildList {
                var device = av_output_audio_device_next(lastOrNull()?.native)
                while (device != null) {
                    add(AVOutputFormat(device))
                    device = av_output_audio_device_next(device)
                }
            }
        }
    public actual val videoInputDevices: List<AVInputFormat>
        get() {
            return buildList {
                var device = av_input_video_device_next(lastOrNull()?.native)
                while (device != null) {
                    add(AVInputFormat(device))
                    device = av_input_video_device_next(device)
                }
            }
        }
    public actual val videoOutputDevices: List<AVOutputFormat>
        get() {
            return buildList {
                var device = av_output_video_device_next(lastOrNull()?.native)
                while (device != null) {
                    add(AVOutputFormat(device))
                    device = av_output_video_device_next(device)
                }
            }
        }

    override fun version(): Int = avdevice_version()

    override fun configuration(): String = avdevice_configuration().string

    override fun license(): String = avdevice_license().string
}