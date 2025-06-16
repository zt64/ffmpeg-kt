import dev.zt64.ffmpegkt.gradle.native

plugins {
    id("kmp-base")
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvmToolchain(17)

    jvm()
    native()
}