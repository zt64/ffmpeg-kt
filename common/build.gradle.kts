plugins {
    id("kmp-base")
}

description = "Common utilities for ffmpeg-kt"

kotlin {
    sourceSets {
        jvmCommonMain {
            dependencies {
                api(libs.ffmpeg)
            }
        }
    }
}