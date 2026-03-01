# Andromeda KMP/CMP Migration Design

## Goal

Migrate Andromeda from an Android-only Compose design system to a Kotlin Multiplatform (KMP) library using Compose Multiplatform (CMP), targeting Android + iOS initially.

## Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Target platforms | Android + iOS | Most common KMP use case. Desktop/WasmJS added later. |
| Migration approach | Big-bang (all modules at once) | 81% of code is pure Compose. Module dependencies force ordering anyway. |
| XML drawables (13) | Convert to ImageVector code | Platform-agnostic, no resource system dependency. |
| Fonts (8 TTFs) | CMP composeResources | Official CMP 1.10+ font loading. Cross-platform out of the box. |
| CircularReveal | Rewrite pointerInteropFilter to pointerInput | 5-line change makes it fully CMP-compatible. |
| Catalog app | Shared CMP app (Android + iOS) | Proves the library works cross-platform. |
| Compose Multiplatform version | 1.10.1 | Latest stable. Requires Kotlin 2.1.0+ (we have 2.1.10). |
| Phosphor Icons | Already KMP-ready | com.adamglin:phosphor-icon supports all CMP targets. |

## Current State

- 74 Kotlin files across 3 library modules
- 57 files (81%) are pure Compose — move to commonMain as-is
- 8 files need minor platform abstraction
- 13 XML vector drawables need conversion to ImageVector
- 8 TTF font files need migration to composeResources
- Build system: Gradle 8.11.1, Kotlin 2.1.10, AGP 8.7.3 — all KMP-compatible
- Publishing: Groovy script, single-artifact per module — needs rewrite for multi-target

## Target Module Structure

```
andromeda/                          <- KMP library (core)
  build.gradle.kts                  <- kotlin("multiplatform") + android("library")
  src/
    commonMain/kotlin/              <- 57 pure Compose files
    commonMain/composeResources/
      font/                         <- 8 TTF files
      values/strings.xml            <- error strings
    androidMain/kotlin/             <- Previews.kt
    iosMain/kotlin/                 <- empty initially

andromeda-icons/                    <- KMP library
  build.gradle.kts
  src/
    commonMain/kotlin/              <- ImageVector icons + AndromedaSystemIcons + Phosphor re-export

andromeda-illustrations/            <- KMP library
  build.gradle.kts
  src/
    commonMain/kotlin/              <- 5 ImageVector illustrations + Illustration composable

catalog/
  composeApp/                       <- Shared CMP demo app
    build.gradle.kts                <- kotlin("multiplatform") + android("application")
    src/
      commonMain/kotlin/            <- All catalog screens
      androidMain/kotlin/           <- LauncherActivity, status bar scrim
      commonMain/composeResources/  <- catalog fonts
  iosApp/                           <- Xcode project with SwiftUI entry point
```

## Files That Need Changes

### Tier 1: Move to commonMain as-is (57 files)
All foundation tokens, color system, typography styles, shapes, composition locals,
Text, Surface, Icon, Divider, Button, ButtonElevation, LoadingSpinner,
Checkbox, RadioButton, Switch, ReadOnlyTextField, field layout components,
CircularRevealScope, CircularRevealAnimationItem, CircularRevealShape, Extension.kt,
BackButton, IconButton, ConstrainedColumn, LocalScaffoldPadding, AndromedaTheme.

### Tier 2: Minor changes needed (8 files)

1. **AndromedaFonts.kt** — Replace `R.font.*` with CMP `Res.font.*` resource loading
2. **TextField.kt** — Replace `R.string.*` with CMP `Res.string.*` or parameter default
3. **CircularReveal.kt** — Replace `pointerInteropFilter` + `MotionEvent` with `pointerInput` + `awaitFirstDown`
4. **ComposeExtensions.kt** — Replace `SystemClock.uptimeMillis()` with `System.currentTimeMillis()` or `Clock.System`. Port `ColorUtils.HSLToColor()` to pure Kotlin.
5. **AndromedaNavBar.kt** — `WindowInsets.statusBars` is CMP-compatible. Minor adjustments for platforms without status bars.
6. **Previews.kt** — Move to androidMain (Android-only @Preview annotation). CMP 1.10+ has common @Preview but we can use it later.
7. **AndromedaIcons.kt** — Remove `painterResource(R.drawable.*)`. Replace with `ImageVector` references to the converted icons.
8. **AndromedaIllustrations.kt** — Same as icons. Refactor to ImageVector-based API.

### Tier 3: Resource conversions (13 XML files)

**Icons (8 XML -> ImageVector):**
- andromeda_icon_alert.xml
- andromeda_icon_alert_circle.xml
- andromeda_icon_info_circle.xml
- andromeda_icon_password.xml
- andromeda_icon_photos.xml
- andromeda_icon_placeholder.xml
- andromeda_icon_visibility_off.xml
- andromeda_icon_visibility_on.xml

**Illustrations (5 XML -> ImageVector):**
- andromeda_vector_friends_chatting.xml (711 lines)
- andromeda_vector_man_vibing.xml (429 lines)
- andromeda_vector_watering_plants.xml (405 lines)
- andromeda_vector_workspace.xml (161 lines)
- andromeda_working_people.xml (119 lines)

### Tier 4: Build system changes

1. **gradle/libs.versions.toml** — Add compose-multiplatform version, kotlin-multiplatform plugin
2. **build.gradle.kts (root)** — Declare kotlin-multiplatform plugin
3. **andromeda/build.gradle.kts** — Android library -> KMP multiplatform library
4. **andromeda-icons/build.gradle.kts** — Same
5. **andromeda-illustrations/build.gradle.kts** — Same
6. **scripts/publish-module.gradle** — Rewrite for KMP multi-target publishing (or inline into each module)
7. **gradle.properties** — Add KMP flags
8. **catalog/** — Restructure as CMP shared app with iosApp

## Publishing

KMP generates multiple publications per module. For each library module:
- `andromeda` (root metadata)
- `andromeda-android` (AAR)
- `andromeda-iosarm64` (klib)
- `andromeda-iossimulatorarm64` (klib)
- `andromeda-iosx64` (klib)

Total: 5 artifacts per module x 3 modules = 15 Maven Central publications.

The Groovy publish-module.gradle will be replaced with Kotlin DSL publishing blocks in each module's build.gradle.kts. The KMP plugin auto-generates publications per target. nexus-publish at root level handles staging/release.

## Compatibility

| Tool | Version | KMP Support |
|------|---------|-------------|
| Gradle | 8.11.1 | Full |
| Kotlin | 2.1.10 | Full |
| AGP | 8.7.3 | Full (androidTarget) |
| Compose Multiplatform | 1.10.1 (new) | Full |
| Phosphor Icons | 1.0.0 | Full (Android, iOS, JVM, JS, WasmJS) |
| nexus-publish | 2.0.0 | Full (multi-publication) |
| Dokka | 1.9.20 | Full (multiplatform-aware) |

## Migration Path for Existing Consumers

### Maven Coordinates

**Before (v1.x, Android-only):**
```kotlin
implementation("design.andromedacompose:andromeda:1.5.0")
implementation("design.andromedacompose.icons:andromeda-icons:1.5.0")
implementation("design.andromedacompose.illustrations:andromeda-illustrations:1.5.0")
```

**After (v2.0, KMP):**
```kotlin
// SAME coordinates — Gradle resolves the -android variant automatically
implementation("design.andromedacompose:andromeda:2.0.0")
implementation("design.andromedacompose.icons:andromeda-icons:2.0.0")
implementation("design.andromedacompose.illustrations:andromeda-illustrations:2.0.0")
```

Android consumers don't change their dependency declarations. When a KMP library publishes to Maven Central, the root metadata POM redirects Android projects to the `-android` artifact automatically via Gradle module metadata. This is built into Kotlin Multiplatform's publishing model.

### API Compatibility

| Aspect | v1.x (Android) | v2.0 (KMP) | Breaking? |
|--------|----------------|------------|-----------|
| Package name | `design.andromedacompose` | `design.andromedacompose` | No |
| Theme API | `AndromedaTheme(colors, fontFamily, ...)` | Same | No |
| Token access | `AndromedaTheme.colors.*` | Same | No |
| Components | `Button()`, `Text()`, `Surface()`, etc. | Same signatures | No |
| Icons (custom) | `AndromedaIcons.Alert` returns `Painter` | Returns `ImageVector` | **Yes** |
| Icons (system) | `AndromedaIcons.System.ArrowBack` | Same (already ImageVector) | No |
| Icons (Phosphor) | `AndromedaIcons.Phosphor.Regular.Heart` | Same | No |
| Illustrations | `AndromedaIllustrations.FriendsChatting.resource()` returns `Painter` | Returns `ImageVector` | **Yes** |
| Fonts | Bundled via R.font | Bundled via composeResources | No (transparent) |

### Breaking Changes (2 items)

1. **Custom icons return type**: `Painter` -> `ImageVector`. Fix: wrap with `rememberVectorPainter()` if you need a Painter.
2. **Illustration API**: `IllustrationResource.resource()` signature changes. Fix: use `Image(imageVector = ..., ...)` instead of `Image(painter = ..., ...)`.

Both are minor and documented in release notes. The core theme/token/component API is identical.

### Migration Guide for v1.x -> v2.0

```markdown
1. Update dependency version to 2.0.0 (coordinates unchanged)
2. If using custom AndromedaIcons (Alert, Photos, etc.):
   - Change `Icon(painter = AndromedaIcons.Alert, ...)`
   - To `Icon(imageVector = AndromedaIcons.Alert, ...)`
3. If using AndromedaIllustrations:
   - Change `Image(painter = illustration.resource(), ...)`
   - To `Image(imageVector = illustration.imageVector, ...)`
4. Everything else (theme, colors, typography, tokens, components) is unchanged.
```

## Risk Assessment

| Risk | Mitigation |
|------|-----------|
| Illustration ImageVector conversion (1825 lines XML) | Use composable-to-imagevector tooling. Test visual output. |
| iOS font loading differences | CMP composeResources handles this. Test on simulator. |
| CI/CD needs iOS build capability | Add macOS runner for iOS targets. Skip iOS in Linux CI. |
| Publishing 15 artifacts to Maven Central | Test with local staging first. nexus-publish handles batching. |
| Custom icon Painter->ImageVector break | Minor, documented. `rememberVectorPainter()` as compat bridge. |
| Existing Android consumers confused by KMP | Same Maven coordinates, same API. Gradle resolves automatically. |
