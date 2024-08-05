package dev.zt64.ffmpegkt

interface FfmpegLibrary {
    fun version(): Int

    fun configuration(): String

    fun license(): String
}