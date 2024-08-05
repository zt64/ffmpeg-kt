package dev.zt64.ffmpegkt.avdevice

import dev.zt64.ffmpegkt.FfmpegLibrary
import dev.zt64.ffmpegkt.avformat.AVInputFormat
import dev.zt64.ffmpegkt.avformat.AVOutputFormat

public expect object AVDevice : FfmpegLibrary {
    public fun registerAll()

    public fun inputAudioDeviceNext(d: AVInputFormat): AVInputFormat

    public fun inputVideoDeviceNext(d: AVInputFormat): AVInputFormat

    public fun outputAudioDeviceNext(d: AVOutputFormat): AVOutputFormat

    public fun outputVideoDeviceNext(d: AVOutputFormat): AVOutputFormat
}