package dev.zt64.ffmpegkt.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
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
                        // withAndroidTarget()
                        withJvm()
                    }
                }
            }

            jvm()

            native()

            compilerOptions {
                freeCompilerArgs.addAll("-Xexpect-actual-classes")
            }

            sourceSets.apply {
            }
        }
    }
}