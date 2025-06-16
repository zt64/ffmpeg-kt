package dev.zt64.ffmpegkt.avfilter

import kotlin.test.Test

// https://www.ffmpeg.org/doxygen/trunk/filter_audio_8c-example.html
class FilterAudioTest {
    @Test
    fun filterAudio() {
        /**
         *                 [main]
         * input --> split ---------------------> overlay --> output
         *             |                             ^
         *             |[tmp]                  [flip]|
         *             +-----> crop --> vflip -------+
         *
         */
        val graph = FilterGraph {
            filter("split") {
                val tmp = output {
                    filter("crop") {
                        option("out_w", "iw")
                        option("out_h", "ih/2")
                        option("x", 0)
                        option("y", 0)
                    }

                    filter("vflip")
                }

                output {
                    filter("overlay", tmp)
                }
            }
        }

        graph.close()
    }
}