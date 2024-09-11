package dev.zt64.ffmpegkt.avcodec

import kotlin.test.BeforeTest

class Setup {
    @BeforeTest
    fun setup() {
        platformSetup()
    }
}

expect fun platformSetup()