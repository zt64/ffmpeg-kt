package dev.zt64.ffmpegkt

// see: https://stackoverflow.com/a/71578372
interface RawRepresentable<T> {
    val rawValue: T
}

inline fun <reified E, T> valueOf(value: T): E where E : Enum<E>, E : RawRepresentable<T> {
    return enumValues<E>().first { it.rawValue == value }
}