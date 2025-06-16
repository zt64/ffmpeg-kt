package dev.zt64.ffmpegkt.ksp.annotation

@Target(AnnotationTarget.CLASS)
annotation class GenerateActual

enum class Platform {
    JVM,
    NATIVE
}