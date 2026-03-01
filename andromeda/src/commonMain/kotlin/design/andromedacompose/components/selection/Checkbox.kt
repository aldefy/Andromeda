package design.andromedacompose.components.selection

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
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
 * Represents the three possible states of a checkbox.
 */
public enum class ToggleableState {
    Checked,
    Unchecked,
    Indeterminate,
}

/**
 * Andromeda Checkbox — a custom-drawn checkbox with animated check mark.
 *
 * @param checked Whether the checkbox is checked.
 * @param onCheckedChange Called when the checkbox is toggled.
 * @param modifier Modifier applied to the checkbox.
 * @param enabled Whether the checkbox is interactive.
 * @param checkedColor Fill color when checked.
 * @param uncheckedColor Border color when unchecked.
 * @param checkmarkColor Color of the check mark.
 * @param size Size of the checkbox box.
 */
@Composable
public fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedColor: Color = AndromedaTheme.colors.primaryColors.active,
    uncheckedColor: Color = AndromedaTheme.colors.borderColors.active,
    checkmarkColor: Color = Color.White,
    size: Dp = 22.dp,
) {
    val state = if (checked) ToggleableState.Checked else ToggleableState.Unchecked
    TriStateCheckbox(
        state = state,
        onClick = { onCheckedChange(!checked) },
        modifier = modifier,
        enabled = enabled,
        checkedColor = checkedColor,
        uncheckedColor = uncheckedColor,
        checkmarkColor = checkmarkColor,
        size = size,
    )
}

/**
 * Andromeda Checkbox with label text.
 */
@Composable
public fun LabeledCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedColor: Color = AndromedaTheme.colors.primaryColors.active,
    uncheckedColor: Color = AndromedaTheme.colors.borderColors.active,
    checkmarkColor: Color = Color.White,
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
                    role = Role.Checkbox,
                    onClick = { onCheckedChange(!checked) },
                )
                .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CheckboxCanvas(
            state = if (checked) ToggleableState.Checked else ToggleableState.Unchecked,
            modifier = Modifier,
            enabled = enabled,
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor,
            checkmarkColor = checkmarkColor,
            size = 22.dp,
        )
        Spacer(Modifier.width(12.dp))
        label()
    }
}

/**
 * Tri-state checkbox supporting Checked, Unchecked, and Indeterminate states.
 *
 * @param state The current [ToggleableState].
 * @param onClick Called when clicked. Null to disable click handling.
 */
@Composable
public fun TriStateCheckbox(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checkedColor: Color = AndromedaTheme.colors.primaryColors.active,
    uncheckedColor: Color = AndromedaTheme.colors.borderColors.active,
    checkmarkColor: Color = Color.White,
    size: Dp = 22.dp,
) {
    val clickModifier =
        if (onClick != null) {
            Modifier
                .minimumInteractiveSize()
                .toggleableSemantics(
                    checked = state == ToggleableState.Checked,
                    role = Role.Checkbox,
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = AndromedaIndication,
                    enabled = enabled,
                    role = Role.Checkbox,
                    onClick = onClick,
                )
        } else {
            Modifier
        }

    CheckboxCanvas(
        state = state,
        modifier = modifier.then(clickModifier),
        enabled = enabled,
        checkedColor = checkedColor,
        uncheckedColor = uncheckedColor,
        checkmarkColor = checkmarkColor,
        size = size,
    )
}

@Composable
private fun CheckboxCanvas(
    state: ToggleableState,
    modifier: Modifier,
    enabled: Boolean,
    checkedColor: Color,
    uncheckedColor: Color,
    checkmarkColor: Color,
    size: Dp,
) {
    val isCheckedOrIndeterminate = state != ToggleableState.Unchecked
    val progress =
        animateFloatAsState(
            targetValue = if (isCheckedOrIndeterminate) 1f else 0f,
            animationSpec = tween(durationMillis = AndromedaMotion.Fast),
            label = "checkboxProgress",
        )
    val alpha = if (enabled) AndromedaOpacity.Full else AndromedaOpacity.Disabled

    Canvas(modifier = modifier.size(size)) {
        val boxSize = this.size
        val cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
        val borderWidth = 2.dp.toPx()
        val fillAlpha = progress.value * alpha

        // Background fill (animated)
        if (isCheckedOrIndeterminate) {
            drawRoundRect(
                color = checkedColor,
                cornerRadius = cornerRadius,
                alpha = fillAlpha,
            )
        }

        // Border
        drawRoundRect(
            color = if (isCheckedOrIndeterminate) checkedColor else uncheckedColor,
            cornerRadius = cornerRadius,
            style = Stroke(width = borderWidth),
            alpha = alpha,
        )

        // Check mark or indeterminate dash
        if (progress.value > 0f) {
            when (state) {
                ToggleableState.Checked ->
                    drawCheckMark(
                        color = checkmarkColor,
                        progress = progress.value,
                        alpha = alpha,
                        boxSize = boxSize,
                    )
                ToggleableState.Indeterminate ->
                    drawIndeterminateDash(
                        color = checkmarkColor,
                        alpha = alpha,
                        boxSize = boxSize,
                    )
                else -> {}
            }
        }
    }
}

private fun DrawScope.drawCheckMark(
    color: Color,
    progress: Float,
    alpha: Float,
    boxSize: Size,
) {
    val stroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
    val path =
        Path().apply {
            // Check mark path: down-left to bottom, then up-right to top
            val startX = boxSize.width * 0.22f
            val startY = boxSize.height * 0.52f
            val midX = boxSize.width * 0.42f
            val midY = boxSize.height * 0.72f
            val endX = boxSize.width * 0.78f
            val endY = boxSize.height * 0.30f

            moveTo(startX, startY)
            if (progress < 0.5f) {
                val p = progress * 2f
                lineTo(
                    startX + (midX - startX) * p,
                    startY + (midY - startY) * p,
                )
            } else {
                lineTo(midX, midY)
                val p = (progress - 0.5f) * 2f
                lineTo(
                    midX + (endX - midX) * p,
                    midY + (endY - midY) * p,
                )
            }
        }
    drawPath(path, color, alpha = alpha, style = stroke)
}

private fun DrawScope.drawIndeterminateDash(
    color: Color,
    alpha: Float,
    boxSize: Size,
) {
    val stroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
    val y = boxSize.height / 2f
    val padding = boxSize.width * 0.22f
    drawLine(
        color = color,
        start = Offset(padding, y),
        end = Offset(boxSize.width - padding, y),
        strokeWidth = stroke.width,
        cap = StrokeCap.Round,
        alpha = alpha,
    )
}
