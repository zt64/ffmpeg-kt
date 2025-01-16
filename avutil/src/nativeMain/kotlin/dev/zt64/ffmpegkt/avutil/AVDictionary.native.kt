package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_dict_set
import kotlinx.cinterop.*

internal actual typealias AVDictionaryNative = ffmpeg.AVDictionary

internal actual fun AVDictionary(nativeDict: AVDictionaryNative): AVDictionary {
    return buildMap {
        repeat(nativeDict.count) {
            val entry = nativeDict.elems!![it]
            put(entry.key!!.toKString(), entry.value!!.toKString())
        }
    }
}

internal actual fun AVDictionary.toNative(): AVDictionaryNative {
    return nativeHeap.alloc<AVDictionaryNative> {
        for ((key, value) in this@toNative) {
            av_dict_set(cValuesOf(ptr), key, value, 0)
        }
    }
}