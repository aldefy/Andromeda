# KMP/CMP Migration Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Migrate Andromeda from Android-only Compose to Kotlin Multiplatform (KMP) with Compose Multiplatform (CMP), targeting Android + iOS.

**Architecture:** Big-bang migration of all 3 library modules simultaneously. 57 of 74 files move to commonMain unchanged. 8 files need minor platform abstraction. 7 icon XMLs and 5 illustration XMLs convert to ImageVector code. Build system rewrites from Android-only Kotlin DSL to KMP multiplatform.

**Tech Stack:** Kotlin 2.1.10, Compose Multiplatform 1.10.1, Gradle 8.11.1, AGP 8.7.3

**Design doc:** `docs/plans/2026-03-01-kmp-cmp-migration-design.md`

---

## Task 1: Add KMP dependencies to version catalog

**Files:**
- Modify: `gradle/libs.versions.toml`

**Step 1: Add CMP and KMP entries to libs.versions.toml**

Add under `[versions]`:
```toml
compose-multiplatform = "1.10.1"
```

Add under `[plugins]`:
```toml
kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
compose-multiplatform-plugin = { id = "org.jetbrains.compose", version.ref = "compose-multiplatform" }
```

**Step 2: Declare KMP plugins in root build.gradle.kts**

Add to the `plugins {}` block (with `apply false`):
```kotlin
alias(libs.plugins.kotlin.multiplatform) apply false
alias(libs.plugins.compose.multiplatform.plugin) apply false
```

**Step 3: Add KMP flags to gradle.properties**

Append:
```properties
kotlin.mpp.androidSourceSetLayoutVersion=2
```

**Step 4: Verify project syncs**

Run: `./gradlew help`
Expected: BUILD SUCCESSFUL (plugins resolved but not applied yet)

**Step 5: Commit**

```bash
git add gradle/libs.versions.toml build.gradle.kts gradle.properties
git commit -m "build: add KMP/CMP plugin declarations to version catalog"
```

---

## Task 2: Convert andromeda-icons to KMP module

**Files:**
- Rewrite: `andromeda-icons/build.gradle.kts`
- Create: `andromeda-icons/src/commonMain/kotlin/design/andromedacompose/icons/AndromedaIcons.kt`
- Create: `andromeda-icons/src/commonMain/kotlin/design/andromedacompose/icons/AndromedaSystemIcons.kt`
- Create: 7 ImageVector files in `andromeda-icons/src/commonMain/kotlin/design/andromedacompose/icons/`
- Delete: `andromeda-icons/src/main/` (old Android source set)
- Delete: `andromeda-icons/src/main/res/drawable/` (7 XML icons)
- Delete: `andromeda-icons/src/main/res/values/colors.xml`

**Step 1: Rewrite build.gradle.kts for KMP**

Replace entire `andromeda-icons/build.gradle.kts`:
```kotlin
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
            api(libs.phosphor.icons)
        }
    }
}

android {
    namespace = "design.andromedacompose.icons"
    compileSdk = 35
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

extra["PUBLISH_GROUP_ID"] = "design.andromedacompose.icons"
extra["PUBLISH_ARTIFACT_ID"] = "andromeda-icons"
extra["PUBLISH_VERSION"] = "2.0.0-alpha01"
apply(from = "../scripts/publish-module.gradle")
```

**Step 2: Create KMP directory structure**

```bash
mkdir -p andromeda-icons/src/commonMain/kotlin/design/andromedacompose/icons
```

**Step 3: Convert 7 icon XMLs to ImageVector Kotlin files**

For each XML drawable, create a Kotlin file with an `ImageVector.Builder`. The icons are:
- `AlertIcon.kt` — from `andromeda_icon_alert.xml`
- `AlertCircleIcon.kt` — from `andromeda_icon_alert_circle.xml`
- `InfoCircleIcon.kt` — from `andromeda_icon_info_circle.xml`
- `PasswordIcon.kt` — from `andromeda_icon_password.xml`
- `PhotosIcon.kt` — from `andromeda_icon_photos.xml`
- `VisibilityOnIcon.kt` — from `andromeda_icon_visibility_on.xml`
- `VisibilityOffIcon.kt` — from `andromeda_icon_visibility_off.xml`

Each file follows this pattern:
```kotlin
package design.andromedacompose.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _alertIcon: ImageVector? = null
val AlertIcon: ImageVector
    get() {
        if (_alertIcon != null) return _alertIcon!!
        _alertIcon = ImageVector.Builder(
            name = "AlertIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                // Path data converted from XML
            }
        }.build()
        return _alertIcon!!
    }
```

Use the XML path data from each drawable. Convert `android:pathData` to `path {}` DSL calls.

**Step 4: Move AndromedaSystemIcons.kt to commonMain**

Copy from `andromeda-icons/src/main/java/design/andromedacompose/icons/AndromedaSystemIcons.kt` to `andromeda-icons/src/commonMain/kotlin/design/andromedacompose/icons/AndromedaSystemIcons.kt`.

No changes needed — it's already pure ImageVector code.

**Step 5: Rewrite AndromedaIcons.kt for commonMain**

The current `AndromedaIcons.kt` uses `painterResource(R.drawable.*)` which is Android-only. Rewrite to return `ImageVector` references:

```kotlin
package design.andromedacompose.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.nickelaway.phosphoricons.PhosphorIcons

object AndromedaIcons {
    val Visibility: ImageVector get() = VisibilityOnIcon
    val VisibilityOff: ImageVector get() = VisibilityOffIcon
    val InformationCircle: ImageVector get() = InfoCircleIcon
    val Password: ImageVector get() = PasswordIcon
    val Alert: ImageVector get() = AlertIcon
    val AlertCircle: ImageVector get() = AlertCircleIcon
    val Photos: ImageVector get() = PhotosIcon

    /** Pre-built system icons (ArrowBack, Close, Error, Info, Edit, MoreVert) */
    val System = AndromedaSystemIcons

    /** Phosphor Icons (9000+ icons, 6 weights) */
    val Phosphor = PhosphorIcons
}
```

**Step 6: Delete old Android source set**

```bash
rm -rf andromeda-icons/src/main/
```

**Step 7: Verify module builds**

Run: `./gradlew :andromeda-icons:compileKotlinIosArm64 :andromeda-icons:compileReleaseKotlinAndroid`
Expected: BUILD SUCCESSFUL

**Step 8: Commit**

```bash
git add andromeda-icons/
git commit -m "feat: convert andromeda-icons to KMP module with ImageVector icons"
```

---

## Task 3: Convert andromeda-illustrations to KMP module

**Files:**
- Rewrite: `andromeda-illustrations/build.gradle.kts`
- Create: 5 ImageVector files in `andromeda-illustrations/src/commonMain/kotlin/design/andromedacompose/illustrations/`
- Create: `andromeda-illustrations/src/commonMain/kotlin/design/andromedacompose/illustrations/AndromedaIllustrations.kt`
- Create: `andromeda-illustrations/src/commonMain/kotlin/design/andromedacompose/illustrations/Illustration.kt`
- Delete: `andromeda-illustrations/src/main/` (old Android source set)

**Step 1: Rewrite build.gradle.kts for KMP**

Replace entire `andromeda-illustrations/build.gradle.kts`:
```kotlin
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
```

**Step 2: Create KMP directory structure**

```bash
mkdir -p andromeda-illustrations/src/commonMain/kotlin/design/andromedacompose/illustrations
```

**Step 3: Convert 5 illustration XMLs to ImageVector Kotlin files**

These are larger vector graphics (119–711 lines of XML path data). For each:
- `FriendsChattingVector.kt` — from `andromeda_vector_friends_chatting.xml` (711 lines)
- `ManVibingVector.kt` — from `andromeda_vector_man_vibing.xml` (429 lines)
- `WateringPlantsVector.kt` — from `andromeda_vector_watering_plants.xml` (405 lines)
- `WorkspaceVector.kt` — from `andromeda_vector_workspace.xml` (161 lines)
- `WorkingPeopleVector.kt` — from `andromeda_working_people.xml` (119 lines)

Each follows the lazy-init ImageVector.Builder pattern. Start with the smaller ones (WorkingPeople, Workspace) first to validate the conversion approach, then do the larger ones.

**Step 4: Rewrite AndromedaIllustrations.kt**

Replace the enum with an object returning ImageVectors:
```kotlin
package design.andromedacompose.illustrations

import androidx.compose.ui.graphics.vector.ImageVector

object AndromedaIllustrations {
    val FriendsChatting: ImageVector get() = FriendsChattingVector
    val ManVibing: ImageVector get() = ManVibingVector
    val WateringPlants: ImageVector get() = WateringPlantsVector
    val Workspace: ImageVector get() = WorkspaceVector
    val WorkingPeople: ImageVector get() = WorkingPeopleVector
}
```

**Step 5: Rewrite Illustration.kt composable**

```kotlin
package design.andromedacompose.illustrations

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Illustration(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}
```

**Step 6: Delete old Android source set**

```bash
rm -rf andromeda-illustrations/src/main/
```

**Step 7: Verify module builds**

Run: `./gradlew :andromeda-illustrations:compileKotlinIosArm64 :andromeda-illustrations:compileReleaseKotlinAndroid`
Expected: BUILD SUCCESSFUL

**Step 8: Commit**

```bash
git add andromeda-illustrations/
git commit -m "feat: convert andromeda-illustrations to KMP module with ImageVector illustrations"
```

---

## Task 4: Convert andromeda core module to KMP — build file and source sets

**Files:**
- Rewrite: `andromeda/build.gradle.kts`
- Move: 57 pure-Compose files from `src/main/java/` to `src/commonMain/kotlin/`
- Move: 8 TTF fonts from `src/main/res/font/` to `src/commonMain/composeResources/font/`
- Move: `src/main/res/values/strings.xml` to `src/commonMain/composeResources/values/strings.xml`
- Create: `src/androidMain/kotlin/` for Android-specific files
- Create: `src/iosMain/kotlin/` for iOS stubs

**Step 1: Rewrite build.gradle.kts for KMP**

Replace entire `andromeda/build.gradle.kts`:
```kotlin
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
            implementation(compose.animation)
            implementation(compose.components.resources)
            api(project(":andromeda-icons"))
            api(project(":andromeda-illustrations"))
        }
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
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
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

extra["PUBLISH_GROUP_ID"] = "design.andromedacompose"
extra["PUBLISH_ARTIFACT_ID"] = "andromeda"
extra["PUBLISH_VERSION"] = "2.0.0-alpha01"
apply(from = "../scripts/publish-module.gradle")
```

**Step 2: Create KMP directory structure**

```bash
mkdir -p andromeda/src/commonMain/kotlin/design/andromedacompose
mkdir -p andromeda/src/commonMain/composeResources/font
mkdir -p andromeda/src/commonMain/composeResources/values
mkdir -p andromeda/src/androidMain/kotlin/design/andromedacompose
mkdir -p andromeda/src/iosMain/kotlin/design/andromedacompose
```

**Step 3: Move fonts to composeResources**

```bash
cp andromeda/src/main/res/font/*.ttf andromeda/src/commonMain/composeResources/font/
```

**Step 4: Move strings.xml to composeResources**

```bash
cp andromeda/src/main/res/values/strings.xml andromeda/src/commonMain/composeResources/values/strings.xml
```

**Step 5: Move all pure-Compose source files to commonMain**

Move the entire package tree, preserving directory structure:
```bash
# Move everything first
cp -r andromeda/src/main/java/design/andromedacompose/* andromeda/src/commonMain/kotlin/design/andromedacompose/
```

Then move Android-specific files to androidMain (done in Task 5).

**Step 6: Create androidMain AndroidManifest.xml**

Create `andromeda/src/androidMain/AndroidManifest.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest />
```

**Step 7: Verify directory structure is correct**

```bash
find andromeda/src/commonMain -type f | head -20
find andromeda/src/androidMain -type f
find andromeda/src/iosMain -type f
```

**Step 8: Commit (structure only, won't compile yet)**

```bash
git add andromeda/
git commit -m "build: restructure andromeda core to KMP source sets"
```

---

## Task 5: Platform-abstract the 8 files that need changes

**Files to modify in commonMain:**
1. `andromeda/src/commonMain/kotlin/design/andromedacompose/foundation/typography/AndromedaFonts.kt`
2. `andromeda/src/commonMain/kotlin/design/andromedacompose/components/inputs/TextField.kt`
3. `andromeda/src/commonMain/kotlin/design/andromedacompose/components/reveal/CircularReveal.kt`
4. `andromeda/src/commonMain/kotlin/design/andromedacompose/ComposeExtensions.kt` (in `design` subpackage)
5. `andromeda/src/commonMain/kotlin/design/andromedacompose/components/internal/Previews.kt`

**Files to create:**
- `andromeda/src/androidMain/kotlin/design/andromedacompose/components/internal/Previews.kt`

### Step 1: Fix AndromedaFonts.kt — CMP resource loading

Replace `R.font.*` with CMP `Res.font.*`:
```kotlin
package design.andromedacompose.foundation.typography

import andromeda.composeapp.generated.resources.Res
import andromeda.composeapp.generated.resources.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font

@Composable
fun andromedaFontFamily(): FontFamily = FontFamily(
    Font(Res.font.andromeda_black, FontWeight.Black),
    Font(Res.font.andromeda_bold, FontWeight.Bold),
    Font(Res.font.andromeda_extrabold, FontWeight.ExtraBold),
    Font(Res.font.andromeda_light, FontWeight.Light),
    Font(Res.font.andromeda_medium, FontWeight.Medium),
    Font(Res.font.andromeda_regular, FontWeight.W400),
    Font(Res.font.andromeda_semibold, FontWeight.SemiBold),
    Font(Res.font.andromeda_thin, FontWeight.Thin),
)
```

Note: CMP `Font()` requires `@Composable` context. The `AndromedaFonts` val becomes an `andromedaFontFamily()` composable function. Update all call sites (in `AndromedaTheme` and typography setup) to call it within composition.

The generated resource class name depends on the module name. For module `andromeda`, the import will be `andromeda.generated.resources.Res`. Verify the exact import after first build.

### Step 2: Fix TextField.kt — remove R.string reference

Replace line 159:
```kotlin
// Before:
val errorMessage = stringResource(R.string.input_field_default_error)

// After — use CMP string resource or parameter default:
val errorMessage = org.jetbrains.compose.resources.stringResource(Res.string.input_field_default_error)
```

Alternatively, make it a parameter with a default:
```kotlin
fun TextField(
    // ... existing params ...
    errorMessage: String = "Invalid input",
)
```

The parameter default is simpler and avoids the resource system entirely for one string.

### Step 3: Fix CircularReveal.kt — replace pointerInteropFilter

Replace the `pointerInteropFilter` block (Android-only `MotionEvent`) with CMP-compatible `pointerInput`:

```kotlin
// Before:
import android.view.MotionEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter

Box(
    modifier.pointerInteropFilter {
        offset = when (it.action) {
            MotionEvent.ACTION_DOWN -> Offset(it.x, it.y)
            else -> null
        }
        false
    }
)

// After:
import androidx.compose.ui.input.pointer.pointerInput

Box(
    modifier.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                val change = event.changes.firstOrNull()
                if (change != null && change.pressed && !change.previousPressed) {
                    offset = change.position
                }
            }
        }
    }
)
```

Remove the `@OptIn(ExperimentalComposeUiApi::class)` annotation and the `MotionEvent` import.

### Step 4: Fix ComposeExtensions.kt — remove Android dependencies

Two Android-specific APIs to replace:

1. `SystemClock.uptimeMillis()` → Use Kotlin's `System.nanoTime() / 1_000_000`:
```kotlin
// Before:
import android.os.SystemClock
val now = SystemClock.uptimeMillis()

// After:
val now = System.nanoTime() / 1_000_000
```

Note: `System.nanoTime()` works in KMP on JVM and iOS (maps to platform-appropriate monotonic clock via Kotlin/Native).

Actually, for KMP, use `kotlin.time`:
```kotlin
import kotlin.time.TimeSource

private val timeSource = TimeSource.Monotonic
private val startMark = timeSource.markNow()

// In debounced():
val now = startMark.elapsedNow().inWholeMilliseconds
```

2. `ColorUtils.HSLToColor()` / `ColorUtils.colorToHSL()` → Pure Kotlin implementation:
```kotlin
// Replace the invert() extension function with pure Kotlin HSL conversion
fun ComposeColor.invert(): ComposeColor {
    val r = red; val g = green; val b = blue
    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    var h: Float
    val s: Float
    val l = (max + min) / 2f

    if (max == min) {
        h = 0f; s = 0f
    } else {
        val d = max - min
        s = if (l > 0.5f) d / (2f - max - min) else d / (max + min)
        h = when (max) {
            r -> (g - b) / d + (if (g < b) 6f else 0f)
            g -> (b - r) / d + 2f
            else -> (r - g) / d + 4f
        }
        h /= 6f
    }

    val invertedL = 1f - l
    return hslToColor(h, s, invertedL, alpha)
}

private fun hslToColor(h: Float, s: Float, l: Float, alpha: Float): ComposeColor {
    val c = (1f - kotlin.math.abs(2f * l - 1f)) * s
    val x = c * (1f - kotlin.math.abs((h * 6f) % 2f - 1f))
    val m = l - c / 2f
    val (r, g, b) = when {
        h < 1f / 6f -> Triple(c, x, 0f)
        h < 2f / 6f -> Triple(x, c, 0f)
        h < 3f / 6f -> Triple(0f, c, x)
        h < 4f / 6f -> Triple(0f, x, c)
        h < 5f / 6f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }
    return ComposeColor(r + m, g + m, b + m, alpha)
}
```

Remove `import androidx.core.graphics.ColorUtils` and `@OptIn(ExperimentalUnsignedTypes::class)`.

### Step 5: Move Previews.kt to androidMain

Move `Previews.kt` from commonMain to androidMain since `@Preview` annotation is Android-only:
```bash
mkdir -p andromeda/src/androidMain/kotlin/design/andromedacompose/components/internal/
mv andromeda/src/commonMain/kotlin/design/andromedacompose/components/internal/Previews.kt \
   andromeda/src/androidMain/kotlin/design/andromedacompose/components/internal/Previews.kt
```

### Step 6: Delete old Android source set

```bash
rm -rf andromeda/src/main/java
rm -rf andromeda/src/main/res
```

Keep `andromeda/src/main/AndroidManifest.xml` or move to `androidMain` (already handled in Task 4 Step 6).

### Step 7: Verify module builds

Run: `./gradlew :andromeda:compileKotlinIosArm64 :andromeda:compileReleaseKotlinAndroid`
Expected: BUILD SUCCESSFUL

Fix any compilation errors (likely import path changes for generated resources).

### Step 8: Commit

```bash
git add andromeda/
git commit -m "feat: platform-abstract 8 files for KMP compatibility"
```

---

## Task 6: Update catalog app for KMP changes

**Files:**
- Modify: `catalog/build.gradle.kts` — update dependencies for new module APIs
- Modify: `catalog/src/main/java/design/andromedacompose/catalog/screens/MainScreen.kt` — `AndromedaIcons.Alert` now returns `ImageVector` not `Painter`
- Modify: Any other catalog files referencing `AndromedaIcons.*` or `AndromedaIllustrations.*`

**Step 1: Update MainScreen.kt icon references**

The `Icon()` composable in Andromeda already has an `ImageVector` overload, so `Icon(AndromedaIcons.Alert, null)` should work without changes since `Alert` now returns `ImageVector`. Verify this compiles.

**Step 2: Update illustration references in catalog**

Search for any `illustration.resource()` calls and replace with `illustration` directly (now ImageVector).

**Step 3: Update catalog's CatalogTheme for new font loading**

If `AndromedaFonts` became `andromedaFontFamily()` (composable), update the theme setup in the catalog accordingly.

**Step 4: Verify catalog builds and installs**

Run: `./gradlew :catalog:assembleDebug`
Expected: BUILD SUCCESSFUL

Run: `./gradlew :catalog:installDebug`
Expected: App installs on connected device

**Step 5: Commit**

```bash
git add catalog/
git commit -m "fix: update catalog for KMP module API changes"
```

---

## Task 7: Verify all targets compile

**Step 1: Full build — all platforms**

Run: `./gradlew build`
Expected: BUILD SUCCESSFUL for all modules × all targets

**Step 2: Run existing tests if any**

Run: `./gradlew test`
Expected: All tests pass (or skip gracefully if no tests exist)

**Step 3: Verify iOS framework generation**

Run: `./gradlew :andromeda:linkDebugFrameworkIosSimulatorArm64`
Expected: Framework builds without errors

**Step 4: Verify Android artifact**

Run: `./gradlew :andromeda:assembleRelease`
Expected: AAR builds without errors

**Step 5: Commit (if any fixes were needed)**

```bash
git add -A
git commit -m "fix: resolve cross-platform build issues"
```

---

## Task 8: Update publishing for KMP multi-target

**Files:**
- Modify: `scripts/publish-module.gradle` — ensure it works with KMP's auto-generated publications
- Or: Inline publishing config into each module's `build.gradle.kts`

**Step 1: Verify KMP auto-publications**

Run: `./gradlew :andromeda:publishToMavenLocal`
Check: `~/.m2/repository/design/andromedacompose/andromeda/` should contain:
- `andromeda-2.0.0-alpha01.module` (Gradle metadata)
- `andromeda-android-2.0.0-alpha01.aar`
- `andromeda-iosarm64-2.0.0-alpha01.klib`
- `andromeda-iossimulatorarm64-2.0.0-alpha01.klib`
- `andromeda-iosx64-2.0.0-alpha01.klib`

**Step 2: Update publish script for multi-publication POM**

The existing `publish-module.gradle` configures POM metadata for the `release` publication. KMP generates multiple publications. The script may need to iterate over all publications:

```groovy
publishing {
    publications.withType(MavenPublication) {
        pom {
            name = PUBLISH_ARTIFACT_ID
            // ... same POM metadata as before
        }
    }
}
```

Test: `./gradlew :andromeda:publishAllPublicationsToMavenLocal`

**Step 3: Verify signing works for all publications**

Run: `./gradlew :andromeda:publishAllPublicationsToLocalStagingRepository -Psigning.gnupg.keyName=F30A3C2E`
Expected: All 5 artifacts (root + android + 3 iOS) signed and staged

**Step 4: Commit**

```bash
git add scripts/ andromeda/ andromeda-icons/ andromeda-illustrations/
git commit -m "build: update publishing for KMP multi-target artifacts"
```

---

## Task 9: Clean up and final verification

**Step 1: Remove unused Android-only dependencies**

Check `libs.versions.toml` for dependencies no longer needed in library modules (e.g., `compose-activity`, `core-ktx` if only used by catalog).

**Step 2: Run ktlint**

Run: `./gradlew ktlintCheck`
Fix any formatting issues.

**Step 3: Full clean build**

Run: `./gradlew clean build`
Expected: BUILD SUCCESSFUL

**Step 4: Install and test catalog on device**

Run: `./gradlew :catalog:installDebug`
Manual test: Navigate all screens, toggle theme, verify icons/illustrations render correctly.

**Step 5: Commit final cleanup**

```bash
git add -A
git commit -m "chore: cleanup and verify KMP migration"
```

---

## Notes

### Breaking Changes for Library Consumers
1. Custom `AndromedaIcons.*` properties return `ImageVector` instead of `Painter` — wrap with `rememberVectorPainter()` if needed
2. `AndromedaIllustrations.*` returns `ImageVector` instead of `IllustrationResource` — use `Image(imageVector = ...)` instead of `Image(painter = ...)`
3. `AndromedaFonts` val becomes `andromedaFontFamily()` composable function

### What's NOT in this plan (future work)
- iOS catalog app (Xcode project + SwiftUI entry point)
- Desktop/WasmJS targets
- CI/CD macOS runner for iOS builds
- Restructuring catalog as CMP shared app (currently stays Android-only)
- Phase 4 components (Button variants, Dialog, Toast, etc.) — will be built as KMP from day one after this migration

### Potential Blockers
- CMP 1.10.1 + Kotlin 2.1.10 compatibility — if build fails, may need Kotlin 2.1.20
- Phosphor Icons KMP support verification — `com.adamglin:phosphor-icon:1.0.0` claims all targets but needs build verification
- Large illustration ImageVector conversion (711 lines of XML paths) — may need tooling assistance
- Groovy publish script + KMP multi-publication — may need full rewrite to Kotlin DSL if Groovy can't handle it
