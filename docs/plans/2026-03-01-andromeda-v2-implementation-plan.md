# Andromeda v2.0 Modernization — Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Modernize Andromeda from a 2022-era Android-only Compose design system to a 2026 Compose Multiplatform design system with full token coverage, 35+ accessible components, Phosphor icon system, and KMP support (Android + iOS + Desktop + WasmJS).

**Architecture:** 5-phase incremental modernization. Each phase produces a shippable release. Phase 1 updates toolchain, Phase 2 removes Material/3P deps, Phase 3 fills token gaps, Phase 4 adds components + icons, Phase 5 converts to KMP/CMP.

**Tech Stack:** Kotlin 2.1.x, Compose Multiplatform (latest BOM), AGP 8.7.x, Gradle 8.11+, Kotlin DSL + Version Catalog, Phosphor Icons (ImageVector), Maven Central publishing via Sonatype s01.

**Design doc:** `docs/plans/2026-03-01-andromeda-v2-modernization-design.md`

---

## Phase 1: Build Modernization

**Goal:** Project compiles with 2026 toolchain. No API changes. Release: v2.0.0-alpha01

---

### Task 1.1: Create Version Catalog

**Files:**
- Create: `gradle/libs.versions.toml`

**Step 1: Create libs.versions.toml**

Create the version catalog with all current dependencies mapped from `dependencies.gradle`. This becomes the single source of truth.

```toml
[versions]
kotlin = "2.1.10"
agp = "8.7.3"
compose-bom = "2025.04.01"
compose-activity = "1.9.3"
compose-navigation = "2.8.5"
lifecycle = "2.8.7"
core-ktx = "1.15.0"
appcompat = "1.7.0"
coil = "2.7.0"
accompanist = "0.36.0"
dokka = "1.9.20"
ktlint = "12.1.2"
nexus-publish = "2.0.0"

[libraries]
kotlin-stdlib = { module = "org.jetbrains.kotlin:kotlin-stdlib", version.ref = "kotlin" }
compose-bom = { module = "androidx.compose:compose-bom", version.ref = "compose-bom" }
compose-ui = { module = "androidx.compose.ui:ui" }
compose-ui-tooling = { module = "androidx.compose.ui:ui-tooling" }
compose-ui-tooling-preview = { module = "androidx.compose.ui:ui-tooling-preview" }
compose-foundation = { module = "androidx.compose.foundation:foundation" }
compose-runtime = { module = "androidx.compose.runtime:runtime" }
compose-animation = { module = "androidx.compose.animation:animation" }
compose-activity = { module = "androidx.activity:activity-compose", version.ref = "compose-activity" }
compose-navigation = { module = "androidx.navigation:navigation-compose", version.ref = "compose-navigation" }
lifecycle-runtime = { module = "androidx.lifecycle:lifecycle-runtime-ktx", version.ref = "lifecycle" }
lifecycle-viewmodel-compose = { module = "androidx.lifecycle:lifecycle-viewmodel-compose", version.ref = "lifecycle" }
core-ktx = { module = "androidx.core:core-ktx", version.ref = "core-ktx" }
appcompat = { module = "androidx.appcompat:appcompat", version.ref = "appcompat" }
coil-compose = { module = "io.coil-kt:coil-compose", version.ref = "coil" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
android-library = { id = "com.android.library", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
dokka = { id = "org.jetbrains.dokka", version.ref = "dokka" }
ktlint = { id = "org.jlleitschuh.gradle.ktlint", version.ref = "ktlint" }
nexus-publish = { id = "io.github.gradle-nexus.publish-plugin", version.ref = "nexus-publish" }
```

**Step 2: Verify file created**

Run: `cat gradle/libs.versions.toml | head -5`
Expected: `[versions]` header visible

**Step 3: Commit**

```bash
git add gradle/libs.versions.toml
git commit -m "build: add version catalog (libs.versions.toml)"
```

---

### Task 1.2: Update Gradle Wrapper

**Files:**
- Modify: `gradle/wrapper/gradle-wrapper.properties`

**Step 1: Update Gradle wrapper to 8.11+**

Run: `./gradlew wrapper --gradle-version=8.11.1`

**Step 2: Verify**

Run: `./gradlew --version`
Expected: Gradle 8.11.1

**Step 3: Commit**

```bash
git add gradle/wrapper/ gradlew gradlew.bat
git commit -m "build: upgrade Gradle wrapper to 8.11.1"
```

---

### Task 1.3: Migrate Root build.gradle to Kotlin DSL

**Files:**
- Delete: `build.gradle`
- Create: `build.gradle.kts`
- Delete: `dependencies.gradle`
- Delete: `versions.gradle`

**Step 1: Create root build.gradle.kts**

Replace the Groovy root build.gradle with Kotlin DSL. The version catalog replaces dependencies.gradle and versions.gradle.

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.nexus.publish)
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            val ossrhUsername = providers.gradleProperty("OSSRH_USERNAME")
                .orElse(providers.environmentVariable("OSSRH_USERNAME"))
            val ossrhPassword = providers.gradleProperty("OSSRH_PASSWORD")
                .orElse(providers.environmentVariable("OSSRH_PASSWORD"))
            username.set(ossrhUsername)
            password.set(ossrhPassword)
        }
    }
}
```

**Step 2: Delete old Groovy files**

```bash
rm build.gradle dependencies.gradle versions.gradle
```

**Step 3: Verify project recognizes the new file**

Run: `./gradlew tasks --quiet 2>&1 | head -3`
Expected: No "build.gradle not found" error

**Step 4: Commit**

```bash
git add build.gradle.kts
git rm build.gradle dependencies.gradle versions.gradle
git commit -m "build: migrate root build.gradle to Kotlin DSL"
```

---

### Task 1.4: Migrate settings.gradle to Kotlin DSL

**Files:**
- Delete: `settings.gradle`
- Create: `settings.gradle.kts`

**Step 1: Create settings.gradle.kts**

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolution {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Andromeda"

include(":catalog")
include(":andromeda")
include(":andromeda-icons")
include(":andromeda-illustrations")
```

**Step 2: Delete old file and verify**

```bash
rm settings.gradle
./gradlew projects 2>&1 | head -5
```

Expected: Lists all 4 subprojects

**Step 3: Commit**

```bash
git add settings.gradle.kts
git rm settings.gradle
git commit -m "build: migrate settings.gradle to Kotlin DSL"
```

---

### Task 1.5: Migrate andromeda/build.gradle to Kotlin DSL

**Files:**
- Delete: `andromeda/build.gradle`
- Create: `andromeda/build.gradle.kts`

**Step 1: Create andromeda/build.gradle.kts**

This is the core library module. Key changes:
- Kotlin compose compiler plugin replaces `kotlinCompilerExtensionVersion`
- Version catalog replaces hardcoded dependencies
- Remove Lottie, Timber, compose-material-dialogs, threetenabp (unused — cleaned up now, removed fully in Phase 2)
- Keep Material for now (removed in Phase 2)

```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
}

val publishGroupId = "design.andromedacompose"
val publishArtifactId = "andromeda"
val publishVersion = "2.0.0-alpha01"

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
    implementation("androidx.compose.material:material")

    debugImplementation(libs.compose.ui.tooling)

    api(project(":andromeda-icons"))
    api(project(":andromeda-illustrations"))
}
```

**Step 2: Delete old file**

```bash
rm andromeda/build.gradle
```

**Step 3: Verify compilation**

Run: `./gradlew :andromeda:compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL (or specific deprecation warnings to fix in next tasks)

**Step 4: Commit**

```bash
git add andromeda/build.gradle.kts
git rm andromeda/build.gradle
git commit -m "build: migrate andromeda module to Kotlin DSL + version catalog"
```

---

### Task 1.6: Migrate andromeda-icons/build.gradle to Kotlin DSL

**Files:**
- Delete: `andromeda-icons/build.gradle`
- Create: `andromeda-icons/build.gradle.kts`

**Step 1: Create andromeda-icons/build.gradle.kts**

Unify SDK versions with the main module (compileSdk 35, minSdk 26). Remove buildToolsVersion.

```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "design.andromedacompose.icons"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
}
```

**Step 2: Delete old file and verify**

```bash
rm andromeda-icons/build.gradle
./gradlew :andromeda-icons:compileDebugKotlin 2>&1 | tail -5
```

**Step 3: Commit**

```bash
git add andromeda-icons/build.gradle.kts
git rm andromeda-icons/build.gradle
git commit -m "build: migrate andromeda-icons to Kotlin DSL, unify SDK versions"
```

---

### Task 1.7: Migrate andromeda-illustrations/build.gradle to Kotlin DSL

**Files:**
- Delete: `andromeda-illustrations/build.gradle`
- Create: `andromeda-illustrations/build.gradle.kts`

**Step 1: Create andromeda-illustrations/build.gradle.kts**

Same pattern as icons — unify SDK versions.

```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "design.andromedacompose.illustrations"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
}
```

**Step 2: Delete old file and verify**

```bash
rm andromeda-illustrations/build.gradle
./gradlew :andromeda-illustrations:compileDebugKotlin 2>&1 | tail -5
```

**Step 3: Commit**

```bash
git add andromeda-illustrations/build.gradle.kts
git rm andromeda-illustrations/build.gradle
git commit -m "build: migrate andromeda-illustrations to Kotlin DSL, unify SDK versions"
```

---

### Task 1.8: Migrate catalog/build.gradle to Kotlin DSL

**Files:**
- Delete: `catalog/build.gradle`
- Create: `catalog/build.gradle.kts`

**Step 1: Create catalog/build.gradle.kts**

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "design.andromedacompose.catalog"
    compileSdk = 35

    defaultConfig {
        applicationId = "design.andromedacompose.catalog"
        minSdk = 26
        targetSdk = 35
        versionCode = 17
        versionName = "2.0.0-alpha01"
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
    implementation(project(":andromeda"))
    implementation(project(":andromeda-icons"))
    implementation(project(":andromeda-illustrations"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animation)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.lifecycle.runtime)
    implementation(libs.core.ktx)

    // Material — temporary for catalog, removed in Phase 2
    implementation("androidx.compose.material:material")

    debugImplementation(libs.compose.ui.tooling)
}
```

**Step 2: Delete old file and verify**

```bash
rm catalog/build.gradle
./gradlew :catalog:compileDebugKotlin 2>&1 | tail -5
```

**Step 3: Commit**

```bash
git add catalog/build.gradle.kts
git rm catalog/build.gradle
git commit -m "build: migrate catalog to Kotlin DSL"
```

---

### Task 1.9: Migrate publish script to Kotlin DSL

**Files:**
- Delete: `scripts/publish-module.gradle`
- Create: `scripts/publish-module.gradle.kts`

**Step 1: Create scripts/publish-module.gradle.kts**

```kotlin
apply(plugin = "maven-publish")
apply(plugin = "signing")

val publishGroupId: String by project
val publishArtifactId: String by project
val publishVersion: String by project

tasks.register<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = publishGroupId
                artifactId = publishArtifactId
                version = publishVersion

                artifact(tasks["androidSourcesJar"])

                pom {
                    name.set(publishArtifactId)
                    description.set("Andromeda - Compose Design System")
                    url.set("https://github.com/aldefy/Andromeda")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("aldefy")
                            name.set("Adit Lal")
                            email.set("aditlal@gmail.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:github.com/aldefy/Andromeda.git")
                        developerConnection.set("scm:git:ssh://github.com/aldefy/Andromeda.git")
                        url.set("https://github.com/aldefy/Andromeda/tree/main")
                    }
                }
            }
        }
    }

    signing {
        useInMemoryPgpKeys(
            providers.environmentVariable("GPG_KEY_ID").orNull,
            providers.environmentVariable("GPG_KEY").orNull,
            providers.environmentVariable("GPG_PASSPHRASE").orNull,
        )
        sign(publishing.publications)
    }
}
```

Note: The `android` accessor requires the Android Gradle Plugin to be applied. This script is applied from library modules that already have AGP.

**Step 2: Delete old file**

```bash
rm scripts/publish-module.gradle
```

**Step 3: Commit**

```bash
git add scripts/publish-module.gradle.kts
git rm scripts/publish-module.gradle
git commit -m "build: migrate publish script to Kotlin DSL"
```

---

### Task 1.10: Fix Deprecated Compose APIs

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/buttons/Button.kt`
- Modify: `andromeda/src/main/java/design/andromedacompose/components/Icon.kt`
- Modify: `andromeda/src/main/java/design/andromedacompose/components/IconButton.kt`
- Modify: Any other files using deprecated APIs after Kotlin/Compose upgrade

**Step 1: Fix rememberRipple deprecation**

In Compose 1.7+, `rememberRipple()` was removed. Replace with `ripple()` from `androidx.compose.material.ripple`. Note: We keep Material ripple for now — full Material removal happens in Phase 2.

Search for all `rememberRipple` usages and replace:

```kotlin
// OLD:
import androidx.compose.material.ripple.rememberRipple
// ...
indication = rememberRipple(bounded = true)

// NEW:
import androidx.compose.material.ripple.ripple
// ...
indication = ripple(bounded = true)
```

Files to update: `Button.kt`, `Icon.kt`, `IconButton.kt`

**Step 2: Fix any ExperimentalFoundationApi removals**

Check `TextField.kt` for `@OptIn(ExperimentalFoundationApi::class)` on `BringIntoViewRequester`. In newer Compose this may have been stabilized or changed. Update as needed based on compilation errors.

**Step 3: Verify full project compiles**

Run: `./gradlew assembleDebug 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL

**Step 4: Commit**

```bash
git add -A
git commit -m "fix: update deprecated Compose APIs for latest BOM"
```

---

### Task 1.11: Update CI Workflows

**Files:**
- Modify: `.github/workflows/build_deploy.yml`
- Modify: `.github/workflows/gh-pages.yml`

**Step 1: Update build_deploy.yml**

Key changes:
- actions/checkout@v2 -> v4
- actions/cache@v2 -> v4
- actions/upload-artifact@v2 -> v4
- JDK 11 -> JDK 17
- Remove Ruby/Danger steps (or update Ruby to 3.x)

**Step 2: Update gh-pages.yml**

Key changes:
- ubuntu-18.04 -> ubuntu-latest
- actions/checkout@v2 -> v4
- JDK 11 -> JDK 17

**Step 3: Verify YAML syntax**

Run: `python3 -c "import yaml; yaml.safe_load(open('.github/workflows/build_deploy.yml'))"`
Expected: No errors

**Step 4: Commit**

```bash
git add .github/workflows/
git commit -m "ci: update GitHub Actions to v4, JDK 17, ubuntu-latest"
```

---

### Task 1.12: Update gradle.properties

**Files:**
- Modify: `gradle.properties`

**Step 1: Update JVM args and add Kotlin/Compose settings**

Add/update these properties:

```properties
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
```

**Step 2: Commit**

```bash
git add gradle.properties
git commit -m "build: update gradle.properties for modern toolchain"
```

---

### Task 1.13: Full Build Verification

**Step 1: Clean and build everything**

Run: `./gradlew clean assembleDebug 2>&1 | tail -20`
Expected: BUILD SUCCESSFUL

**Step 2: Run ktlint**

Run: `./gradlew ktlintCheck 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL (or fix any new ktlint issues)

**Step 3: Tag release**

```bash
git tag v2.0.0-alpha01
git commit --allow-empty -m "release: v2.0.0-alpha01 — build modernization complete"
```

---

## Phase 2: Clean Architecture

**Goal:** Zero Material dependencies. Zero unused 3P dependencies. All known bugs fixed. Release: v2.0.0-alpha02

---

### Task 2.1: Create Custom CompositionLocals to Replace Material

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/foundation/LocalAndromedaContentColor.kt`
- Create: `andromeda/src/main/java/design/andromedacompose/foundation/LocalAndromedaTextStyle.kt`

**Step 1: Create LocalAndromedaContentColor**

```kotlin
package design.andromedacompose.foundation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalAndromedaContentColor = compositionLocalOf { Color.Black }
```

**Step 2: Create LocalAndromedaTextStyle**

```kotlin
package design.andromedacompose.foundation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle

val LocalAndromedaTextStyle = compositionLocalOf { TextStyle.Default }

@Composable
fun ProvideAndromedaTextStyle(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalAndromedaTextStyle.current.merge(value)
    CompositionLocalProvider(LocalAndromedaTextStyle provides mergedStyle, content = content)
}
```

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/LocalAndromeda*.kt
git commit -m "feat: add custom CompositionLocals to replace Material equivalents"
```

---

### Task 2.2: Create Custom Ripple/Indication to Replace Material Ripple

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/foundation/indication/AndromedaIndication.kt`

**Step 1: Create Foundation-based indication**

Use `Indication` from `androidx.compose.foundation` instead of Material's ripple.

```kotlin
package design.andromedacompose.foundation.indication

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Andromeda's default interaction indication.
 * Uses alpha dimming on press instead of Material ripple.
 */
object AndromedaIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return AndromedaIndicationNode(interactionSource)
    }

    override fun equals(other: Any?) = other === this
    override fun hashCode() = System.identityHashCode(this)
}

private class AndromedaIndicationNode(
    private val interactionSource: InteractionSource,
) : Modifier.Node(), DrawModifierNode {
    private var isPressed = false

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        isPressed = true
                        invalidateDraw()
                    }
                    is PressInteraction.Release,
                    is PressInteraction.Cancel -> {
                        isPressed = false
                        invalidateDraw()
                    }
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        drawContent()
        if (isPressed) {
            drawRect(
                color = androidx.compose.ui.graphics.Color.Black,
                alpha = 0.1f,
            )
        }
    }
}

@Composable
fun rememberAndromedaIndication(): Indication = remember { AndromedaIndication }
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/indication/
git commit -m "feat: add custom Indication to replace Material ripple"
```

---

### Task 2.3: Create Custom contentColorFor to Replace Material

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/foundation/colors/ContentColors.kt`

**Step 1: Replace Material's contentColorFor with Andromeda's**

Read `ContentColors.kt` and replace the Material import with a custom implementation that maps Andromeda background colors to appropriate content colors using the theme.

```kotlin
// Replace: import androidx.compose.material.LocalContentColor
// With: import design.andromedacompose.foundation.LocalAndromedaContentColor

@Composable
fun contentColorFor(backgroundColor: Color): Color {
    val colors = AndromedaTheme.colors
    return when (backgroundColor) {
        colors.primaryColors.active -> colors.contentColors.normal
        colors.primaryColors.background -> colors.contentColors.normal
        colors.secondaryColors.active -> colors.contentColors.normal
        colors.secondaryColors.background -> colors.contentColors.normal
        colors.primaryColors.error -> Color.White
        else -> LocalAndromedaContentColor.current
    }
}
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/colors/ContentColors.kt
git commit -m "feat: replace Material contentColorFor with Andromeda implementation"
```

---

### Task 2.4: Update Surface.kt — Remove Material Imports

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/Surface.kt`

**Step 1: Replace Material imports**

```kotlin
// Replace:
// import androidx.compose.material.LocalContentColor
// import androidx.compose.material.contentColorFor
// With:
import design.andromedacompose.foundation.LocalAndromedaContentColor
import design.andromedacompose.foundation.colors.contentColorFor
```

Update all `LocalContentColor` references to `LocalAndromedaContentColor`.

**Step 2: Verify compilation**

Run: `./gradlew :andromeda:compileDebugKotlin 2>&1 | tail -5`

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/Surface.kt
git commit -m "refactor: remove Material imports from Surface.kt"
```

---

### Task 2.5: Update TextStyle.kt — Remove Material Imports

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/foundation/typography/TextStyle.kt`

**Step 1: Replace Material text style locals**

```kotlin
// Replace:
// import androidx.compose.material.LocalTextStyle
// import androidx.compose.material.ProvideTextStyle
// With:
import design.andromedacompose.foundation.LocalAndromedaTextStyle
import design.andromedacompose.foundation.ProvideAndromedaTextStyle
```

Update function bodies accordingly.

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/typography/TextStyle.kt
git commit -m "refactor: remove Material imports from TextStyle.kt"
```

---

### Task 2.6: Update Button.kt — Remove Material Imports

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/buttons/Button.kt`

**Step 1: Replace Material imports**

Replace `material.contentColorFor` with Andromeda's and `material.ripple.rememberRipple/ripple` with `AndromedaIndication`.

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/buttons/Button.kt
git commit -m "refactor: remove Material imports from Button.kt"
```

---

### Task 2.7: Update Icon.kt and IconButton.kt — Remove Material Ripple

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/Icon.kt`
- Modify: `andromeda/src/main/java/design/andromedacompose/components/IconButton.kt`

**Step 1: Replace ripple with AndromedaIndication in both files**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/Icon.kt
git add andromeda/src/main/java/design/andromedacompose/components/IconButton.kt
git commit -m "refactor: remove Material ripple from Icon and IconButton"
```

---

### Task 2.8: Add Material Icon Replacements to AndromedaIcons

**Files:**
- Modify: `andromeda-icons/src/main/java/design/andromedacompose/icons/AndromedaIcons.kt`
- Create: `andromeda-icons/src/main/java/design/andromedacompose/icons/system/` (directory with ImageVector icons)

**Step 1: Add ImageVector definitions for Material Icons being replaced**

Create ImageVector Kotlin source for: ArrowBack, Close, DateRange, Error, Info, Edit, MoreVert. These replace the `material.icons.filled.*` imports.

Generate each as a `val` on the `AndromedaIcons` object. Use standard `ImageVector.Builder` pattern.

Example for ArrowBack:
```kotlin
val ArrowBack: ImageVector
    get() = _arrowBack ?: ImageVector.Builder(
        name = "ArrowBack",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(20f, 11f)
            horizontalLineTo(7.83f)
            lineToRelative(5.59f, -5.59f)
            lineTo(12f, 4f)
            lineToRelative(-8f, 8f)
            lineToRelative(8f, 8f)
            lineToRelative(1.41f, -1.41f)
            lineTo(7.83f, 13f)
            horizontalLineTo(20f)
            verticalLineToRelative(-2f)
            close()
        }
    }.build().also { _arrowBack = it }
private var _arrowBack: ImageVector? = null
```

Repeat for Close, DateRange, Error, Info, Edit, MoreVert.

**Step 2: Commit**

```bash
git add andromeda-icons/src/main/java/design/andromedacompose/icons/
git commit -m "feat: add system icons (ArrowBack, Close, etc.) as ImageVector"
```

---

### Task 2.9: Rewrite AndromedaNavBar — Remove Material Surface/Text

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/navbar/AndromedaNavBar.kt`

**Step 1: Replace Material Surface and Text with Andromeda's own**

```kotlin
// Replace:
// import androidx.compose.material.ContentAlpha
// import androidx.compose.material.LocalContentAlpha
// import androidx.compose.material.Surface
// import androidx.compose.material.Text
// With:
import design.andromedacompose.components.Surface
import design.andromedacompose.components.Text
import design.andromedacompose.foundation.LocalAndromedaContentColor
```

Remove `LocalContentAlpha` / `ContentAlpha` usage — replace with Andromeda's ContentEmphasis system.

**Step 2: Verify compilation**

Run: `./gradlew :andromeda:compileDebugKotlin 2>&1 | tail -5`

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/navbar/AndromedaNavBar.kt
git commit -m "refactor: rewrite NavBar with Andromeda Surface/Text, remove Material"
```

---

### Task 2.10: Rewrite BackButton — Remove Material IconButton

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/BackButton.kt`

**Step 1: Replace Material IconButton and Icon with Andromeda's**

Use Andromeda's `IconButton` and `Icon` composables. Replace `Icons.Filled.ArrowBack` with `AndromedaIcons.ArrowBack`.

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/BackButton.kt
git commit -m "refactor: rewrite BackButton with Andromeda components"
```

---

### Task 2.11: Update TextField and FieldMessage — Replace Material Icons

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/inputs/TextField.kt`
- Modify: `andromeda/src/main/java/design/andromedacompose/components/inputs/field/FieldMessage.kt`

**Step 1: Replace Material Icons imports with AndromedaIcons**

```kotlin
// Replace: Icons.Filled.Close -> AndromedaIcons.Close
// Replace: Icons.Filled.Edit -> AndromedaIcons.Edit
// Replace: Icons.Filled.MoreVert -> AndromedaIcons.MoreVert
// Replace: Icons.Filled.Error -> AndromedaIcons.Error
// Replace: Icons.Filled.Info -> AndromedaIcons.Info
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/inputs/
git commit -m "refactor: replace Material Icons with AndromedaIcons in TextField/FieldMessage"
```

---

### Task 2.12: Remove DatePickerInputField and 3P Dependencies

**Files:**
- Delete: `andromeda/src/main/java/design/andromedacompose/components/inputs/DatePickerInputField.kt`
- Modify: `andromeda/build.gradle.kts` — remove Lottie, Timber, compose-material-dialogs, threetenabp

**Step 1: Delete DatePickerInputField**

```bash
rm andromeda/src/main/java/design/andromedacompose/components/inputs/DatePickerInputField.kt
```

**Step 2: Remove all unused 3P dependencies from build.gradle.kts**

Ensure no `lottie`, `timber`, `compose-material-dialogs`, `threetenabp` lines remain.

**Step 3: Remove DatePickerInputField from catalog if referenced**

Check `catalog/` for any references to `DatePickerInputField` and remove them.

**Step 4: Verify compilation**

Run: `./gradlew assembleDebug 2>&1 | tail -10`

**Step 5: Commit**

```bash
git add -A
git commit -m "refactor: remove DatePickerInputField and all unused 3P dependencies"
```

---

### Task 2.13: Remove Material Dependency from build.gradle.kts

**Files:**
- Modify: `andromeda/build.gradle.kts`
- Modify: `catalog/build.gradle.kts`

**Step 1: Remove the Material dependency line**

```kotlin
// DELETE this line from both files:
implementation("androidx.compose.material:material")
```

**Step 2: Verify zero Material imports remain**

Run: `grep -r "androidx.compose.material" andromeda/src/ --include="*.kt"`
Expected: No results

**Step 3: Full build verification**

Run: `./gradlew assembleDebug 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL

**Step 4: Commit**

```bash
git add andromeda/build.gradle.kts catalog/build.gradle.kts
git commit -m "refactor: remove Material dependency entirely"
```

---

### Task 2.14: Fix Known Bugs

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/foundation/colors/FillColors.kt` (PrimaryColors.copy bug)
- Modify: `andromeda/src/main/java/design/andromedacompose/foundation/colors/BorderColors.kt` (copy defaults)
- Modify: `andromeda/src/main/java/design/andromedacompose/components/buttons/ButtonElevation.kt` (dead LaunchedEffect)

**Step 1: Fix PrimaryColors.copy() parameter order**

The `active` and `background` parameters are swapped in the constructor call inside `copy()`. Fix by matching parameter names to constructor positions. Apply same fix to SecondaryColors.copy() and TertiaryColors.copy().

**Step 2: Fix BorderColors.copy() defaults**

Add default parameter values matching current properties.

**Step 3: Remove empty LaunchedEffect in ButtonElevation**

Delete the LaunchedEffect block with empty body.

**Step 4: Verify compilation**

Run: `./gradlew assembleDebug 2>&1 | tail -5`

**Step 5: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/colors/FillColors.kt
git add andromeda/src/main/java/design/andromedacompose/foundation/colors/BorderColors.kt
git add andromeda/src/main/java/design/andromedacompose/components/buttons/ButtonElevation.kt
git commit -m "fix: correct copy() parameter order, remove dead code"
```

---

### Task 2.15: Phase 2 Verification

**Step 1: Clean build**

Run: `./gradlew clean assembleDebug 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL

**Step 2: Verify zero Material imports**

Run: `grep -r "androidx.compose.material" andromeda/src/ andromeda-icons/src/ andromeda-illustrations/src/ --include="*.kt"`
Expected: No results

**Step 3: Verify zero 3P imports**

Run: `grep -r "lottie\|timber\|threetenabp\|vanpra" andromeda/src/ --include="*.kt"`
Expected: No results

**Step 4: Tag release**

```bash
git tag v2.0.0-alpha02
git commit --allow-empty -m "release: v2.0.0-alpha02 — clean architecture complete"
```

---

## Phase 3: Design Token Gaps

**Goal:** Complete token system. Release: v2.0.0-alpha03

---

### Task 3.1: Add Spacing Token Scale

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/foundation/tokens/Spacing.kt`

**Step 1: Expand Spacing from just OneX to full scale**

```kotlin
package design.andromedacompose.foundation.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val None: Dp = 0.dp
    val XXSmall: Dp = 2.dp
    val XSmall: Dp = 4.dp
    val Small: Dp = 8.dp
    val Medium: Dp = 16.dp
    val Large: Dp = 24.dp
    val XLarge: Dp = 32.dp
    val XXLarge: Dp = 48.dp

    // Backwards compatibility
    val OneX: Dp = XSmall
    fun Dp.times(factor: Int): Dp = this * factor
}
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/tokens/Spacing.kt
git commit -m "feat: expand Spacing tokens to full scale (XXSmall to XXLarge)"
```

---

### Task 3.2: Add Elevation Tokens

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/foundation/tokens/Elevation.kt`

**Step 1: Create elevation token system**

```kotlin
package design.andromedacompose.foundation.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object AndromedaElevation {
    val None: Dp = 0.dp
    val XSmall: Dp = 1.dp
    val Small: Dp = 2.dp
    val Medium: Dp = 4.dp
    val Large: Dp = 8.dp
    val XLarge: Dp = 16.dp
}
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/tokens/Elevation.kt
git commit -m "feat: add Elevation tokens"
```

---

### Task 3.3: Add Motion/Animation Tokens

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/foundation/tokens/Motion.kt`

**Step 1: Create motion token system**

```kotlin
package design.andromedacompose.foundation.tokens

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring

object AndromedaMotion {
    // Durations (ms)
    val Instant: Int = 0
    val Fast: Int = 100
    val Normal: Int = 200
    val Slow: Int = 350
    val XSlow: Int = 500

    // Easings
    val EaseIn: Easing = CubicBezierEasing(0.4f, 0f, 1f, 1f)
    val EaseOut: Easing = CubicBezierEasing(0f, 0f, 0.2f, 1f)
    val EaseInOut: Easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)

    // Springs
    fun <T> defaultSpring(): SpringSpec<T> = spring(
        dampingRatio = 0.85f,
        stiffness = 400f,
    )
}
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/tokens/Motion.kt
git commit -m "feat: add Motion/Animation tokens (durations, easings, springs)"
```

---

### Task 3.4: Add Opacity Tokens

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/foundation/tokens/Opacity.kt`

**Step 1: Create opacity tokens**

```kotlin
package design.andromedacompose.foundation.tokens

object AndromedaOpacity {
    val Transparent: Float = 0f
    val Divider: Float = 0.12f
    val Disabled: Float = 0.48f
    val Subtle: Float = 0.66f
    val Minor: Float = 0.80f
    val Full: Float = 1f
}
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/tokens/Opacity.kt
git commit -m "feat: add Opacity tokens"
```

---

### Task 3.5: Expose New Tokens via AndromedaTheme

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/AndromedaTheme.kt`

**Step 1: Add token accessors to the theme**

Add companion properties so consumers can access `AndromedaTheme.elevation`, `AndromedaTheme.motion`, `AndromedaTheme.opacity`, `AndromedaTheme.spacing`.

Since these are object singletons (not theme-customizable yet), they can be direct references:

```kotlin
object AndromedaTheme {
    val colors: AndromedaColors @Composable get() = LocalColors.current
    val typography: AndromedaTypography @Composable get() = LocalTypography.current
    val shapes: AndromedaShapes @Composable get() = LocalShapes.current

    // New token accessors
    val spacing: Spacing get() = Spacing
    val elevation: AndromedaElevation get() = AndromedaElevation
    val motion: AndromedaMotion get() = AndromedaMotion
    val opacity: AndromedaOpacity get() = AndromedaOpacity
}
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/AndromedaTheme.kt
git commit -m "feat: expose new tokens via AndromedaTheme accessors"
```

---

### Task 3.6: Replace Hardcoded Values in Existing Components

**Files:**
- Modify: `andromeda/src/main/java/design/andromedacompose/components/Divider.kt` — replace hardcoded alpha 0.12f with `AndromedaOpacity.Divider`
- Modify: `andromeda/src/main/java/design/andromedacompose/components/buttons/Button.kt` — replace hardcoded elevation values with `AndromedaElevation.*`
- Modify: `andromeda/src/main/java/design/andromedacompose/components/buttons/ButtonElevation.kt` — replace hardcoded elevation
- Modify: `andromeda/src/main/java/design/andromedacompose/components/navbar/AndromedaNavBar.kt` — replace hardcoded elevation
- Modify: `andromeda/src/main/java/design/andromedacompose/components/inputs/TextField.kt` — replace hardcoded animation durations with `AndromedaMotion.*`

**Step 1: Update each file to use token references instead of magic numbers**

**Step 2: Verify compilation**

Run: `./gradlew assembleDebug 2>&1 | tail -5`

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/
git commit -m "refactor: replace hardcoded values with design tokens"
```

---

### Task 3.7: Add Token Showcase to Catalog

**Files:**
- Create: `catalog/src/main/java/design/andromedacompose/catalog/screens/SpacingScreen.kt`
- Create: `catalog/src/main/java/design/andromedacompose/catalog/screens/ElevationScreen.kt`
- Modify: `catalog/src/main/java/design/andromedacompose/catalog/Screen.kt` — add new screens
- Modify: `catalog/src/main/java/design/andromedacompose/catalog/NavGraph.kt` — add routes
- Modify: `catalog/src/main/java/design/andromedacompose/catalog/screens/MainScreen.kt` — add navigation items

**Step 1: Create screens that visually demonstrate spacing, elevation, motion, and opacity tokens**

**Step 2: Verify catalog builds and runs**

Run: `./gradlew :catalog:assembleDebug 2>&1 | tail -5`

**Step 3: Commit**

```bash
git add catalog/src/main/java/design/andromedacompose/catalog/
git commit -m "feat: add token showcase screens to catalog app"
```

---

### Task 3.8: Phase 3 Verification

**Step 1: Clean build**

Run: `./gradlew clean assembleDebug 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL

**Step 2: Tag release**

```bash
git tag v2.0.0-alpha03
git commit --allow-empty -m "release: v2.0.0-alpha03 — complete token system"
```

---

## Phase 4: Component Expansion + Icons

**Goal:** ~35+ components, Phosphor icon system, accessibility. Release: v2.0.0-beta01

> **Note:** This phase is the largest. Each component follows the pattern: create component file, add accessibility semantics, add catalog screen, commit. Components are grouped by tier.

---

### Task 4.1: Set Up Phosphor Icon Integration

**Files:**
- Modify: `andromeda-icons/build.gradle.kts` — add Phosphor dependency or generated source
- Modify: `andromeda-icons/src/main/java/design/andromedacompose/icons/AndromedaIcons.kt`

**Step 1: Evaluate integration approach**

Option A: Depend on `compose-phosphor-icon` library directly
Option B: Generate ImageVector source from Phosphor SVGs into the project

Recommendation: Option A for speed, with the library as an `api` dependency so consumers get access. Can switch to generated source later for size optimization.

Add to `libs.versions.toml`:
```toml
[versions]
phosphor-icons = "<latest>"

[libraries]
phosphor-icons = { module = "com.adamglin:compose-phosphor-icon", version.ref = "phosphor-icons" }
```

**Step 2: Create icon API surface**

```kotlin
object AndromedaIcons {
    // System icons (from Task 2.8)
    val ArrowBack: ImageVector get() = ...
    val Close: ImageVector get() = ...
    // etc.

    // Phosphor icons re-exported for convenience
    val Phosphor = com.adamglin.phosphoricons.Phosphor
}
```

**Step 3: Commit**

```bash
git add andromeda-icons/ gradle/libs.versions.toml
git commit -m "feat: integrate Phosphor icon system"
```

---

### Task 4.2: Create Accessibility Utilities

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/foundation/accessibility/AccessibilityDefaults.kt`

**Step 1: Create shared accessibility utilities**

```kotlin
package design.andromedacompose.foundation.accessibility

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

fun Modifier.minimumInteractiveSize(): Modifier =
    this.sizeIn(minWidth = 48.dp, minHeight = 48.dp)

fun Modifier.toggleableSemantics(
    checked: Boolean,
    role: Role,
): Modifier = semantics {
    this.role = role
    stateDescription = if (checked) "Checked" else "Unchecked"
}
```

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/foundation/accessibility/
git commit -m "feat: add accessibility utility modifiers"
```

---

### Task 4.3: Implement Checkbox and TriStateCheckbox

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/selection/Checkbox.kt`

**Step 1: Implement Checkbox with accessibility**

Build on Foundation's `toggleable` modifier. Use `Role.Checkbox` semantics. Animate check mark with `AndromedaMotion` tokens. 48dp minimum touch target.

**Step 2: Add catalog screen**

- Create: `catalog/src/main/java/design/andromedacompose/catalog/screens/CheckboxScreen.kt`

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/selection/
git add catalog/src/main/java/design/andromedacompose/catalog/screens/CheckboxScreen.kt
git commit -m "feat: add Checkbox and TriStateCheckbox components"
```

---

### Task 4.4: Implement RadioButton and RadioGroup

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/selection/RadioButton.kt`
- Create: `andromeda/src/main/java/design/andromedacompose/components/selection/RadioGroup.kt`

**Step 1: Implement with `Role.RadioButton` semantics, `selectableGroup` for RadioGroup**

**Step 2: Add catalog screen**

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/selection/Radio*.kt
git commit -m "feat: add RadioButton and RadioGroup components"
```

---

### Task 4.5: Implement Switch/Toggle

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/selection/Switch.kt`

**Step 1: Implement with thumb/track animation, `Role.Switch` semantics**

**Step 2: Add catalog screen**

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/selection/Switch.kt
git commit -m "feat: add Switch/Toggle component"
```

---

### Task 4.6: Implement Slider

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/selection/Slider.kt`

**Step 1: Build on Foundation's `draggable` modifier. Support continuous and discrete (stepped) modes.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/selection/Slider.kt
git commit -m "feat: add Slider component"
```

---

### Task 4.7: Implement AlertBanner

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/feedback/AlertBanner.kt`

**Step 1: Implement inline alert with variants (info, warning, error, success). Use `liveRegion` semantics for screen reader announcement.**

**Step 2: Add catalog screen**

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/feedback/AlertBanner.kt
git commit -m "feat: add AlertBanner component"
```

---

### Task 4.8: Implement Toast/Snackbar

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/feedback/Snackbar.kt`
- Create: `andromeda/src/main/java/design/andromedacompose/components/feedback/SnackbarHost.kt`

**Step 1: Implement with timed auto-dismiss, optional action button, `liveRegion` semantics**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/feedback/
git commit -m "feat: add Snackbar and SnackbarHost components"
```

---

### Task 4.9: Implement Badge

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/feedback/Badge.kt`

**Step 1: Implement numeric badge and dot badge variants. Use `contentDescription` for accessibility.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/feedback/Badge.kt
git commit -m "feat: add Badge component"
```

---

### Task 4.10: Implement ProgressIndicator

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/feedback/ProgressIndicator.kt`

**Step 1: Implement linear and circular variants, determinate and indeterminate. Use `ProgressBarRangeInfo` semantics.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/feedback/ProgressIndicator.kt
git commit -m "feat: add ProgressIndicator (linear + circular)"
```

---

### Task 4.11: Implement Skeleton/Loading Placeholder

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/feedback/Skeleton.kt`

**Step 1: Implement shimmer animation placeholder. Configurable shape (rectangle, circle, rounded).**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/feedback/Skeleton.kt
git commit -m "feat: add Skeleton loading placeholder"
```

---

### Task 4.12: Implement Card

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/containers/Card.kt`

**Step 1: Build on Andromeda Surface with elevation tokens, slot-based API (header, content, footer slots). Use `mergeDescendants` for compound a11y.**

**Step 2: Add catalog screen**

**Step 3: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/containers/Card.kt
git commit -m "feat: add Card component"
```

---

### Task 4.13: Implement ListItem

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/containers/ListItem.kt`

**Step 1: Implement with leading/trailing content slots, single/two/three line variants, 48dp minimum height.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/containers/ListItem.kt
git commit -m "feat: add ListItem component"
```

---

### Task 4.14: Implement BottomSheet

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/overlay/BottomSheet.kt`

**Step 1: Build on Foundation's `AnchoredDraggable`. Standard and modal variants. Swipe-to-dismiss. Uses `bottomSheet` shape token.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/overlay/BottomSheet.kt
git commit -m "feat: add BottomSheet component"
```

---

### Task 4.15: Implement Dialog

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/overlay/Dialog.kt`

**Step 1: Build on Foundation's `Popup` or `Dialog` composable. Slot-based API (title, content, actions). Uses `dialogShape` token. Scrim background.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/overlay/Dialog.kt
git commit -m "feat: add Dialog component"
```

---

### Task 4.16: Implement TabBar/TabGroup

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/navigation/TabBar.kt`

**Step 1: Implement with `selectableGroup` semantics, `Role.Tab`, animated indicator. Scrollable for many tabs.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/navigation/TabBar.kt
git commit -m "feat: add TabBar/TabGroup component"
```

---

### Task 4.17: Implement TextArea

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/inputs/TextArea.kt`

**Step 1: Build on BasicTextField with multiline support. Share styling with existing TextField. Min/max lines parameter.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/inputs/TextArea.kt
git commit -m "feat: add TextArea (multiline) component"
```

---

### Task 4.18: Implement Select/Dropdown

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/inputs/Select.kt`

**Step 1: Implement trigger (ReadonlyTextField-like) + popup menu. Keyboard navigable. `Role.DropdownList` semantics.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/inputs/Select.kt
git commit -m "feat: add Select/Dropdown component"
```

---

### Task 4.19: Implement Chip/Tag

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/content/Chip.kt`

**Step 1: Implement filter, action, and input chip variants. Optional dismiss button. Slot-based leading icon.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/content/Chip.kt
git commit -m "feat: add Chip/Tag component"
```

---

### Task 4.20: Implement Avatar

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/content/Avatar.kt`

**Step 1: Implement image avatar with fallback to initials. Circular shape. Size variants (small, medium, large).**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/content/Avatar.kt
git commit -m "feat: add Avatar component"
```

---

### Task 4.21: Implement Tooltip

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/overlay/Tooltip.kt`

**Step 1: Build on Foundation's `Popup`. Show on long press (mobile) or hover (desktop). Auto-dismiss.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/overlay/Tooltip.kt
git commit -m "feat: add Tooltip component"
```

---

### Task 4.22: Implement SearchField

**Files:**
- Create: `andromeda/src/main/java/design/andromedacompose/components/inputs/SearchField.kt`

**Step 1: Extend TextField with search icon (leading), clear button (trailing), debounced search callback.**

**Step 2: Commit**

```bash
git add andromeda/src/main/java/design/andromedacompose/components/inputs/SearchField.kt
git commit -m "feat: add SearchField component"
```

---

### Task 4.23: Update Catalog with All New Components

**Files:**
- Modify: `catalog/src/main/java/design/andromedacompose/catalog/Screen.kt`
- Modify: `catalog/src/main/java/design/andromedacompose/catalog/NavGraph.kt`
- Modify: `catalog/src/main/java/design/andromedacompose/catalog/screens/MainScreen.kt`
- Create: Multiple new screen files in `catalog/src/main/java/design/andromedacompose/catalog/screens/`

**Step 1: Add catalog screens for each new component group**

Organize by category: Selection Controls, Feedback, Containers, Inputs, Content, Navigation.

**Step 2: Add theme switcher to catalog**

Add a FAB or top-bar toggle that switches between light/dark/custom themes live.

**Step 3: Verify catalog builds**

Run: `./gradlew :catalog:assembleDebug 2>&1 | tail -5`

**Step 4: Commit**

```bash
git add catalog/
git commit -m "feat: update catalog with all new components and theme switcher"
```

---

### Task 4.24: Phase 4 Verification

**Step 1: Full clean build**

Run: `./gradlew clean assembleDebug 2>&1 | tail -10`
Expected: BUILD SUCCESSFUL

**Step 2: Component count verification**

Run: `grep -r "@Composable" andromeda/src/ --include="*.kt" -l | wc -l`
Expected: Significantly more than the original ~12 files

**Step 3: Accessibility verification**

Run: `grep -r "Role\.\|semantics\|contentDescription\|stateDescription\|liveRegion" andromeda/src/ --include="*.kt" -l | wc -l`
Expected: Most component files should have accessibility annotations

**Step 4: Tag release**

```bash
git tag v2.0.0-beta01
git commit --allow-empty -m "release: v2.0.0-beta01 — component expansion complete"
```

---

## Phase 5: KMP / Compose Multiplatform

**Goal:** Full CMP support — Android, iOS, Desktop, WasmJS. Release: v2.0.0

> **Note:** This is the most architecturally complex phase. The key principle: move everything possible to `commonMain`, use `expect/actual` only where platform-specific code is unavoidable.

---

### Task 5.1: Update Build System for KMP

**Files:**
- Modify: `gradle/libs.versions.toml` — add CMP dependencies
- Modify: `settings.gradle.kts` — add compose multiplatform plugin
- Modify: Root `build.gradle.kts`

**Step 1: Add CMP versions and plugins to version catalog**

```toml
[versions]
compose-multiplatform = "<latest CMP version>"
kotlin = "2.1.10"

[plugins]
compose-multiplatform = { id = "org.jetbrains.compose", version.ref = "compose-multiplatform" }
```

**Step 2: Commit**

```bash
git add gradle/libs.versions.toml settings.gradle.kts build.gradle.kts
git commit -m "build: add Compose Multiplatform plugin and dependencies"
```

---

### Task 5.2: Convert andromeda Module to KMP

**Files:**
- Rewrite: `andromeda/build.gradle.kts` — from Android library to KMP library
- Restructure: `andromeda/src/main/java/` -> `andromeda/src/commonMain/kotlin/`

**Step 1: Rewrite build.gradle.kts for KMP**

```kotlin
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            api(project(":andromeda-icons"))
            api(project(":andromeda-illustrations"))
        }
        androidMain.dependencies {
            implementation(libs.compose.activity)
        }
    }
}

android {
    namespace = "design.andromedacompose"
    compileSdk = 35
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
```

**Step 2: Move source files**

```bash
mkdir -p andromeda/src/commonMain/kotlin/design/andromedacompose
mv andromeda/src/main/java/design/andromedacompose/* andromeda/src/commonMain/kotlin/design/andromedacompose/
```

**Step 3: Verify compilation**

Run: `./gradlew :andromeda:compileKotlinDesktop 2>&1 | tail -10`
Expected: Compilation errors for Android-specific references (fix in next tasks)

**Step 4: Commit**

```bash
git add andromeda/
git commit -m "build: convert andromeda module to Kotlin Multiplatform"
```

---

### Task 5.3: Convert Font Loading to CMP Resources

**Files:**
- Modify: `andromeda/src/commonMain/kotlin/design/andromedacompose/foundation/typography/AndromedaFonts.kt`
- Move: Font files from `andromeda/src/main/res/font/` to `andromeda/src/commonMain/composeResources/font/`

**Step 1: Move font files to CMP resources**

```bash
mkdir -p andromeda/src/commonMain/composeResources/font
cp andromeda/src/main/res/font/andromeda_*.ttf andromeda/src/commonMain/composeResources/font/
```

**Step 2: Update AndromedaFonts.kt to use CMP resource loading**

Replace `Font(R.font.andromeda_*)` with `Font(Res.font.andromeda_*)` from Compose Multiplatform resources.

**Step 3: Commit**

```bash
git add andromeda/src/commonMain/
git commit -m "feat: migrate fonts to CMP resources for cross-platform support"
```

---

### Task 5.4: Convert andromeda-icons to KMP

**Files:**
- Rewrite: `andromeda-icons/build.gradle.kts` for KMP
- Restructure: Source from `src/main/java/` to `src/commonMain/kotlin/`
- Convert: XML vector drawables to ImageVector Kotlin source (if not done in Phase 4)

**Step 1: All icons should already be ImageVector Kotlin source from Phase 4. Move to commonMain.**

**Step 2: Remove XML drawable resources (not KMP-compatible)**

**Step 3: Commit**

```bash
git add andromeda-icons/
git commit -m "build: convert andromeda-icons to KMP"
```

---

### Task 5.5: Convert andromeda-illustrations to KMP

**Files:**
- Rewrite: `andromeda-illustrations/build.gradle.kts` for KMP
- Convert: XML vector illustrations to ImageVector Kotlin source or CMP resources

**Step 1: Convert illustrations to ImageVector or use CMP resources API**

**Step 2: Move source to commonMain**

**Step 3: Commit**

```bash
git add andromeda-illustrations/
git commit -m "build: convert andromeda-illustrations to KMP"
```

---

### Task 5.6: Handle Platform-Specific Code with expect/actual

**Files:**
- Create: `andromeda/src/commonMain/kotlin/design/andromedacompose/platform/Platform.kt` (expect)
- Create: `andromeda/src/androidMain/kotlin/design/andromedacompose/platform/Platform.android.kt`
- Create: `andromeda/src/desktopMain/kotlin/design/andromedacompose/platform/Platform.desktop.kt`
- Create: `andromeda/src/iosMain/kotlin/design/andromedacompose/platform/Platform.ios.kt`
- Create: `andromeda/src/wasmJsMain/kotlin/design/andromedacompose/platform/Platform.wasmJs.kt`

**Step 1: Identify any remaining platform-specific code**

After moving to commonMain, identify compilation errors. Common issues:
- Android Activity references
- Android resource loading
- Platform-specific haptic feedback
- Context references

**Step 2: Create expect/actual declarations for platform-specific behaviors**

**Step 3: Commit**

```bash
git add andromeda/src/*/kotlin/
git commit -m "feat: add expect/actual platform abstractions"
```

---

### Task 5.7: Create KMP Catalog App

**Files:**
- Restructure: `catalog/` for CMP (commonMain + platform entry points)
- Create: `catalog/src/commonMain/kotlin/` — shared UI
- Create: `catalog/src/androidMain/` — Android Activity entry point
- Create: `catalog/src/desktopMain/` — Desktop Window entry point
- Create: `catalog/src/wasmJsMain/` — Web entry point
- Create: `catalog/src/iosMain/` — iOS entry point

**Step 1: Rewrite catalog build.gradle.kts for CMP**

**Step 2: Move shared UI to commonMain**

**Step 3: Create platform entry points**

Android:
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CatalogApp() }
    }
}
```

Desktop:
```kotlin
fun main() = application {
    Window(title = "Andromeda Catalog") { CatalogApp() }
}
```

WasmJS:
```kotlin
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Andromeda Catalog") { CatalogApp() }
}
```

**Step 4: Verify each platform compiles**

```bash
./gradlew :catalog:compileKotlinDesktop 2>&1 | tail -5
./gradlew :catalog:compileKotlinWasmJs 2>&1 | tail -5
./gradlew :catalog:compileDebugKotlinAndroid 2>&1 | tail -5
```

**Step 5: Commit**

```bash
git add catalog/
git commit -m "feat: convert catalog to CMP (Android + Desktop + Web + iOS)"
```

---

### Task 5.8: Set Up KMP Publishing

**Files:**
- Modify: `scripts/publish-module.gradle.kts` — update for KMP publications
- Modify: Root `build.gradle.kts` — update nexus publishing

**Step 1: Update publishing script for KMP**

KMP automatically creates publications for each target. Update the publish script to sign all publications and add POM metadata.

**Step 2: Verify publication list**

Run: `./gradlew :andromeda:publishToMavenLocal --dry-run 2>&1 | grep "publish"`

Expected: Publications for android, jvm, iosarm64, iossimulatorarm64, iosx64, wasmjs, kotlinMultiplatform

**Step 3: Commit**

```bash
git add scripts/publish-module.gradle.kts build.gradle.kts
git commit -m "build: update publishing for KMP artifacts"
```

---

### Task 5.9: Cross-Platform Testing and Verification

**Step 1: Build all targets**

```bash
./gradlew assemble 2>&1 | tail -20
```

**Step 2: Run desktop catalog**

```bash
./gradlew :catalog:run
```
Expected: Desktop window opens with catalog app

**Step 3: Build Android APK**

```bash
./gradlew :catalog:assembleDebug
```
Expected: APK at `catalog/build/outputs/apk/debug/`

**Step 4: Build WasmJS**

```bash
./gradlew :catalog:wasmJsBrowserDevelopmentRun
```
Expected: Browser opens with catalog app

**Step 5: Verify iOS framework**

```bash
./gradlew :andromeda:linkDebugFrameworkIosSimulatorArm64
```
Expected: Framework built successfully

---

### Task 5.10: Final Release

**Step 1: Update version to 2.0.0**

Update all module build.gradle.kts files: `publishVersion = "2.0.0"`

**Step 2: Update CLAUDE.md with new project structure**

**Step 3: Full clean build**

Run: `./gradlew clean assemble 2>&1 | tail -20`
Expected: BUILD SUCCESSFUL for all targets

**Step 4: Tag release**

```bash
git add -A
git commit -m "release: v2.0.0 — Andromeda Compose Multiplatform Design System"
git tag v2.0.0
```

---

## Appendix: File Structure After All Phases

```
Andromeda/
  build.gradle.kts                          (root - KMP)
  settings.gradle.kts
  gradle.properties
  gradle/
    libs.versions.toml
    wrapper/
  andromeda/
    build.gradle.kts                        (KMP library)
    src/
      commonMain/
        kotlin/design/andromedacompose/
          AndromedaTheme.kt
          foundation/
            LocalAndromedaContentColor.kt
            LocalAndromedaTextStyle.kt
            ContentEmphasis.kt
            accessibility/
              AccessibilityDefaults.kt
            colors/
              AndromedaColors.kt
              FillColors.kt
              BorderColors.kt
              ContentColors.kt
              IconColors.kt
              DefaultLightColors.kt
              DefaultDarkColors.kt
              tokens/DefaultColorTokens.kt
            typography/
              AndromedaTypography.kt
              AndromedaFonts.kt
              BaseTypography.kt
              body/ caption/ title/
            shape/
              AndromedaShapes.kt
              DefaultShapes.kt
            tokens/
              Spacing.kt
              Elevation.kt
              Motion.kt
              Opacity.kt
            indication/
              AndromedaIndication.kt
          components/
            Text.kt
            Surface.kt
            Icon.kt
            IconButton.kt
            Divider.kt
            BackButton.kt
            buttons/
              Button.kt
              ButtonElevation.kt
            inputs/
              TextField.kt
              TextArea.kt
              ReadonlyTextField.kt
              SearchField.kt
              Select.kt
              field/
            navbar/
              AndromedaNavBar.kt
            selection/
              Checkbox.kt
              RadioButton.kt
              RadioGroup.kt
              Switch.kt
              Slider.kt
            feedback/
              AlertBanner.kt
              Snackbar.kt
              SnackbarHost.kt
              Badge.kt
              ProgressIndicator.kt
              Skeleton.kt
            containers/
              Card.kt
              ListItem.kt
            overlay/
              BottomSheet.kt
              Dialog.kt
              Tooltip.kt
            navigation/
              TabBar.kt
            content/
              Chip.kt
              Avatar.kt
            reveal/
              CircularReveal.kt
              ...
        composeResources/
          font/
            andromeda_*.ttf
      androidMain/
      desktopMain/
      iosMain/
      wasmJsMain/
  andromeda-icons/
    build.gradle.kts                        (KMP library)
    src/commonMain/kotlin/
      design/andromedacompose/icons/
        AndromedaIcons.kt
        system/                             (ArrowBack, Close, etc.)
  andromeda-illustrations/
    build.gradle.kts                        (KMP library)
    src/commonMain/kotlin/
      design/andromedacompose/illustrations/
        AndromedaIllustrations.kt
        Illustration.kt
  catalog/
    build.gradle.kts                        (CMP application)
    src/
      commonMain/kotlin/                    (shared UI)
      androidMain/kotlin/                   (Activity)
      desktopMain/kotlin/                   (Window)
      wasmJsMain/kotlin/                    (CanvasBasedWindow)
      iosMain/kotlin/                       (UIViewController)
  scripts/
    publish-module.gradle.kts
  docs/plans/
    2026-03-01-andromeda-v2-modernization-design.md
    2026-03-01-andromeda-v2-implementation-plan.md
  .github/workflows/
    build_deploy.yml
    gh-pages.yml
```
