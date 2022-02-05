package io.andromeda.design.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.contentColorFor
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.components.Surface
import io.andromeda.design.foundation.ContentEmphasis
import io.andromeda.design.foundation.ProvideContentEmphasis
import io.andromeda.design.foundation.typography.ProvideMergedTextStyle
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
internal fun Button(
    onClick: () -> Unit,
    backgroundColor: ComposeColor,
    modifier: Modifier = Modifier,
    contentColor: ComposeColor = contentColorFor(backgroundColor = backgroundColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    shape: Shape = AndromedaTheme.shapes.small,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation.elevation(enabled = true, interactionSource).value,
        onClick = onClick,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple()
    ) {
        ProvideContentEmphasis(
            emphasis = ContentEmphasis.Normal
        ) {
            ProvideMergedTextStyle(
                value = AndromedaTheme.typography.titleModerateDemiTextStyle
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }

}

public object ButtonDefaults {
    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 8.dp

    /**
     * The default content padding used by [Button]
     */
    public val ContentPadding: PaddingValues = PaddingValues(
        horizontal = ButtonHorizontalPadding,
        vertical = ButtonVerticalPadding
    )

    /**
     * The default min width applied for the [Button].
     * Note that you can override it by applying Modifier.widthIn directly on [Button].
     */
    public val MinWidth: Dp = 64.dp

    /**
     * The default min height applied for the [Button].
     * Note that you can override it by applying Modifier.heightIn directly on [Button].
     */
    public val MinHeight: Dp = 44.dp

    /**
     * Creates a [ButtonElevation] that will animate between the provided values according to the
     * Material specification for a [Button].
     *
     * @param defaultElevation the elevation to use when the [Button] is enabled, and has no
     * other [Interaction]s.
     * @param pressedElevation the elevation to use when the [Button] is enabled and
     * is pressed.
     * @param disabledElevation the elevation to use when the [Button] is not enabled.
     */
    @Composable
    public fun elevation(
        defaultElevation: Dp = 2.dp,
        pressedElevation: Dp = 8.dp,
        // focused: Dp = 4.dp,
        // hovered: Dp = 4.dp,
        disabledElevation: Dp = 0.dp
    ): ButtonElevation {
        return remember(defaultElevation, pressedElevation, disabledElevation) {
            DefaultButtonElevation(
                defaultElevation = defaultElevation,
                pressedElevation = pressedElevation,
                disabledElevation = disabledElevation
            )
        }
    }
}
