package dev.zt64.ffmpegkt.avfilter

import kotlin.test.Test

// https://www.ffmpeg.org/doxygen/trunk/filter_audio_8c-example.html
class FilterAudioTest {
    @Test
    fun filterAudio() {
        val graph = FilterGraph2D()

        println(graph.threads)
        graph.use {
            println(graph.threads)
        }

        println(graph.threads)
        println(graph.close())

        println(graph.threads)
    }
}