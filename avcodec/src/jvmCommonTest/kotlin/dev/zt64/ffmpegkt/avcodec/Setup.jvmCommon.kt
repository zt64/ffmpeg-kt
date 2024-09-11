package dev.zt64.ffmpegkt.avcodec

actual fun platformSetup() {
    System.setProperty("org.bytedeco.javacpp.nopointergc", "true")
}