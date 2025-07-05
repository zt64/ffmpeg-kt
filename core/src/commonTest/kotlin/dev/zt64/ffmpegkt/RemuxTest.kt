package dev.zt64.ffmpegkt

import dev.zt64.ffmpegkt.avutil.LibAVUtil
import dev.zt64.ffmpegkt.avutil.LogLevel
import dev.zt64.ffmpegkt.avutil.logging
import dev.zt64.ffmpegkt.container.Container
import dev.zt64.ffmpegkt.test.TestResources
import dev.zt64.ffmpegkt.test.TestUtil
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class RemuxTest {
    /**
     * Remuxes a video file to another container format.
     * This is a simple example of using the libavformat API to remux a media file from one container format to another.
     *
     */
    @Test
    fun testRemux() = runTest {
        LibAVUtil.logging.level = LogLevel.VERBOSE

        val outputPath = TestUtil.resolvePath("remuxed.mp4")

        val input = Container.openInput(TestResources.RESOURCE_1.readBytes())
        val output = Container.openOutput("somefile.mp4")
    }
}