plugins {
    `kotlin-dsl`
}

fun DependencyHandler.implementation(dependency: Provider<PluginDependency>) {
    implementation(dependency.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" })
}

fun DependencyHandler.compileOnly(dependency: Provider<PluginDependency>) {
    compileOnly(dependency.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" })
}

dependencies {
    compileOnly(libs.plugins.kotlin.multiplatform)
    compileOnly(libs.plugins.android.library)
    implementation(libs.plugins.publish)
}

gradlePlugin {
    plugins {
        register("kmp-base") {
            id = "kmp-base"
            implementationClass = "dev.zt64.ffmpegkt.gradle.KmpBasePlugin"
        }

        register("kmp-lib") {
            id = "kmp-lib"
            implementationClass = "dev.zt64.ffmpegkt.gradle.KmpLibPlugin"
        }
    }
}