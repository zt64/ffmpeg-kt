plugins {
    id("kmp-base")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.avutil)
                api(projects.avcodec)
                api(projects.avdevice)
                api(projects.avfilter)
                api(projects.avformat)
                api(projects.postproc)
                api(projects.swresample)
                api(projects.swscale)
            }
        }
    }
}