package dev.zt64.ffmpegkt.gradle

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        setupAndroid(target)
        setupKmp(target)
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    private fun setupKmp(target: Project) {
        target.apply(plugin = "kotlin-multiplatform")

        target.configure<KotlinMultiplatformExtension> {
            jvmToolchain(17)

            applyDefaultHierarchyTemplate {
                common {
                    group("jvmCommon") {
                        withAndroidTarget()
                        withJvm()
                    }
                }
            }

            jvm()
            androidTarget()

            native()

            compilerOptions {
                freeCompilerArgs.addAll("-Xexpect-actual-classes")
            }

            sourceSets.apply {
            }
        }
    }

    private fun setupAndroid(target: Project) {
        target.apply(plugin = "android-library")

        target.configure<LibraryExtension> {
            namespace = "dev.zt64.ffmpegkt"
            compileSdk = 33
            defaultConfig {
                minSdk = 21
            }
        }
    }
}