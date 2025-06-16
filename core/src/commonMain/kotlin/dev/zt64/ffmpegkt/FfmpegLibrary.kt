package dev.zt64.ffmpegkt

public interface FfmpegLibrary {
    public fun version(): Int

    public fun configuration(): String

    public fun license(): String
}