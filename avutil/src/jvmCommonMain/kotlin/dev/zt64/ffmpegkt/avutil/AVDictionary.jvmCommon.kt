package dev.zt64.ffmpegkt.avutil

import org.bytedeco.ffmpeg.avutil.AVDictionaryEntry
import org.bytedeco.ffmpeg.global.avutil.*

public actual typealias AVDictionaryNative = org.bytedeco.ffmpeg.avutil.AVDictionary

public actual fun AVDictionaryNative(dict: AVDictionary): AVDictionaryNative {
    val nativeDict = AVDictionaryNative()

    dict.forEach {
        av_dict_set(nativeDict, it.key, it.value, 0)
    }

    return nativeDict
}

public actual fun AVDictionary(nativeDict: AVDictionaryNative): AVDictionary {
    val dict: AVDictionary = LinkedHashMap<String, String>(av_dict_count(nativeDict)).apply {
        var entry: AVDictionaryEntry? = null
        while (true) {
            entry = av_dict_iterate(nativeDict, entry) ?: break

            put(entry.tkey, entry.tvalue)
        }
    }

    av_dict_free(nativeDict)

    return dict
}