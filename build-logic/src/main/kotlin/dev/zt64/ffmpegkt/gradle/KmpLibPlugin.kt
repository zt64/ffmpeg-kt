package dev.zt64.ffmpegkt.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

internal fun Project.versionCatalog(): VersionCatalog {
    return extensions.getByType<VersionCatalogsExtension>().named("libs")
}

class KmpLibPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        setupKmp(target)
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    private fun setupKmp(target: Project) {
        target.apply(plugin = "kmp-base")

        target.configure<KotlinMultiplatformExtension> {
            explicitApi()

            native {
                compilations.getByName("main") {
                    cinterops.create("ffmpeg")
                }
            }

            sourceSets.apply {
                commonMain {
                    dependencies {
                        if (target.name != "core") {
                            api(project(":core"))
                        }
                    }
                }
            }
        }

        target.tasks.withType<KotlinNativeCompile>().configureEach {
            compilerOptions {
                optIn.add("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
}