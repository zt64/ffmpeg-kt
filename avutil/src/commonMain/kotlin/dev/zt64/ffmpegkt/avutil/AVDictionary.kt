package dev.zt64.ffmpegkt.avutil

public expect class AVDictionary

public fun AVDictionary.iterate(prev: AVDictionaryEntry? = null): AVDictionaryEntry? {
    return AVUtil.dictIterate(this, prev)
}

public expect fun AVDictionary.count(): Int

public expect operator fun AVDictionary.get(key: String, flags: Int = 0): String?

public expect fun AVDictionary.set(
    key: String,
    value: String,
    flags: Int
)

public expect fun AVDictionary.set(
    key: String,
    value: Long,
    flags: Int
)

public operator fun AVDictionary.set(key: String, value: String) {
    set(key, value, 0)
}

public operator fun AVDictionary.set(key: String, value: Long) {
    set(key, value, 0)
}