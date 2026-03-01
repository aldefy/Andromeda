# Andromeda v2.0 — Modernization Design Document

**Author:** Adit Lal (aldefy)
**Date:** 2026-03-01
**Status:** Approved

---

## 1. Overview

Andromeda is an open-source non-Material Jetpack Compose design system library. It has been dormant for ~4 years. This document describes the plan to modernize it from a 2022-era Android-only library (Kotlin 1.7.20, Compose compiler 1.3.2) to a 2026 Compose Multiplatform design system targeting Android, iOS, Desktop, and Web.

### Goals

- Bring all toolchain dependencies to 2026 latest stable
- Remove all Material Design and unnecessary third-party dependencies
- Fill missing design tokens (elevation, motion, spacing, opacity)
- Expand from ~12 components to ~35+ production-ready components
- Ship a comprehensive ImageVector-based icon system (Phosphor Icons)
- Bake accessibility into every component from day one (EAA 2025 compliant)
- Convert to Kotlin Multiplatform / Compose Multiplatform (Android + iOS + Desktop + WasmJS)
- Migrate build system to Kotlin DSL + Version Catalog

### Non-Goals

- Material Design interop layer (explicitly excluded)
- Figma plugin or design-to-code tooling (future work)
- Server-driven UI support (out of scope)

---

## 2. Current State Audit

### Toolchain (all critically outdated)

| Dependency | Current | Target |
|-----------|---------|--------|
| Kotlin | 1.7.20 | 2.1.x |
| AGP | 7.3.1 | 8.7.x |
| Gradle | 7.4 | 8.11+ |
| Compose BOM | 2024.02.00 | 2025.x latest stable |
| Compose Compiler | 1.3.2 | Built-in (Kotlin 2.0+ compiler plugin) |
| compileSdk | 31-34 (mixed) | 35 |
| minSdk | 21-26 (mixed) | 26 |
| Build files | Groovy (.gradle) | Kotlin DSL (.gradle.kts) + libs.versions.toml |
| CI Actions | v2, Ubuntu 18.04 | v4, ubuntu-latest |

### Module Structure

```
andromeda/              -> Core library (tokens + components)
andromeda-icons/        -> 7 icon vector drawables
andromeda-illustrations/-> 5 illustration vector drawables
catalog/                -> Demo app
```

### Material Dependency Leaks (11 files)

| File | Material Imports |
|------|-----------------|
| BackButton.kt | material.Icon, material.IconButton, material.icons.filled.ArrowBack |
| Button.kt | material.contentColorFor, material.ripple.rememberRipple |
| Icon.kt | material.ripple.rememberRipple |
| IconButton.kt | material.ripple.rememberRipple |
| DatePickerInputField.kt | material.icons.filled.Close, material.icons.filled.DateRange |
| FieldMessage.kt | material.icons.filled.Error, material.icons.filled.Info |
| TextField.kt | material.icons.filled.Close, material.icons.filled.Edit, material.icons.filled.MoreVert |
| AndromedaNavBar.kt | material.Surface, material.Text, material.ContentAlpha, material.LocalContentAlpha |
| Surface.kt | material.LocalContentColor, material.contentColorFor |
| ContentColors.kt | material.LocalContentColor |
| TextStyle.kt | material.LocalTextStyle, material.ProvideTextStyle |

### Unused Third-Party Dependencies (exposed as `api`)

| Dependency | Status |
|-----------|--------|
| Lottie 5.2.0 / Lottie-Compose 4.2.2 | Zero imports in library code |
| Timber 5.0.1 | Zero imports in library code |
| compose-material-dialogs 0.9.0 | Only creates unused `dialogState` in DatePickerInputField |
| threetenabp 1.3.1 | Unnecessary — minSdk 26 has java.time |

### Known Bugs

1. `PrimaryColors.copy()` / `SecondaryColors.copy()` / `TertiaryColors.copy()` — active/background parameters swapped in constructor call
2. `ButtonElevation` — empty LaunchedEffect body (dead code)
3. `DatePickerInputField` — creates unused `dialogState`
4. `BorderColors.copy()` — missing default parameters

### Existing Token Coverage

| Token Type | Status | Details |
|-----------|--------|---------|
| Colors | Complete | 6 semantic groups x 6 states, light/dark |
| Typography | Complete | 8 exposed styles, 15 internal style classes |
| Shapes | Complete | 6 shape tokens |
| Spacing | Minimal | Only OneX=4dp with multiplier |
| Elevation | Missing | Hardcoded per-component |
| Motion/Animation | Missing | Hardcoded durations |
| Opacity | Missing | Hardcoded values |

### Component Coverage (12 of ~35+ needed)

**Existing:** Text, Surface, Button, IconButton, Icon, BackButton, Divider, TextField, ReadonlyTextField, DatePickerInputField, AndromedaNavBar, CircularReveal

**Missing (Tier 1-4 listed in Phase 4 below)**

---

## 3. Research Findings

### Community Pain Points (Reddit, developer blogs, Kotlin Slack)

1. **The "Missing Middle Layer"** — No standard layer between Compose Foundation (too primitive) and Material (too opinionated). Developers must rebuild complex behaviors (focus, keyboard nav, a11y) from scratch. This is the #1 pain point.

2. **Material3 Color Overwhelm** — 200+ color variations confuse developers. Simpler semantic groupings (like Andromeda's 6-group system) are preferred.

3. **TextField Padding Wars** — Material's hardcoded internal padding is the most complained-about Compose issue. Custom TextField implementations are in high demand.

4. **Ripple Customization** — API has changed multiple times, hardware-clamped alpha on API 28+. Developers want simple interaction indication.

5. **Material Icons Extended is Dead** — No longer maintained. The ecosystem is moving to standalone icon libraries with ImageVector generation.

6. **KMP is Table Stakes** — Every competing design system has gone multiplatform. Android-only is increasingly niche.

### Competitive Landscape

| Library | Focus | Platform | Differentiator |
|---------|-------|----------|---------------|
| Compose Unstyled | Renderless accessible components | KMP | Behavior-only, zero styling |
| Orbit (Kiwi) | Full DS (archived May 2025) | Android | Strong semantic naming |
| Jewel (JetBrains) | IntelliJ theme recreation | Desktop | Deep platform integration |
| Compose Cupertino | iOS-native look | KMP | Platform-adaptive |
| Carbon Compose | IBM Carbon | KMP | Enterprise-grade |

**Andromeda's differentiator:** Opinionated-but-customizable tokens with a cohesive visual language. Not purely renderless (like Unstyled) but not tied to Material or any platform's native look. A true independent design system.

### Icon System Research

**Recommendation: Phosphor Icons (MIT license)**
- 9,000+ icons across 6 weight variants (Thin/Light/Regular/Bold/Fill/Duotone)
- Weight variants enable hierarchy through icon weight (unique advantage)
- Existing Compose Multiplatform library: compose-phosphor-icon
- Pre-generated ImageVector = zero runtime overhead, tree-shakeable, KMP-compatible

### Accessibility Requirements

- EAA (European Accessibility Act) went into effect June 2025
- Every component needs: proper `role` semantics, 48dp minimum touch targets, `mergeDescendants` for compound components, `stateDescription` for stateful elements
- Live regions for dynamic feedback (toasts, alerts)
- Focus indicators and keyboard navigation (critical for desktop/web)

---

## 4. Architecture Decisions

### AD-1: Remove All Material Dependencies

**Decision:** Replace every Material import with custom Andromeda implementations.

**Rationale:** Andromeda's identity is non-Material. Material leaks cause consumer dependency conflicts and undermine the library's value proposition.

**Replacements:**
- `material.ripple.rememberRipple()` -> custom `Indication` on Foundation
- `material.contentColorFor()` -> Andromeda's own color resolution
- `material.LocalContentColor` -> `LocalAndromedaContentColor`
- `material.LocalTextStyle` / `ProvideTextStyle` -> custom CompositionLocals
- Material Icons (ArrowBack, Close, etc.) -> add to AndromedaIcons

### AD-2: Remove All Unnecessary Third-Party Dependencies

**Decision:** Strip Lottie, Timber, compose-material-dialogs, threetenabp from core.

**Rationale:** None are used in library code. A design system should have zero opinion on animation/date/logging libraries.

### AD-3: Phosphor Icons via Pre-Generated ImageVector

**Decision:** Ship Phosphor Icons as Kotlin-generated ImageVector source, split by category module.

**Rationale:** KMP-compatible, tree-shakeable, 6 weight variants for hierarchy, MIT license, largest coverage.

### AD-4: Kotlin DSL + Version Catalog

**Decision:** Migrate all build files to .gradle.kts with libs.versions.toml.

**Rationale:** Type-safe, better IDE support, standard in KMP ecosystem.

### AD-5: Accessibility First

**Decision:** Every component ships with proper semantics, touch targets, and keyboard navigation from day one.

**Rationale:** EAA 2025 makes this a legal requirement. Retrofitting a11y is far more expensive than building it in.

### AD-6: Slot-Based Component APIs

**Decision:** All components use slot-based APIs where every visual element is replaceable.

**Rationale:** Community's #1 wish for non-Material design systems. Allows consumers to customize without forking.

---

## 5. Phased Implementation Plan

### Phase 1: Build Modernization

**Goal:** Project compiles with 2026 toolchain. No API changes.

**Scope:**
- Gradle 7.4 -> 8.11+
- AGP 7.3.1 -> 8.7.x
- Kotlin 1.7.20 -> 2.1.x
- Compose Compiler 1.3.2 -> built-in Kotlin compiler plugin
- Compose BOM -> 2025.x latest stable
- compileSdk -> 35 (all modules)
- minSdk -> 26 (all modules)
- Groovy build files -> Kotlin DSL (.gradle.kts)
- dependencies.gradle / versions.gradle -> libs.versions.toml
- CI: Actions v2 -> v4, ubuntu-latest, Ruby 3.x
- Fix deprecated API usages (rememberRipple -> ripple, etc.)
- Update publishing config for new Gradle/AGP

**Release:** v2.0.0-alpha01

### Phase 2: Clean Architecture

**Goal:** Zero Material dependencies. Zero unused 3P dependencies. All known bugs fixed.

**Scope:**

*Material Removal (11 files):*
- Create `LocalAndromedaContentColor` CompositionLocal
- Create `LocalAndromedaTextStyle` and `ProvideAndromedaTextStyle`
- Implement custom `contentColorFor()` using Andromeda color system
- Replace `rememberRipple()` with Foundation-based `Indication`
- Rewrite `AndromedaNavBar` using Andromeda's own Surface and Text
- Rewrite `BackButton` without Material IconButton
- Add Material Icons equivalents (ArrowBack, Close, DateRange, Error, Info, Edit, MoreVert) to AndromedaIcons as ImageVector

*3P Removal:*
- Remove Lottie / Lottie-Compose dependencies
- Remove Timber dependency
- Remove compose-material-dialogs dependency
- Remove threetenabp dependency
- Remove DatePickerInputField (depends on removed libs; replace with simple date picker using java.time on API 26+)

*Bug Fixes:*
- Fix PrimaryColors.copy() / SecondaryColors.copy() / TertiaryColors.copy() parameter order
- Fix BorderColors.copy() missing defaults
- Remove ButtonElevation empty LaunchedEffect
- Clean up DatePickerInputField unused dialogState (or remove entirely)

**Release:** v2.0.0-alpha02

### Phase 3: Design Token Gaps

**Goal:** Complete token system covering all design dimensions.

**Scope:**

*New Token Types:*
```kotlin
// Elevation tokens
object AndromedaElevation {
    val None      // 0dp
    val XSmall    // 1dp
    val Small     // 2dp
    val Medium    // 4dp
    val Large     // 8dp
    val XLarge    // 16dp
}

// Motion tokens
object AndromedaMotion {
    // Durations
    val Instant    // 0ms
    val Fast       // 100ms
    val Normal     // 200ms
    val Slow       // 350ms
    val XSlow      // 500ms
    // Easings
    val EaseIn: Easing
    val EaseOut: Easing
    val EaseInOut: Easing
    val Spring: SpringSpec
}

// Opacity tokens
object AndromedaOpacity {
    val Divider    // 0.12f
    val Disabled   // 0.48f
    val Subtle     // 0.66f
    val Minor      // 0.80f
    val Full       // 1.0f
}

// Spacing scale (expanded from OneX=4dp)
object Spacing {
    val XXSmall    // 2dp
    val XSmall     // 4dp
    val Small      // 8dp
    val Medium     // 16dp
    val Large      // 24dp
    val XLarge     // 32dp
    val XXLarge    // 48dp
}
```

*Token Integration:*
- Add `LocalElevation`, `LocalMotion`, `LocalOpacity` CompositionLocals
- Expose via `AndromedaTheme.elevation`, `.motion`, `.opacity`
- Replace all hardcoded values in existing components with token references
- Update catalog app to showcase all new tokens

**Release:** v2.0.0-alpha03

### Phase 4: Component Expansion + Icons

**Goal:** Production-ready component coverage (~35+ components) with comprehensive icon system and built-in accessibility.

**Scope:**

*Tier 1 — Selection Controls:*
- `Checkbox` + `TriStateCheckbox` — custom toggle with check animation, proper role semantics
- `RadioButton` + `RadioGroup` — exclusive selection with group management
- `Switch` / `Toggle` — animated on/off with thumb + track
- `Slider` — continuous/discrete value selection

*Tier 2 — Feedback & Status:*
- `AlertBanner` — inline information/warning/error/success banners
- `Toast` / `Snackbar` — timed feedback with optional action, live region a11y
- `Badge` / `NotificationBadge` — numeric and dot indicators
- `ProgressIndicator` — linear and circular, determinate/indeterminate
- `Skeleton` — loading placeholder with shimmer animation

*Tier 3 — Layout & Containers:*
- `Card` — content container built on Andromeda Surface
- `ListItem` — standard list row with leading/trailing slots
- `BottomSheet` — standard and modal variants with swipe-to-dismiss
- `Dialog` / `AlertDialog` — modal overlay with scrim
- `TabBar` / `TabGroup` — horizontal tab navigation

*Tier 4 — Content & Input:*
- `TextArea` — multiline text input
- `Select` / `Dropdown` — single-value selector with popup menu
- `Chip` / `Tag` — filter, action, input chips
- `Avatar` — image/initials with fallback
- `Tooltip` — contextual hover/long-press info
- `SearchField` — text field with search icon and clear action

*Icon System — Phosphor Icons:*
- Integrate or generate Phosphor Icons as ImageVector Kotlin source
- Split into category-based modules (e.g., andromeda-icons-system, andromeda-icons-media, etc.) or ship as single module with tree-shaking
- Support 6 weight variants: Thin, Light, Regular, Bold, Fill, Duotone
- API: `AndromedaIcons.Phosphor.Regular.Search`, `AndromedaIcons.Phosphor.Bold.Search`
- Keep the 7 original Andromeda icons as `AndromedaIcons.Legacy.*`

*Accessibility (applied to ALL components):*
- Proper `role` semantics (Button, Checkbox, RadioButton, Tab, Switch, etc.)
- 48dp minimum touch targets via custom `minimumInteractiveSize` modifier
- `mergeDescendants` on compound components (Card, ListItem)
- `stateDescription` for stateful components (Checkbox: checked/unchecked, Switch: on/off)
- `liveRegion` for Toast/Snackbar/AlertBanner
- Focus indicators and keyboard navigation
- `contentDescription` defaults where applicable

*Catalog App:*
- Add screens for all new components
- Interactive component playground (toggle states, change variants)
- Accessibility showcase screen
- Theme switcher (light/dark/custom)

**Release:** v2.0.0-beta01

### Phase 5: KMP / Compose Multiplatform

**Goal:** Full Compose Multiplatform support — Android, iOS, JVM Desktop, WasmJS.

**Scope:**

*Module Restructure:*
```
andromeda/
  src/
    commonMain/     -> All tokens, theme, composables (pure Compose)
    androidMain/    -> Android-specific (if any remain)
    iosMain/        -> iOS-specific (if any)
    desktopMain/    -> Desktop-specific (if any)
    wasmJsMain/     -> Web-specific (if any)
andromeda-icons/
  src/
    commonMain/     -> All ImageVector definitions (already KMP-ready)
andromeda-illustrations/
  src/
    commonMain/     -> Illustrations as ImageVector or CMP resources
catalog/            -> CMP demo app (all platforms)
```

*Key Migration Tasks:*
- Convert XML vector drawables (icons + illustrations) to ImageVector Kotlin source
- Migrate font loading from Android R.font to CMP resources (Res.font.*)
- Replace any Android-specific resource loading with CMP resources API
- Ensure all Foundation-based components work cross-platform (BasicText, BasicTextField, etc.)
- Test VoiceOver on iOS (stable since CMP 1.8.0)
- Test keyboard navigation on Desktop and Web
- Set explicit fonts everywhere (CMP does not guarantee font consistency across platforms)

*Publishing:*
- 7+ KMP artifacts per release (matching Lumen's setup):
  - `andromeda` (root metadata)
  - `andromeda-android` (AAR)
  - `andromeda-jvm` (JAR)
  - `andromeda-wasmjs` (klib)
  - `andromeda-iosarm64` (klib)
  - `andromeda-iossimulatorarm64` (klib)
  - `andromeda-iosx64` (klib)
- Same pattern for andromeda-icons and andromeda-illustrations
- Publish to Maven Central via Sonatype s01

*Catalog App:*
- Run on all 4 platforms
- Platform-specific entry points (Android Activity, iOS UIViewController, Desktop Window, WasmJS main)
- Shared UI code in commonMain

**Release:** v2.0.0

---

## 6. Release Strategy

| Phase | Version | Type | Milestone |
|-------|---------|------|-----------|
| 1 | v2.0.0-alpha01 | Alpha | Compiles on 2026 toolchain |
| 2 | v2.0.0-alpha02 | Alpha | Zero Material, zero unused 3P |
| 3 | v2.0.0-alpha03 | Alpha | Complete token system |
| 4 | v2.0.0-beta01 | Beta | 35+ components, Phosphor icons, a11y |
| 5 | v2.0.0 | Stable | Full KMP/CMP support |

Each phase produces a shippable, testable release. Consumers can adopt alpha/beta versions early for feedback.

---

## 7. Risk Assessment

| Risk | Impact | Mitigation |
|------|--------|------------|
| Kotlin 2.x migration breaks Compose APIs | High | Phase 1 focused solely on compilation, no API changes |
| Material removal breaks existing consumers | High | Major version bump (v2.0.0) signals breaking changes |
| Phosphor Icons increase library size | Medium | Tree-shaking + modular split by category |
| CMP font rendering differs across platforms | Medium | Explicit font loading, visual regression testing |
| iOS VoiceOver gaps in CMP | Medium | CMP 1.8.0+ has stable iOS a11y; test thoroughly |
| Component API design churn | Medium | Design each component API before implementing; slot-based pattern for consistency |

---

## 8. Success Criteria

- All 5 phases completed with passing CI on each release
- Zero Material Design dependencies in the dependency tree
- 35+ components with accessibility semantics
- Phosphor icon system integrated with 6 weight variants
- Running on Android, iOS, Desktop, and Web (WasmJS)
- Published to Maven Central as KMP artifacts
- Updated catalog app demonstrating all components on all platforms
- Blog post on aditlal.dev announcing v2.0.0
