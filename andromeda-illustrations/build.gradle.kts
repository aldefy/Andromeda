plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform.plugin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        publishLibraryVariants("release")
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
        }
    }
}

android {
    namespace = "design.andromedacompose.illustrations"
    compileSdk = 35
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

extra["PUBLISH_GROUP_ID"] = "design.andromedacompose.illustrations"
extra["PUBLISH_ARTIFACT_ID"] = "andromeda-illustrations"
extra["PUBLISH_VERSION"] = "2.0.0-alpha01"
apply(from = "../scripts/publish-module.gradle")
