plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

extra["PUBLISH_GROUP_ID"] = "design.andromedacompose"
extra["PUBLISH_ARTIFACT_ID"] = "andromeda"
extra["PUBLISH_VERSION"] = "2.0.0-alpha01"

android {
    namespace = "design.andromedacompose"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.animation)
    implementation(libs.compose.activity)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime)

    // Material — temporary, removed in Phase 2
    implementation(libs.compose.material)

    debugImplementation(libs.compose.ui.tooling)

    api(project(":andromeda-icons"))
    api(project(":andromeda-illustrations"))
}

apply(from = "${rootProject.projectDir}/scripts/publish-module.gradle")
