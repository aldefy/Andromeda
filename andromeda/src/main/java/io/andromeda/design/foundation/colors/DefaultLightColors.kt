package io.andromeda.design.foundation.colors

import io.andromeda.design.foundation.colors.tokens.DefaultColorTokens
import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Light side
 */
internal fun defaultLightColors(
    primaryColors: PrimaryColors = defaultPrimaryLightColors(),
    secondaryColors: SecondaryColors = defaultSecondaryLightColors(),
    tertiaryColors: TertiaryColors = defaultTertiaryLightColors(),
    borderColors: BorderColors = defaultBorderLightColors(),
    iconColors: IconColors = defaultIconsLightColors()
): AndromedaColors = AndromedaColors(
    primaryColors = primaryColors,
    secondaryColors = secondaryColors,
    tertiaryColors = tertiaryColors,
    borderColors = borderColors,
    iconColors = iconColors,
    isDark = false
)

internal fun defaultPrimaryLightColors(
    active: ComposeColor = DefaultColorTokens.activePrimaryLight,
    background: ComposeColor = DefaultColorTokens.backgroundPrimaryLight,
    error: ComposeColor = DefaultColorTokens.errorPrimaryLight,
    mute: ComposeColor = DefaultColorTokens.mutePrimaryLight,
    pressed: ComposeColor = DefaultColorTokens.pressedPrimaryLight,
): PrimaryColors = PrimaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed
)

internal fun defaultSecondaryLightColors(
    active: ComposeColor = DefaultColorTokens.activeSecondaryLight,
    background: ComposeColor = DefaultColorTokens.backgroundSecondaryLight,
    error: ComposeColor = DefaultColorTokens.errorSecondaryLight,
    mute: ComposeColor = DefaultColorTokens.muteSecondaryLight,
    pressed: ComposeColor = DefaultColorTokens.pressedSecondaryLight,
): SecondaryColors = SecondaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed
)

internal fun defaultTertiaryLightColors(
    active: ComposeColor = DefaultColorTokens.activeTertiaryLight,
    background: ComposeColor = DefaultColorTokens.backgroundTertiaryLight,
    error: ComposeColor = DefaultColorTokens.errorTertiaryLight,
    mute: ComposeColor = DefaultColorTokens.muteTertiaryLight,
    pressed: ComposeColor = DefaultColorTokens.pressedTertiaryLight,
): TertiaryColors = TertiaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed
)

internal fun defaultBorderLightColors(
    active: ComposeColor = DefaultColorTokens.activeBorderLight,
    pressed: ComposeColor = DefaultColorTokens.pressedBorderLight,
    inactive: ComposeColor = DefaultColorTokens.inactiveBorderLight,
    mute: ComposeColor = DefaultColorTokens.muteBorderLight,
    focus: ComposeColor = DefaultColorTokens.focusBorderLight,
    error: ComposeColor = DefaultColorTokens.errorBorderLight,
): BorderColors = AndromedaBorderColors(
    active = active,
    pressed = pressed,
    inactive = inactive,
    mute = mute,
    focus = focus,
    error = error
)

internal fun defaultIconsLightColors(
    default: ComposeColor = DefaultColorTokens.activeBorderLight,
    disabled: ComposeColor = DefaultColorTokens.activeBorderLight,
    active: ComposeColor = DefaultColorTokens.activeBorderLight,
): IconColors = AndromedaIconColors(
    default = default,
    disabled = disabled,
    active = active
)
