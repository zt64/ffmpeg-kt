package dev.zt64.ffmpegkt.container

import dev.zt64.ffmpegkt.avutil.toNative
import dev.zt64.ffmpegkt.avutil.util.checkError
import dev.zt64.ffmpegkt.codec.Codec
import dev.zt64.ffmpegkt.codec.Packet
import dev.zt64.ffmpegkt.stream.Stream
import ffmpeg.*
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

public actual class OutputContainer(native: NativeAVFormatContext2) : Container(native) {
    private val _metadata = mutableMapOf<String, String>()

    public actual override val metadata: MutableMap<String, String> = object : MutableMap<String, String> by _metadata {
        override fun put(key: String, value: String): String? {
            val previous = _metadata.put(key, value)
            updateNativeMetadata()
            return previous
        }

        override fun putAll(from: Map<out String, String>) {
            _metadata.putAll(from)
            updateNativeMetadata()
        }

        override fun remove(key: String): String? {
            val previous = _metadata.remove(key)
            updateNativeMetadata()
            return previous
        }

        override fun clear() {
            _metadata.clear()
            updateNativeMetadata()
        }
    }
    private fun updateNativeMetadata() {
        // Free any existing metadata dictionary
        if (native.metadata != null) {
            av_dict_free(cValuesOf(native.metadata))
        }

        // Create a new dictionary from current metadata
        if (_metadata.isNotEmpty()) {
            val dict = _metadata.toNative()
            native.metadata = dict.ptr
        } else {
            native.metadata = null
        }
    }

    public actual constructor(
        format: AVOutputFormat?,
        formatName: String?,
        filename: String
    ) : this(avformat_alloc_context()!!.pointed) {
        avformat_alloc_output_context2(
            ctx = cValuesOf(native.ptr),
            oformat = format?.native?.ptr,
            format_name = formatName,
            filename = filename
        ).checkError()
    }

    public actual fun <T : Stream> addStream(stream: T): T {
        TODO()
    }

    public actual inline fun <reified T : Stream> newStream(codec: Codec, streamIndex: Int): T {
        TODO()
    }

    public actual fun writeHeader() {
        avformat_write_header(native.ptr, null).checkError()
    }

    public actual fun mux(packet: Packet) {
    }

    public actual fun mux(packets: List<Packet>) {
    }

    public actual override fun close() {
        if (native.pb != null) avio_context_free(cValuesOf(native.pb))

        TODO("Not yet implemented")
    }
}