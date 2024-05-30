package dev.zt64.ffmpegkt.gradle

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

fun KotlinMultiplatformExtension.native(configure: KotlinNativeTarget.() -> Unit = {}) {
    val os = System.getProperty("os.name").lowercase()
    val targets = when {
        os.contains("win") -> listOf(mingwX64())

        os.contains("linux") -> listOf(
            linuxX64(),
            linuxArm64()
        )

        os.contains("mac") -> listOf(
            macosX64(),
            macosArm64(),
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        )

        else -> throw IllegalStateException("Unsupported OS: $os")
    }

    targets.forEach(configure)
}