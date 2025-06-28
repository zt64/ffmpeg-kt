package dev.zt64.ffmpegkt

import org.bytedeco.javacpp.IntPointer
import org.bytedeco.javacpp.Pointer

@PublishedApi
internal inline fun <T : Pointer, R> T?.toList(crossinline getValue: (pointer: T, index: Long) -> R?): List<R> {
    if (this == null) return emptyList()
    return buildList {
        var index = 0L
        while (true) {
            val value = getValue(this@toList, index) ?: break
            add(value)
            index++
        }
    }
}

@PublishedApi
internal inline fun <reified R : Any> IntPointer?.toList(terminator: Int, crossinline transform: (Int) -> R = { it as R }): List<R> {
    if (this == null) return emptyList()
    return buildList {
        var i = 0L
        while (true) {
            val value = get(i)
            if (value == terminator) break
            add(transform(value))
            i++
        }
    }
}

@PublishedApi
internal inline fun <T : Pointer, R> T?.toList(crossinline isTerminator: (T) -> Boolean, crossinline transform: (T) -> R): List<R> {
    if (this == null) return emptyList()
    return buildList {
        var i = 0L
        while (true) {
            val value = getPointer(i) as T
            if (isTerminator(value)) break
            add(transform(value))
            i++
        }
    }
}