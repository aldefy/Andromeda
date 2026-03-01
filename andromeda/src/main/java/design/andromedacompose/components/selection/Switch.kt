package design.andromedacompose.components.selection

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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
 * Andromeda Switch — a toggle component with animated thumb.
 *
 * @param checked Whether the switch is on.
 * @param onCheckedChange Called when toggled. Null to disable interaction.
 * @param modifier Modifier applied to the switch.
 * @param enabled Whether the switch is interactive.
 * @param checkedTrackColor Track color when on.
 * @param uncheckedTrackColor Track color when off.
 * @param thumbColor Thumb color.
 * @param width Total width of the switch track.
 * @param height Total height of the switch track.
 */
@Composable
public fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedTrackColor: Color = AndromedaTheme.colors.primaryColors.active,
    uncheckedTrackColor: Color = AndromedaTheme.colors.borderColors.inactive,
    thumbColor: Color = Color.White,
    width: Dp = 48.dp,
    height: Dp = 28.dp,
) {
    val thumbDiameter = height - 4.dp
    val thumbPadding = 2.dp
    val thumbTravel = width - thumbDiameter - (thumbPadding * 2)

    val thumbOffset = animateDpAsState(
        targetValue = if (checked) thumbTravel else 0.dp,
        animationSpec = tween(durationMillis = AndromedaMotion.Fast),
        label = "switchThumb"
    )

    val alpha = if (enabled) AndromedaOpacity.Full else AndromedaOpacity.Disabled

    val clickModifier = if (onCheckedChange != null) {
        Modifier
            .minimumInteractiveSize()
            .toggleableSemantics(
                checked = checked,
                role = Role.Switch,
                checkedLabel = "On",
                uncheckedLabel = "Off",
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = AndromedaIndication,
                enabled = enabled,
                role = Role.Switch,
                onClick = { onCheckedChange(!checked) },
            )
    } else {
        Modifier
    }

    Canvas(
        modifier = modifier
            .then(clickModifier)
            .requiredSize(width = width, height = height)
    ) {
        val cornerRadius = CornerRadius(size.height / 2f)
        val trackColor = if (checked) checkedTrackColor else uncheckedTrackColor

        // Track
        drawRoundRect(
            color = trackColor,
            cornerRadius = cornerRadius,
            alpha = alpha,
        )

        // Thumb
        val thumbRadius = thumbDiameter.toPx() / 2f
        val thumbX = thumbPadding.toPx() + thumbRadius + thumbOffset.value.toPx()
        val thumbY = size.height / 2f

        // Thumb shadow
        drawCircle(
            color = Color.Black,
            radius = thumbRadius + 0.5.dp.toPx(),
            center = Offset(thumbX, thumbY + 1.dp.toPx()),
            alpha = 0.1f * alpha,
        )

        // Thumb
        drawCircle(
            color = thumbColor,
            radius = thumbRadius,
            center = Offset(thumbX, thumbY),
            alpha = alpha,
        )
    }
}

/**
 * Switch with a label. The entire row is clickable.
 */
@Composable
public fun LabeledSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedTrackColor: Color = AndromedaTheme.colors.primaryColors.active,
    uncheckedTrackColor: Color = AndromedaTheme.colors.borderColors.inactive,
    thumbColor: Color = Color.White,
    label: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .minimumInteractiveSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = AndromedaIndication,
                enabled = enabled,
                role = Role.Switch,
                onClick = { onCheckedChange(!checked) },
            )
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        label()
        Spacer(Modifier.width(12.dp).weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            checkedTrackColor = checkedTrackColor,
            uncheckedTrackColor = uncheckedTrackColor,
            thumbColor = thumbColor,
        )
    }
}
