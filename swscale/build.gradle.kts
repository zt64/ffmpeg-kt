plugins {
    id("kmp-lib")
}

kotlin {
    sourceSets {
        commonTest {
            dependencies {
                implementation(libs.okio.fakefilesystem)
            }
        }
    }
}