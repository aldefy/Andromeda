# Your Status Bar Icons Vanished on Android 16. Here's Why Nothing You Try Will Fix It.

*And the one approach that actually works.*

---

## The Problem

You upgraded `targetSdk` to 35. Maybe you're already on 36. You call `enableEdgeToEdge()` like a responsible citizen. Your app has a dark mode toggle — a `CircularReveal`, a simple boolean flip, whatever. Light mode looks perfect. Dark status bar icons on a white background. Chef's kiss.

Then you toggle to dark mode and the status bar becomes a black void. The clock, your notification icons, signal strength — all gone. Swallowed by the darkness.

Only the battery indicator survives, because it's colored by the system and doesn't follow `APPEARANCE_LIGHT_STATUS_BARS`.

You know exactly what's wrong. You just can't fix it.

## The Agitation

Here's what you've tried. I know because I tried all of them too.

**Attempt 1: `isAppearanceLightStatusBars`**

```kotlin
WindowCompat.getInsetsController(window, window.decorView)
    .isAppearanceLightStatusBars = false // light icons for dark bg
```

This is the documented API. It worked on API 34. On API 36, the system ignores it. Your app says "I have a dark background, give me light icons" and Android says "the system is in light mode, so no."

**Attempt 2: `enableEdgeToEdge()` with `SystemBarStyle`**

```kotlin
SideEffect {
    val barStyle = if (isDark) {
        SystemBarStyle.dark(Color.TRANSPARENT)
    } else {
        SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
    }
    enableEdgeToEdge(statusBarStyle = barStyle, navigationBarStyle = barStyle)
}
```

Same result. `SystemBarStyle.dark()` internally sets the same `APPEARANCE_LIGHT_STATUS_BARS` flag. The system overrides it.

**Attempt 3: `AppCompatDelegate.localNightMode`**

```kotlin
delegate.localNightMode = if (isLight)
    AppCompatDelegate.MODE_NIGHT_NO
else
    AppCompatDelegate.MODE_NIGHT_YES
```

This changes the activity's `Configuration.uiMode`, which *used to* signal the system about your app's theme. On API 36 it either gets ignored or triggers an activity recreation that destroys your Compose state. Adding `android:configChanges="uiMode"` prevents the recreation but the status bar still doesn't update.

**Attempt 4: `window.insetsController` directly**

```kotlin
window.decorView.post {
    window.insetsController?.setSystemBarsAppearance(
        0, // clear LIGHT flag = request light icons
        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
    )
}
```

Posted on the next frame, after config changes, with `decorView.post {}`. Doesn't matter. The system overrides it.

**Attempt 5: Combining everything**

`AppCompatActivity` + `localNightMode` + `enableEdgeToEdge(SystemBarStyle.dark)` + `isAppearanceLightStatusBars = false` + `configChanges="uiMode"` + posting to the next frame.

Nothing. The status bar icons remain dark on a dark background.

At this point you're not debugging. You're bargaining.

## Why It Happens

Android 15 (API 35) enforced edge-to-edge. Android 16 (API 36) went further: the system now determines status bar icon tint based on the **system-level** dark mode setting, not the app's preference.

Your app can declare itself dark all it wants. If the device is in light mode, the system draws dark status bar icons. Period.

This is arguably a regression. The whole point of `APPEARANCE_LIGHT_STATUS_BARS` was to let apps control icon contrast when drawing behind a transparent status bar. But on API 36, that contract is broken for apps whose theme doesn't match the system theme.

The [Kotlin Slack](https://slack-chats.kotlinlang.org/t/27081088/does-anyone-know-why-when-i-m-running-under-api-level-35-and) and various [developer forums](https://www.b4x.com/android/forum/threads/android-16-edge-to-edge-layout-issue-action-bar-shifted-down-status-bar-icons-missing-after-targeting-sdk-36.169031/) are full of reports about this. There's no official acknowledgment from Google yet.

## The Solution

Stop trying to change the icon color. Change what's behind them.

The [official Android documentation](https://developer.android.com/develop/ui/compose/system/system-bars) recommends a **status bar protection scrim** — a gradient or solid color drawn over the status bar area to ensure contrast regardless of icon tint.

Here's the implementation that works:

```kotlin
@Composable
private fun StatusBarProtection(color: Color) {
    val statusBarsInsets = WindowInsets.statusBars
    val density = LocalDensity.current
    val heights = remember(statusBarsInsets, density) {
        val statusBarH = statusBarsInsets.getTop(density).toFloat()
        statusBarH to statusBarH * 1.3f
    }
    Canvas(Modifier.fillMaxSize()) {
        val (solidHeight, totalHeight) = heights
        // Solid opaque bar behind the status bar icons
        drawRect(
            color = color,
            size = Size(size.width, solidHeight),
        )
        // Short gradient fade below
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(color, Color.Transparent),
                startY = solidHeight,
                endY = totalHeight,
            ),
            topLeft = Offset(0f, solidHeight),
            size = Size(size.width, totalHeight - solidHeight),
        )
    }
}
```

Use it in your activity:

```kotlin
enableEdgeToEdge() // still call this for backward compat

setContent {
    var isDark by remember { mutableStateOf(false) }

    // Keep SystemBarStyle for APIs < 36 where it still works
    SideEffect {
        val barStyle = if (isDark) SystemBarStyle.dark(Color.TRANSPARENT)
            else SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        enableEdgeToEdge(statusBarStyle = barStyle, navigationBarStyle = barStyle)
    }

    MyTheme(darkTheme = isDark) {
        Box(Modifier.fillMaxSize()) {
            MainContent(onToggleTheme = { isDark = !isDark })

            // Scrim only needed in dark mode when system is in light mode
            if (isDark) {
                StatusBarProtection(
                    color = MyTheme.colors.background
                )
            }
        }
    }
}
```

### Why this works

The system draws dark icons because it thinks the background is light. By drawing an opaque solid rectangle behind the status bar area using your dark theme's background color, the dark icons now sit on a **light** surface (the scrim) that provides sufficient contrast. The short gradient fade below the solid bar blends it seamlessly into your actual content.

### Why not use alpha/transparency?

If you use `color.copy(alpha = 0.8f)` in the gradient (like the official docs suggest), you'll get bleed-through artifacts during animations like `CircularReveal` where both theme layers are composited simultaneously. The semi-transparent scrim lets the underlying layer show through.

Use a **fully opaque** solid color for the status bar height and only fade at the bottom edge.

### What about `isSystemInDarkTheme()`?

You could conditionally apply the scrim only when `isDark && !isSystemInDarkTheme()` — i.e., only when the app's dark mode doesn't match the system. But the scrim is invisible when the background color already matches, so the simpler `if (isDark)` works fine.

## What About KMP / Compose Multiplatform?

If you're building a design system that targets Compose Multiplatform (Android + iOS + Desktop + Wasm), this problem is Android-only. But that doesn't mean you can ignore it.

The scrim approach is actually KMP-friendly because it's pure Compose:

```kotlin
// commonMain — the scrim is just Canvas + WindowInsets
@Composable
expect fun StatusBarProtection(color: Color)

// androidMain — the implementation above
@Composable
actual fun StatusBarProtection(color: Color) { /* Canvas scrim */ }

// iosMain / desktopMain / wasmMain — no-op
@Composable
actual fun StatusBarProtection(color: Color) { /* nothing needed */ }
```

On iOS, the system handles status bar icon tint correctly based on the `UIStatusBarStyle` your app declares. Desktop and Wasm don't have system status bars. So you `expect/actual` the scrim, implement it on Android, and skip it everywhere else.

The `enableEdgeToEdge()` + `SystemBarStyle` calls stay in your Android activity code (platform entry point), not in shared Compose. The scrim composable lives in shared UI with a platform-specific implementation.

This is the pattern we're using in [Andromeda](https://github.com/nickelaway/Andromeda) as we migrate from Android-only to Compose Multiplatform: shared theme tokens and design primitives across platforms, platform-specific system integration where the OS forces your hand.

## The Takeaway

On Android 16, `isAppearanceLightStatusBars` is effectively read-only for the system. Your app cannot override it when the app theme diverges from the system theme. The fix is architectural, not API-level: draw your own contrast behind the system icons.

Keep `enableEdgeToEdge()` and `SystemBarStyle` for backward compatibility. Add the scrim for API 36+. For KMP, `expect/actual` the scrim and no-op on non-Android targets. Ship it.

---

*Tested on Pixel 9 Pro Fold running Android 16 (API 36), device in light mode, app toggling to dark mode via CircularReveal animation. All status bar icons visible in both themes. No artifacts during animation.*
