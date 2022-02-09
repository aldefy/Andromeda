package design.andromedacompose.foundation.colors

import design.andromedacompose.foundation.colors.tokens.DefaultColorTokens
import design.andromedacompose.design.invert
import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Light side
 */
fun defaultLightColors(
    primaryColors: PrimaryColors = defaultPrimaryLightColors(),
    secondaryColors: SecondaryColors = defaultSecondaryLightColors(),
    tertiaryColors: TertiaryColors = defaultTertiaryLightColors(),
    borderColors: BorderColors = defaultBorderLightColors(),
    iconColors: IconColors = defaultIconsLightColors(),
    contentColors: ContentColors = defaultContentLightColors()
): AndromedaColors = AndromedaColors(
    primaryColors = primaryColors,
    secondaryColors = secondaryColors,
    tertiaryColors = tertiaryColors,
    borderColors = borderColors,
    iconColors = iconColors,
    contentColors = contentColors,
    isDark = false
)

internal fun defaultPrimaryLightColors(
    active: ComposeColor = DefaultColorTokens.activePrimaryLight,
    background: ComposeColor = DefaultColorTokens.backgroundPrimaryLight,
    error: ComposeColor = DefaultColorTokens.errorPrimaryLight,
    mute: ComposeColor = DefaultColorTokens.mutePrimaryLight,
    pressed: ComposeColor = DefaultColorTokens.pressedPrimaryLight,
    alt: ComposeColor = DefaultColorTokens.White.invert(),
): PrimaryColors = PrimaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed,
    alt = alt
)

internal fun defaultSecondaryLightColors(
    active: ComposeColor = DefaultColorTokens.activeSecondaryLight,
    background: ComposeColor = DefaultColorTokens.backgroundSecondaryLight,
    error: ComposeColor = DefaultColorTokens.errorSecondaryLight,
    mute: ComposeColor = DefaultColorTokens.muteSecondaryLight,
    pressed: ComposeColor = DefaultColorTokens.pressedSecondaryLight,
    alt: ComposeColor = DefaultColorTokens.White.invert(),
): SecondaryColors = SecondaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed,
    alt = alt
)

internal fun defaultTertiaryLightColors(
    active: ComposeColor = DefaultColorTokens.activeTertiaryLight,
    background: ComposeColor = DefaultColorTokens.backgroundTertiaryLight,
    error: ComposeColor = DefaultColorTokens.errorTertiaryLight,
    mute: ComposeColor = DefaultColorTokens.muteTertiaryLight,
    pressed: ComposeColor = DefaultColorTokens.pressedTertiaryLight,
    alt: ComposeColor = DefaultColorTokens.White.invert(),
): TertiaryColors = TertiaryColors(
    active = active,
    background = background,
    error = error,
    mute = mute,
    pressed = pressed,
    alt = alt
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
    default: ComposeColor = DefaultColorTokens.iconDefaultLight,
    disabled: ComposeColor = DefaultColorTokens.iconDisabledLight,
    active: ComposeColor = DefaultColorTokens.iconActiveLight,
): IconColors = AndromedaIconColors(
    default = default,
    disabled = disabled,
    active = active
)

internal fun defaultContentLightColors(
    normal: ComposeColor = DefaultColorTokens.contentNormal,
    minor: ComposeColor = DefaultColorTokens.contentMinor,
    subtle: ComposeColor = DefaultColorTokens.contentSubtle,
    disabled: ComposeColor = DefaultColorTokens.contentDisabled,
): ContentColors = ContentColors(
    normal = normal,
    minor = minor,
    subtle = subtle,
    disabled = disabled
)
