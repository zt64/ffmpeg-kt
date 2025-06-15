package dev.zt64.ffmpegkt.avutil

import ffmpeg.av_dict_set
import kotlinx.cinterop.*

internal actual typealias NativeAVDictionary = ffmpeg.AVDictionary

public actual fun Dictionary(nativeDict: NativeAVDictionary): Dictionary {
    return buildMap {
        repeat(nativeDict.count) {
            val entry = nativeDict.elems!![it]
            put(entry.key!!.toKString(), entry.value!!.toKString())
        }
    }
}

public actual fun Dictionary.toNative(): NativeAVDictionary {
    return nativeHeap.alloc<NativeAVDictionary> {
        for ((key, value) in this@toNative) {
            av_dict_set(cValuesOf(ptr), key, value, 0)
        }
    }
}