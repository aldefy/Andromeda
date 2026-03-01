package design.andromedacompose.foundation.tokens

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring

object AndromedaMotion {
    // Durations (ms)
    val Instant: Int = 0
    val Fast: Int = 100
    val Normal: Int = 200
    val Slow: Int = 350
    val XSlow: Int = 500

    // Easings
    val EaseIn: Easing = CubicBezierEasing(0.4f, 0f, 1f, 1f)
    val EaseOut: Easing = CubicBezierEasing(0f, 0f, 0.2f, 1f)
    val EaseInOut: Easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)

    // Springs
    fun <T> defaultSpring(): SpringSpec<T> = spring(
        dampingRatio = 0.85f,
        stiffness = 400f,
    )
}
