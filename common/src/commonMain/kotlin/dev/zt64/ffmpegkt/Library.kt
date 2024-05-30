package dev.zt64.ffmpegkt

interface Library {
    fun version(): Int

    fun configuration(): String

    fun license(): String
}