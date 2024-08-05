package dev.zt64.ffmpegkt.avformat

import dev.zt64.ffmpegkt.avutil.AVClass
import dev.zt64.ffmpegkt.avutil.AVDictionary

public expect class AVStreamGroup {
    public val avClass: AVClass
    public val index: UInt
    public val id: Long
    public val type: AVStreamGroupParamsType
    public val metadata: AVDictionary
    public val streams: List<AVStream>
    public val disposition: Int
}

public enum class AVStreamGroupParamsType {
    NONE,
    IAMF_AUDIO_ELEMENT,
    IAMF_MIX_PRESENTATION,
    TILE_GRID
}