package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avformat.AVInputFormat
import dev.zt64.ffmpegkt.avformat.AVOutputFormat
import org.bytedeco.ffmpeg.global.avdevice.*

public actual object LibAVDevice : FfmpegLibrary {
    override fun version(): Int = avdevice_version()

    override fun configuration(): String = avdevice_configuration().string

    override fun license(): String = avdevice_license().string

    public actual fun registerAll(): Unit = avdevice_register_all()

    public actual fun inputAudioDeviceNext(d: AVInputFormat): AVInputFormat {
        return av_input_audio_device_next(d)
    }

    public actual fun inputVideoDeviceNext(d: AVInputFormat): AVInputFormat {
        return av_input_video_device_next(d)
    }

    public actual fun outputAudioDeviceNext(d: AVOutputFormat): AVOutputFormat {
        return AVOutputFormat(av_output_audio_device_next(d.native))
    }

    public actual fun outputVideoDeviceNext(d: AVOutputFormat): AVOutputFormat {
        return AVOutputFormat(av_output_video_device_next(d.native))
    }
}