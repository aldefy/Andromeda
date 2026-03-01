package design.andromedacompose.components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.Icon
import design.andromedacompose.components.Surface
import design.andromedacompose.foundation.ContentEmphasis
import design.andromedacompose.foundation.ProvideContentEmphasis
import design.andromedacompose.foundation.colors.contentColorFor
import design.andromedacompose.foundation.indication.AndromedaIndication
import design.andromedacompose.foundation.tokens.AndromedaElevation
import design.andromedacompose.foundation.tokens.AndromedaMotion
import design.andromedacompose.foundation.tokens.AndromedaOpacity
import design.andromedacompose.foundation.typography.ProvideMergedTextStyle
import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Button variant determines the visual style of the button.
 */
public enum class ButtonVariant {
    /** Solid background with contrasting content color. Default. */
    Filled,
    /** Transparent background with colored border and content. */
    Outlined,
    /** No background or border. Text-only appearance. */
    Ghost,
}

/**
 * Button size determines height, padding, and typography.
 */
public enum class ButtonSize {
    /** Compact: 32dp height, 12sp body text, 12dp horizontal padding */
    Small,
    /** Default: 44dp height, 16sp text, 16dp horizontal padding */
    Medium,
    /** Prominent: 56dp height, 18sp text, 24dp horizontal padding */
    Large,
}

/**
 * Encapsulates colors for a button in its different states.
 */
@Stable
public class ButtonColors(
    public val backgroundColor: ComposeColor,
    public val contentColor: ComposeColor,
    public val disabledBackgroundColor: ComposeColor,
    public val disabledContentColor: ComposeColor,
    public val borderColor: ComposeColor = ComposeColor.Transparent,
    public val disabledBorderColor: ComposeColor = ComposeColor.Transparent,
)

/**
 * Andromeda Button — a modern, variant-aware button component.
 *
 * Supports [Filled][ButtonVariant.Filled], [Outlined][ButtonVariant.Outlined],
 * and [Ghost][ButtonVariant.Ghost] variants, three sizes, loading state,
 * and full accessibility semantics.
 *
 * @param onClick Called when the button is clicked (not called when disabled or loading).
 * @param modifier Modifier applied to the button.
 * @param variant Visual style variant.
 * @param size Controls height, padding, and text size.
 * @param colors Color configuration. Use [ButtonDefaults.filledColors],
 *   [ButtonDefaults.outlinedColors], or [ButtonDefaults.ghostColors].
 * @param enabled Whether the button is interactive.
 * @param isLoading Shows a spinner and disables interaction.
 * @param elevation Elevation animation behavior.
 * @param shape Button shape.
 * @param interactionSource Interaction tracking.
 * @param indication Visual indication on press.
 * @param content Button content (text, icons, etc.)
 */
@Composable
public fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Filled,
    size: ButtonSize = ButtonSize.Medium,
    colors: ButtonColors = ButtonDefaults.colorsForVariant(variant),
    enabled: Boolean = true,
    isLoading: Boolean = false,
    elevation: ButtonElevation = ButtonDefaults.elevationForVariant(variant),
    shape: Shape = AndromedaTheme.shapes.buttonShape,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = AndromedaIndication,
    content: @Composable RowScope.() -> Unit,
) {
    val isInteractive = enabled && !isLoading

    val resolvedBackgroundColor = if (isInteractive) {
        colors.backgroundColor
    } else {
        colors.disabledBackgroundColor
    }

    val resolvedContentColor = if (isInteractive) {
        colors.contentColor
    } else {
        colors.disabledContentColor
    }

    val resolvedBorder = when {
        variant == ButtonVariant.Outlined -> {
            val borderColor = if (isInteractive) colors.borderColor else colors.disabledBorderColor
            BorderStroke(ButtonDefaults.OutlinedBorderWidth, borderColor)
        }
        else -> null
    }

    val contentAlpha = animateFloatAsState(
        targetValue = if (isLoading) AndromedaOpacity.Subtle else AndromedaOpacity.Full,
        animationSpec = tween(durationMillis = AndromedaMotion.Fast),
        label = "buttonContentAlpha"
    )

    Surface(
        modifier = modifier,
        shape = shape,
        color = resolvedBackgroundColor,
        contentColor = resolvedContentColor,
        border = resolvedBorder,
        elevation = elevation.elevation(enabled = isInteractive, interactionSource).value,
        onClick = { if (isInteractive) onClick() },
        role = Role.Button,
        interactionSource = interactionSource,
        indication = if (isInteractive) indication else null,
        enabled = isInteractive,
    ) {
        ProvideContentEmphasis(
            emphasis = if (isInteractive) ContentEmphasis.Normal else ContentEmphasis.Disabled
        ) {
            ProvideMergedTextStyle(
                value = ButtonDefaults.textStyleForSize(size)
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.minWidthForSize(size),
                            minHeight = ButtonDefaults.minHeightForSize(size)
                        )
                        .padding(ButtonDefaults.contentPaddingForSize(size)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AnimatedVisibility(
                        visible = isLoading,
                        enter = fadeIn() + scaleIn(initialScale = 0.6f),
                        exit = fadeOut() + scaleOut(targetScale = 0.6f),
                    ) {
                        Row {
                            LoadingSpinner(
                                color = resolvedContentColor,
                                size = ButtonDefaults.spinnerSizeForButtonSize(size),
                            )
                            Spacer(Modifier.width(ButtonDefaults.iconSpacingForSize(size)))
                        }
                    }
                    Box(Modifier.alpha(contentAlpha.value)) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            content = content
                        )
                    }
                }
            }
        }
    }
}

/**
 * Icon-only button with a single icon and no text.
 * Uses circular shape and equal padding for a balanced look.
 *
 * @param onClick Called when the button is clicked.
 * @param icon The icon to display.
 * @param contentDescription Accessibility description for the icon.
 * @param modifier Modifier applied to the button.
 * @param variant Visual style variant.
 * @param size Controls overall button size.
 * @param colors Color configuration.
 * @param enabled Whether the button is interactive.
 * @param isLoading Shows a spinner instead of the icon.
 */
@Composable
public fun IconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Filled,
    size: ButtonSize = ButtonSize.Medium,
    colors: ButtonColors = ButtonDefaults.colorsForVariant(variant),
    enabled: Boolean = true,
    isLoading: Boolean = false,
    shape: Shape = AndromedaTheme.shapes.buttonShape,
) {
    val iconSize = ButtonDefaults.iconSizeForButtonSize(size)
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(
            minWidth = ButtonDefaults.minHeightForSize(size),
            minHeight = ButtonDefaults.minHeightForSize(size)
        ),
        variant = variant,
        size = size,
        colors = colors,
        enabled = enabled,
        isLoading = isLoading,
        shape = shape,
    ) {
        if (!isLoading) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize),
            )
        }
    }
}

// --- Button Defaults ---

public object ButtonDefaults {

    /** Border width for outlined variant */
    public val OutlinedBorderWidth: Dp = 1.5.dp

    // --- Content Padding ---

    public val ContentPadding: PaddingValues = contentPaddingForSize(ButtonSize.Medium)

    public fun contentPaddingForSize(size: ButtonSize): PaddingValues = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = 12.dp, vertical = 4.dp)
        ButtonSize.Medium -> PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ButtonSize.Large -> PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    }

    // --- Min dimensions ---

    public val MinWidth: Dp = 64.dp
    public val MinHeight: Dp = 44.dp

    public fun minWidthForSize(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 48.dp
        ButtonSize.Medium -> 64.dp
        ButtonSize.Large -> 80.dp
    }

    public fun minHeightForSize(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 32.dp
        ButtonSize.Medium -> 44.dp
        ButtonSize.Large -> 56.dp
    }

    // --- Typography ---

    @Composable
    public fun textStyleForSize(size: ButtonSize): TextStyle = when (size) {
        ButtonSize.Small -> AndromedaTheme.typography.bodySmallDefaultTypographyStyle
        ButtonSize.Medium -> AndromedaTheme.typography.titleSmallDemiTextStyle
        ButtonSize.Large -> AndromedaTheme.typography.titleModerateDemiTextStyle
    }

    // --- Icon & spinner sizes ---

    public fun iconSizeForButtonSize(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 16.dp
        ButtonSize.Medium -> 20.dp
        ButtonSize.Large -> 24.dp
    }

    public fun spinnerSizeForButtonSize(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 14.dp
        ButtonSize.Medium -> 18.dp
        ButtonSize.Large -> 22.dp
    }

    public fun iconSpacingForSize(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 4.dp
        ButtonSize.Medium -> 8.dp
        ButtonSize.Large -> 8.dp
    }

    // --- Elevation ---

    @Composable
    public fun elevation(
        defaultElevation: Dp = AndromedaElevation.Small,
        pressedElevation: Dp = AndromedaElevation.Large,
        disabledElevation: Dp = AndromedaElevation.None,
    ): ButtonElevation {
        return remember(defaultElevation, pressedElevation, disabledElevation) {
            DefaultButtonElevation(
                defaultElevation = defaultElevation,
                pressedElevation = pressedElevation,
                disabledElevation = disabledElevation
            )
        }
    }

    @Composable
    public fun elevationForVariant(variant: ButtonVariant): ButtonElevation = when (variant) {
        ButtonVariant.Filled -> elevation()
        ButtonVariant.Outlined -> elevation(
            defaultElevation = AndromedaElevation.None,
            pressedElevation = AndromedaElevation.XSmall,
            disabledElevation = AndromedaElevation.None,
        )
        ButtonVariant.Ghost -> elevation(
            defaultElevation = AndromedaElevation.None,
            pressedElevation = AndromedaElevation.None,
            disabledElevation = AndromedaElevation.None,
        )
    }

    // --- Colors ---

    @Composable
    public fun colorsForVariant(variant: ButtonVariant): ButtonColors = when (variant) {
        ButtonVariant.Filled -> filledColors()
        ButtonVariant.Outlined -> outlinedColors()
        ButtonVariant.Ghost -> ghostColors()
    }

    @Composable
    public fun filledColors(
        backgroundColor: ComposeColor = AndromedaTheme.colors.primaryColors.active,
        contentColor: ComposeColor = contentColorFor(backgroundColor),
        disabledBackgroundColor: ComposeColor = AndromedaTheme.colors.primaryColors.mute,
        disabledContentColor: ComposeColor = AndromedaTheme.colors.contentColors.disabled,
    ): ButtonColors = ButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    public fun outlinedColors(
        backgroundColor: ComposeColor = ComposeColor.Transparent,
        contentColor: ComposeColor = AndromedaTheme.colors.primaryColors.active,
        disabledBackgroundColor: ComposeColor = ComposeColor.Transparent,
        disabledContentColor: ComposeColor = AndromedaTheme.colors.contentColors.disabled,
        borderColor: ComposeColor = AndromedaTheme.colors.primaryColors.active,
        disabledBorderColor: ComposeColor = AndromedaTheme.colors.borderColors.mute,
    ): ButtonColors = ButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
        borderColor = borderColor,
        disabledBorderColor = disabledBorderColor,
    )

    @Composable
    public fun ghostColors(
        backgroundColor: ComposeColor = ComposeColor.Transparent,
        contentColor: ComposeColor = AndromedaTheme.colors.primaryColors.active,
        disabledBackgroundColor: ComposeColor = ComposeColor.Transparent,
        disabledContentColor: ComposeColor = AndromedaTheme.colors.contentColors.disabled,
    ): ButtonColors = ButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
    )
}
