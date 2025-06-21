plugins {
    id("kmp-lib")
}

description = "FFmpeg-kt core functionality, including the FFmpeg API and common data structures"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(libs.coroutines.core)
            }
        }

        jvmCommonMain {
            dependencies {
                api(libs.ffmpeg)
            }
        }
    }
}