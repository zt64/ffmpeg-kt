package dev.zt64.ffmpegkt.avutil

public data class PixelFormatDescriptor(
    val name: String,
    val components: Int,
    val log2_chroma_w: Int,
    val log2_chroma_h: Int,
    val flags: Int,
    val comp: List<ComponentDescriptor>
)