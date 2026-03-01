# Andromeda — Compose Design System

## Project Identity
- **Andromeda** = open-source non-Material Jetpack Compose design system library
- Package: `design.andromedacompose` | Maven: `design.andromedacompose:andromeda`
- Author: Adit Lal (aldefy) | License: MIT | Version: 1.5.0
- Android-only (Kotlin 1.7.20, Compose compiler 1.3.2, AGP 7.3.1)
- Published to Maven Central via Sonatype s01

## Module Structure
```
andromeda/              → Core library (foundation tokens + components)
andromeda-icons/        → 7 icon vector drawables
andromeda-illustrations/→ 5 illustration vector drawables + Illustration() composable
catalog/                → Demo app showcasing all components
```

## Architecture — Foundation Layer

### Theme Entry Point
```kotlin
AndromedaTheme(shapes, fontFamily, colors, typography) { content }
// Access: AndromedaTheme.colors / .typography / .shapes
```

### Color System (6 semantic groups)
- `AndromedaColors` → primaryColors / secondaryColors / tertiaryColors / borderColors / iconColors / contentColors
- Each group has states: active, background, error, mute, pressed, alt
- Light/dark via `defaultLightColors()` / `defaultDarkColors()`
- Raw hex in `DefaultColorTokens` — primary blue #2196F3
- All properties use `mutableStateOf(structuralEqualityPolicy())` for runtime theme switching

### Typography (8 exposed styles)
- `BaseTypography` interface → concrete style classes → `TextStyle` conversion
- 8 weights shipped as fonts: R.font.andromeda_black → andromeda_thin
- Hero(28sp), ModerateBold(18sp), ModerateDemi(18sp), SmallDemi(16sp), BodyModerate(16sp), BodySmall(14sp), CaptionBook(14sp), CaptionDemi(14sp)

### Shapes
- small(4dp), normal(6dp), large(12dp), bottomSheet(16dp top), button(8dp), dialog(8dp)

### ContentEmphasis
- Normal / Minor(0.80) / Subtle(0.66) / Disabled(0.48)
- Maps to content color variants or alpha reduction

### CompositionLocals
- `LocalColors`, `LocalTypography`, `LocalShapes`, `LocalContentEmphasis`

## Architecture — Component Layer

### Non-Material Components (uses BasicText/BasicTextField)
- `Text()` — wraps BasicText with emphasis + color resolution
- `Surface()` — custom shadow+clip+background (NOT Material Surface)
- `Button()` — custom with elevation animation, 44dp min height
- `TextField()` — BasicTextField with custom FieldContent MeasurePolicy layout
- `Icon()` — 3 overloads (ImageVector/Bitmap/Painter), optional click
- `IconButton()`, `Divider()`, `BackButton()`

### Material Leaks (inconsistencies)
- `NavBar` uses Material `Surface` (not Andromeda's)
- `BackButton` uses Material `IconButton`
- `ContentColors.kt` re-exports Material's `LocalContentColor`
- `Button.kt` imports `material.contentColorFor`

### Additional Components
- `CircularReveal<T>()` — animated state transition with circular clip (credit: Benjamin Monjoie)
- `DatePickerInputField()` — Android DatePickerDialog + ReadonlyTextField
- `ReadonlyTextField()` — click-intercepted field for pickers

## Known Bugs / Issues
1. `PrimaryColors.copy()` has parameter order bug (active/background swapped in constructor)
2. `ButtonElevation` has dead code (empty LaunchedEffect body)
3. `DatePickerInputField` creates unused `dialogState` from compose-material-dialogs
4. Timber dependency declared but never used in library code
5. Lottie + threetenabp + compose-material-dialogs exposed as `api` — leak to consumers

## Build
- Groovy build files (.gradle not .gradle.kts)
- `./gradlew assembleRelease` — build library
- `./gradlew :catalog:assembleDebug` — build demo app
- `./gradlew ktlintCheck` — code formatting

## Outdated Dependencies (needs modernization)
- Kotlin 1.7.20 → 2.x, AGP 7.3.1 → 8.x, Compose BOM → latest
- CI actions v2 → v4
- `rememberRipple()` deprecated in newer Compose
- `ExperimentalFoundationApi` on BringIntoView — unstable API
