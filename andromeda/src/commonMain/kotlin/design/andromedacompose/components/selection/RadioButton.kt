package design.andromedacompose.components.selection

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.foundation.accessibility.minimumInteractiveSize
import design.andromedacompose.foundation.accessibility.toggleableSemantics
import design.andromedacompose.foundation.indication.AndromedaIndication
import design.andromedacompose.foundation.tokens.AndromedaMotion
import design.andromedacompose.foundation.tokens.AndromedaOpacity

/**
 * Andromeda RadioButton — a custom-drawn radio button with animated fill.
 *
 * @param selected Whether this radio button is selected.
 * @param onClick Called when clicked. Null to disable click handling.
 * @param modifier Modifier applied to the radio button.
 * @param enabled Whether the radio button is interactive.
 * @param selectedColor Color when selected.
 * @param unselectedColor Border color when not selected.
 * @param size Diameter of the radio button.
 */
@Composable
public fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedColor: Color = AndromedaTheme.colors.primaryColors.active,
    unselectedColor: Color = AndromedaTheme.colors.borderColors.active,
    size: Dp = 22.dp,
) {
    val dotScale =
        animateFloatAsState(
            targetValue = if (selected) 1f else 0f,
            animationSpec = tween(durationMillis = AndromedaMotion.Fast),
            label = "radioDotScale",
        )
    val alpha = if (enabled) AndromedaOpacity.Full else AndromedaOpacity.Disabled

    val clickModifier =
        if (onClick != null) {
            Modifier
                .minimumInteractiveSize()
                .toggleableSemantics(
                    checked = selected,
                    role = Role.RadioButton,
                    checkedLabel = "Selected",
                    uncheckedLabel = "Not selected",
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = AndromedaIndication,
                    enabled = enabled,
                    role = Role.RadioButton,
                    onClick = onClick,
                )
        } else {
            Modifier
        }

    Canvas(modifier = modifier.then(clickModifier).size(size)) {
        val borderWidth = 2.dp.toPx()
        val radius = this.size.minDimension / 2f
        val color = if (selected) selectedColor else unselectedColor

        // Outer circle
        drawCircle(
            color = color,
            radius = radius - borderWidth / 2f,
            style = Stroke(width = borderWidth),
            alpha = alpha,
        )

        // Inner filled dot (animated)
        if (dotScale.value > 0f) {
            val dotRadius = (radius * 0.45f) * dotScale.value
            drawCircle(
                color = selectedColor,
                radius = dotRadius,
                alpha = alpha,
            )
        }
    }
}

/**
 * RadioButton with a label. The entire row is clickable.
 */
@Composable
public fun LabeledRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedColor: Color = AndromedaTheme.colors.primaryColors.active,
    unselectedColor: Color = AndromedaTheme.colors.borderColors.active,
    label: @Composable () -> Unit,
) {
    Row(
        modifier =
            modifier
                .minimumInteractiveSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = AndromedaIndication,
                    enabled = enabled,
                    role = Role.RadioButton,
                    onClick = onClick,
                )
                .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            enabled = enabled,
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
        )
        Spacer(Modifier.width(12.dp))
        label()
    }
}

/**
 * A semantically grouped container for [RadioButton] or [LabeledRadioButton] items.
 * Applies `selectableGroup` semantics so screen readers treat the children as a group.
 *
 * @param modifier Modifier applied to the group column.
 * @param content The radio button items.
 */
@Composable
public fun RadioGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.selectableGroup(),
    ) {
        content()
    }
}
