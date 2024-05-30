plugins {
    id("kmp-base")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.avcodec)
                api(projects.avformat)
                api(projects.avfilter)
                api(projects.avutil)
                api(projects.postproc)
                api(projects.swresample)
                api(projects.swscale)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}