plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose.multiplatform.plugin) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.nexus.publish)
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        android.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            allWarningsAsErrors.set(
                providers.gradleProperty("warningsAsErrors").map { it.toBoolean() }.orElse(false),
            )
        }
    }
}

// Publishing configuration
val ossrhUsername: String? =
    providers.gradleProperty("OSSRH_USERNAME")
        .orElse(providers.environmentVariable("OSSRH_USERNAME"))
        .orNull
val ossrhPassword: String? =
    providers.gradleProperty("OSSRH_PASSWORD")
        .orElse(providers.environmentVariable("OSSRH_PASSWORD"))
        .orNull
val sonatypeStagingProfileId: String? =
    providers.gradleProperty("sonatypeStagingProfileId")
        .orElse(providers.environmentVariable("SONATYPE_STAGING_PROFILE_ID"))
        .orNull

apply(from = "git-hooks.gradle.kts")

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(sonatypeStagingProfileId)
            username.set(ossrhUsername)
            password.set(ossrhPassword)
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
