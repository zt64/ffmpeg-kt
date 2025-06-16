package dev.zt64.ffmpegkt

// see: https://stackoverflow.com/a/71578372
public interface RawRepresentable<T> {
    public val rawValue: T
}

public inline fun <reified E, T> valueOf(value: T): E where E : Enum<E>, E : RawRepresentable<T> {
    return enumValues<E>().first { it.rawValue == value }
}