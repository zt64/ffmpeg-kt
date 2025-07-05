package dev.zt64.ffmpegkt.gradle

import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

fun Project.publishing() {
    apply(plugin = "com.vanniktech.maven.publish")

    configure<MavenPublishBaseExtension> {
        publishToMavenCentral()
        signAllPublications()
        coordinates("dev.zt64.ffmpeg-kt", "ffmpeg-kt", "$version")
        configure(KotlinMultiplatform(sourcesJar = true))

        pom {
            name = "ffmpeg-kt "
            description = "ffmpeg Kotlin bindings"
            inceptionYear = "2024"
            url = "https://github.com/zt64/ffmpeg-kt"

            licenses {
                license {
                    name = "MIT"
                    url = "https://opensource.org/licenses/MIT"
                }
            }

            developers {
                developer {
                    id = "zt64"
                    name = "zt64"
                    url = "https://zt64.dev"
                }
            }

            scm {
                url = "https://github.com/zt64/ffmpeg-kt"
                connection = "scm:git:github.com/zt64/ffmpeg-kt.git"
                developerConnection = "scm:git:ssh://github.com/zt64/ffmpeg-kt.git"
            }
        }
    }
}