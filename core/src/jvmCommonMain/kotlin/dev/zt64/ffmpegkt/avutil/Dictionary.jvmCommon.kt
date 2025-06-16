package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.avutil.AVDictionaryEntry
import org.bytedeco.ffmpeg.global.avutil.*

internal actual typealias NativeAVDictionary = org.bytedeco.ffmpeg.avutil.AVDictionary

public actual fun Dictionary(nativeDict: NativeAVDictionary): Dictionary {
    val dict: Dictionary = LinkedHashMap<String, String>(av_dict_count(nativeDict)).apply {
        var entry: AVDictionaryEntry? = null
        while (true) {
            entry = av_dict_iterate(nativeDict, entry) ?: break

            put(entry.key().string, entry.value().string)
        }
    }

    av_dict_free(nativeDict)

    return dict
}

public actual fun Dictionary.toNative(): NativeAVDictionary {
    val nativeDict = NativeAVDictionary()

    this.forEach {
        av_dict_set(nativeDict, it.key, it.value, 0)
    }

    return nativeDict
}