package dev.zt64.ffmpegkt.avdevice

import kotlin.test.Test

class AVDeviceTest {
    @Test
    fun test() {
        println("Audio input devices:")
        println(LibAVDevice.audioInputDevices.ifEmpty { "None" })
        println("Audio output devices:")
        println(LibAVDevice.audioOutputDevices.ifEmpty { "None" })
        println("Video input devices:")
        println(LibAVDevice.videoInputDevices.ifEmpty { "None" })
        println("Video output devices:")
        println(LibAVDevice.videoOutputDevices.ifEmpty { "None" })
    }
}