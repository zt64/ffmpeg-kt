package dev.zt64.ffmpegkt.test

import com.goncalossilva.resources.Resource

object TestResources {
    private const val BASE_PATH = "../testing/src/commonMain/resources"

    val RESOURCE_1 = Resource("$BASE_PATH/video/SampleVideo_1280x720_5mb.mp4")
    val MPEG_1_VIDEO = Resource("$BASE_PATH/video/sample_640x360.mpeg")
    val WAV_AUDIO = Resource("$BASE_PATH/audio/Dreaming-of-you_fma-145627_001_00-00-01.mp2")
}