package dev.zt64.ffmpegkt

actual fun setProperty() {
    System.setProperty("org.bytedeco.javacpp.nopointergc", "true")
}