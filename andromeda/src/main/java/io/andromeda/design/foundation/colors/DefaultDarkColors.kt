package io.andromeda.design.foundation.colors

import io.andromeda.design.foundation.colors.tokens.DefaultColorTokens
import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Dark side
 */
fun defaultDarkColors(
    primaryColors: PrimaryColors = defaultPrimaryDarkColors(),
    secondaryColors: SecondaryColors = defaultSecondaryDarkColors(),
    tertiaryColors: TertiaryColors = defaultTertiaryDarkColors(),
    borderColors: BorderColors = defaultBorderDarkColors()
): AndromedaColors = AndromedaColors(
    primaryColors = primaryColors,
    secondaryColors = secondaryColors,
    tertiaryColors = tertiaryColors,
    borderColors = borderColors,
    isDark = true
)

fun defaultPrimaryDarkColors(
    active: ComposeColor = DefaultColorTokens.activePrimaryDark,
    background: ComposeColor = DefaultColorTokens.backgroundPrimaryDark,
    error: ComposeColor = DefaultColorTokens.errorPrimaryDark,
    mute: ComposeColor = DefaultColorTokens.mutePrimaryDark,
    pressed: ComposeColor = DefaultColorTokens.pressedPrimaryDark,
): PrimaryColors = PrimaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed
)

fun defaultSecondaryDarkColors(
    active: ComposeColor = DefaultColorTokens.activeSecondaryDark,
    background: ComposeColor = DefaultColorTokens.backgroundSecondaryDark,
    error: ComposeColor = DefaultColorTokens.errorSecondaryDark,
    mute: ComposeColor = DefaultColorTokens.muteSecondaryDark,
    pressed: ComposeColor = DefaultColorTokens.pressedSecondaryDark,
): SecondaryColors = SecondaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed
)

fun defaultTertiaryDarkColors(
    active: ComposeColor = DefaultColorTokens.activeTertiaryDark,
    background: ComposeColor = DefaultColorTokens.backgroundTertiaryDark,
    error: ComposeColor = DefaultColorTokens.errorTertiaryDark,
    mute: ComposeColor = DefaultColorTokens.muteTertiaryDark,
    pressed: ComposeColor = DefaultColorTokens.pressedTertiaryDark,
): TertiaryColors = TertiaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed
)

fun defaultBorderDarkColors(
    active: ComposeColor = DefaultColorTokens.activeBorderDark,
    pressed: ComposeColor = DefaultColorTokens.pressedBorderDark,
    inactive: ComposeColor = DefaultColorTokens.inactiveBorderDark,
    mute: ComposeColor = DefaultColorTokens.muteBorderDark,
    focus: ComposeColor = DefaultColorTokens.focusBorderDark,
    error: ComposeColor = DefaultColorTokens.errorBorderDark,
): BorderColors = StrokeColors(
    active = active,
    pressed = pressed,
    inactive = inactive,
    mute = mute,
    focus = focus,
    error = error
)
