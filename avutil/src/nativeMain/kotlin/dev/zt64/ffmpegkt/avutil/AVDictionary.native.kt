package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_dict_set
import kotlinx.cinterop.*

public actual typealias AVDictionaryNative = ffmpeg.AVDictionary

public actual fun AVDictionaryNative(dict: AVDictionary): AVDictionaryNative {
    val nativeDict = nativeHeap.alloc<AVDictionaryNative>()

    for ((key, value) in dict) {
        av_dict_set(cValuesOf(nativeDict.ptr), key, value, 0)
    }

    return nativeDict
}

public actual fun AVDictionary(nativeDict: AVDictionaryNative): AVDictionary {
    return buildMap {
        repeat(nativeDict.count) {
            val entry = nativeDict.elems!![it]
            put(entry.key!!.toKString(), entry.value!!.toKString())
        }
    }
}