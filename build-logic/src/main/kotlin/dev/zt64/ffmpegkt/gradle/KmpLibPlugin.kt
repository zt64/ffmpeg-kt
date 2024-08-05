package dev.zt64.ffmpegkt.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

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
                        api(project(":common"))

                        if (target.name != "avutil") {
                            implementation(project(":avutil"))
                        }
                    }
                }

                commonTest {
                    dependencies {
                        implementation("org.jetbrains.kotlin:kotlin-test")
                    }
                }

                named("jvmCommonMain") {
                    dependencies {
                        // implementation(target.versionCatalog().findLibrary("ffmpeg").get())
                    }
                }
            }

            compilerOptions {
                optIn.add("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
}