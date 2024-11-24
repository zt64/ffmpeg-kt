package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_dict_set
import kotlinx.cinterop.*

public actual typealias AVDictionaryNative = ffmpeg.AVDictionary

public actual fun AVDictionaryNative(dict: AVDictionary): AVDictionaryNative {
    return nativeHeap.alloc<AVDictionaryNative> {
        for ((key, value) in dict) {
            av_dict_set(cValuesOf(ptr), key, value, 0)
        }
    }
}

public actual fun AVDictionary(nativeDict: AVDictionaryNative): AVDictionary {
    return buildMap {
        repeat(nativeDict.count) {
            val entry = nativeDict.elems!![it]
            put(entry.key!!.toKString(), entry.value!!.toKString())
        }
    }
}