import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    // Kotlin
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false

    // Android
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false

    // Maintenance
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.kover) apply false
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }

    configure<KtlintExtension> {
        version = rootProject.libs.versions.ktlint
        verbose = true
    }

    group = "dev.zt64.ffmpeg-kt"
    version = "1.0.0"
}