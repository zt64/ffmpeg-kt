package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import ffmpeg.*
import kotlinx.cinterop.toKString

public actual object LibAVDevice : FfmpegLibrary {
    override fun version(): Int = avdevice_version().toInt()

    override fun configuration(): String = avdevice_configuration()?.toKString().orEmpty()

    override fun license(): String = avdevice_license()?.toKString().orEmpty()

    public actual fun registerAll() {
        avdevice_register_all()
    }

    public actual fun inputAudioDeviceNext(d: AVInputFormat): AVInputFormat {
        TODO("Not yet implemented")
    }

    public actual fun inputVideoDeviceNext(d: AVInputFormat): AVInputFormat {
        TODO("Not yet implemented")
    }

    public actual fun outputAudioDeviceNext(d: AVOutputFormat): AVOutputFormat {
        TODO("Not yet implemented")
    }

    public actual fun outputVideoDeviceNext(d: AVOutputFormat): AVOutputFormat {
        TODO("Not yet implemented")
    }
}