plugins {
    id("kmp-base")
    alias(libs.plugins.ktx.resources)
}

description = "Module containing common test code"

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core)
                api(libs.kotlin.test)
                api(libs.coroutines.test)
                api(libs.resources.test)
                api(libs.okio)
                api(libs.okio.fakefilesystem)
            }
        }
    }
}