package dev.zt64.ffmpegkt.avformat

public object AVFMT {
    public const val NO_FILE: Int = 0x0001
    public const val NEED_NUMBER: Int = 0x0002
    public const val EXPERIMENTAL: Int = 0x0004
    public const val SHOW_IDS: Int = 0x0008
    public const val GLOBAL_HEADER: Int = 0x0040
    public const val NO_TIMESTAMPS: Int = 0x0080
    public const val GENERIC_INDEX: Int = 0x0100
    public const val TS_DISCONT: Int = 0x0200
    public const val VARIABLE_FPS: Int = 0x0400
    public const val NODIMENSIONS: Int = 0x0800
    public const val NOSTREAMS: Int = 0x1000
    public const val NOBINSEARCH: Int = 0x2000
    public const val NOGENSEARCH: Int = 0x4000
    public const val NOBYTESEEK: Int = 0x8000
    public const val TS_NONSTRICT: Int = 0x20000
    public const val TS_NEGATIVE: Int = 0x40000
    public const val SEEK_TO_PTS: Int = 0x4000000
}