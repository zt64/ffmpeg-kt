package dev.zt64.ffmpegkt.avcodec

actual fun setup() {
    System.setProperty("org.bytedeco.javacpp.nopointergc", "true")
}