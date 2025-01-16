plugins {
    id("kmp-lib")
    // alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        commonMain {
            // kotlin.srcDir("build/generated/ksp/commonMain/kotlin")

            dependencies {
                api(projects.avcodec)
                // compileOnly(projects.ksp.annotations)
            }
        }
    }
}

// tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
//     if (name != "kspCommonMainKotlinMetadata") {
//         dependsOn("kspCommonMainKotlinMetadata")
//     }
// }
//
// dependencies {
//     "kspCommonMainMetadata"(projects.ksp.kspProcessor)
//     "kspJvm"(projects.ksp.kspProcessor)
// }