plugins {
    id("kmp-base")
}

kotlin {
    sourceSets {
        jvmCommonMain {
            dependencies {
                api(libs.ffmpeg)
            }
        }
    }
}

description = "Common utilities for ffmpeg-kt"