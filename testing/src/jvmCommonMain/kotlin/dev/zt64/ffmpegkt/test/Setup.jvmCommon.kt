package dev.zt64.ffmpegkt.test

actual fun platformSetup() {
    System.setProperty("org.bytedeco.javacpp.nopointergc", "true")
}