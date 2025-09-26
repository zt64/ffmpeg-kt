package dev.zt64.ffmpegkt.gradle

import org.gradle.api.provider.Property

fun <T : Any> Property<T>.assign(value: T) = set(value)