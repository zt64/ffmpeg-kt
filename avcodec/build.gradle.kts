import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kmp-lib")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        val friendModule = projects.avutil.dependencyProject
        val jarPath = friendModule.configurations["jvmRuntimeElements"].artifacts.first().file.path
        freeCompilerArgs.add("-Xfriend-paths=$jarPath")
    }
}