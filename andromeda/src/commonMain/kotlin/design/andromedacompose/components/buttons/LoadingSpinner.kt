package design.andromedacompose.components.buttons

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A simple circular loading spinner drawn with Canvas.
 * No Material dependency — pure Foundation + UI.
 */
@Composable
internal fun LoadingSpinner(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    size: Dp = 18.dp,
    strokeWidth: Dp = 2.dp,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinnerRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
        label = "spinnerAngle",
    )

    Canvas(
        modifier = modifier.size(size),
    ) {
        val stroke =
            Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
            )
        drawArc(
            color = color,
            startAngle = rotation,
            sweepAngle = 270f,
            useCenter = false,
            style = stroke,
        )
    }
}
