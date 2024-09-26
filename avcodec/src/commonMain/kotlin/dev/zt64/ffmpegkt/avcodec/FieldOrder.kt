package dev.zt64.ffmpegkt.avcodec

public enum class FieldOrder {
    AV_FIELD_UNKNOWN,
    AV_FIELD_PROGRESSIVE,

    // Top coded_first, top displayed first
    AV_FIELD_TT,

    // Bottom coded first, bottom displayed first
    AV_FIELD_BB,

    // Top coded first, bottom displayed first
    AV_FIELD_TB,

    // Bottom coded first, top displayed first
    AV_FIELD_BT
}