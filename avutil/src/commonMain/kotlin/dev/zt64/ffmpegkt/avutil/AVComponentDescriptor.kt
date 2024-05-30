package dev.zt64.ffmpegkt.avutil

public data class AVComponentDescriptor(
    val plane: Int,
    val step: Int,
    val offset: Int,
    val shift: Int,
    val depth: Int
)