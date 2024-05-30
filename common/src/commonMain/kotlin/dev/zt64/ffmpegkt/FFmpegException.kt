package dev.zt64.ffmpegkt

public class FFmpegException(code: Int) : Exception() {
    public override val message: String = "FFmpeg error: $code"
}