plugins {
    id("kmp-lib")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.avcodec)
            }
        }
    }
}