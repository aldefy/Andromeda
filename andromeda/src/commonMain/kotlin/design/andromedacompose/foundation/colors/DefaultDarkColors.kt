package design.andromedacompose.foundation.colors

import design.andromedacompose.foundation.colors.tokens.DefaultColorTokens
import design.andromedacompose.design.invert
import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Dark side
 */
fun defaultDarkColors(
    primaryColors: PrimaryColors = defaultPrimaryDarkColors(),
    secondaryColors: SecondaryColors = defaultSecondaryDarkColors(),
    tertiaryColors: TertiaryColors = defaultTertiaryDarkColors(),
    borderColors: BorderColors = defaultBorderDarkColors(),
    iconColors: IconColors = defaultIconsDarkColors(),
    contentColors: ContentColors = defaultContentDarkColors()
): AndromedaColors = AndromedaColors(
    primaryColors = primaryColors,
    secondaryColors = secondaryColors,
    tertiaryColors = tertiaryColors,
    borderColors = borderColors,
    iconColors = iconColors,
    contentColors = contentColors,
    isDark = true
)

internal fun defaultPrimaryDarkColors(
    active: ComposeColor = DefaultColorTokens.activePrimaryDark,
    background: ComposeColor = DefaultColorTokens.backgroundPrimaryDark,
    error: ComposeColor = DefaultColorTokens.errorPrimaryDark,
    mute: ComposeColor = DefaultColorTokens.mutePrimaryDark,
    pressed: ComposeColor = DefaultColorTokens.pressedPrimaryDark,
    alt: ComposeColor = DefaultColorTokens.CloudDarkNormal,
): PrimaryColors = PrimaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed,
    alt = alt
)

internal fun defaultSecondaryDarkColors(
    active: ComposeColor = DefaultColorTokens.activeSecondaryDark,
    background: ComposeColor = DefaultColorTokens.backgroundSecondaryDark,
    error: ComposeColor = DefaultColorTokens.errorSecondaryDark,
    mute: ComposeColor = DefaultColorTokens.muteSecondaryDark,
    pressed: ComposeColor = DefaultColorTokens.pressedSecondaryDark,
    alt: ComposeColor = DefaultColorTokens.White,
): SecondaryColors = SecondaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed,
    alt = alt
)

internal fun defaultTertiaryDarkColors(
    active: ComposeColor = DefaultColorTokens.activeTertiaryDark,
    background: ComposeColor = DefaultColorTokens.backgroundTertiaryDark,
    error: ComposeColor = DefaultColorTokens.errorTertiaryDark,
    mute: ComposeColor = DefaultColorTokens.muteTertiaryDark,
    pressed: ComposeColor = DefaultColorTokens.pressedTertiaryDark,
    alt: ComposeColor = DefaultColorTokens.White,
): TertiaryColors = TertiaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed,
    alt = alt
)

internal fun defaultBorderDarkColors(
    active: ComposeColor = DefaultColorTokens.activeBorderDark,
    pressed: ComposeColor = DefaultColorTokens.pressedBorderDark,
    inactive: ComposeColor = DefaultColorTokens.inactiveBorderDark,
    mute: ComposeColor = DefaultColorTokens.muteBorderDark,
    focus: ComposeColor = DefaultColorTokens.focusBorderDark,
    error: ComposeColor = DefaultColorTokens.errorBorderDark,
): BorderColors = AndromedaBorderColors(
    active = active,
    pressed = pressed,
    inactive = inactive,
    mute = mute,
    focus = focus,
    error = error
)

internal fun defaultIconsDarkColors(
    default: ComposeColor = DefaultColorTokens.iconDefaultDark,
    disabled: ComposeColor = DefaultColorTokens.iconDisabledDark,
    active: ComposeColor = DefaultColorTokens.iconActiveDark,
): IconColors = AndromedaIconColors(
    default = default,
    disabled = disabled,
    active = active
)

internal fun defaultContentDarkColors(
    normal: ComposeColor = DefaultColorTokens.contentNormal.invert(),
    minor: ComposeColor = DefaultColorTokens.contentMinor.invert(),
    subtle: ComposeColor = DefaultColorTokens.contentSubtle.invert(),
    disabled: ComposeColor = DefaultColorTokens.contentDisabled.invert(),
): ContentColors = ContentColors(
    normal = normal,
    minor = minor,
    subtle = subtle,
    disabled = disabled
)
