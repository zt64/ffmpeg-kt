@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven("https://repo.nokee.dev/snapshot")
    }

    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }

    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
}

rootProject.name = "ffmpeg-kt"

include(
    "common",
    "avcodec",
    "avdevice",
    "avfilter",
    "avformat",
    "avutil",
    "postproc",
    "swresample",
    "swscale",
    "complete"
)
include("testing")