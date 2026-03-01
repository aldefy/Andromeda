package design.andromedacompose.foundation.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val None: Dp = 0.dp
    val XXSmall: Dp = 2.dp
    val XSmall: Dp = 4.dp
    val Small: Dp = 8.dp
    val Medium: Dp = 16.dp
    val Large: Dp = 24.dp
    val XLarge: Dp = 32.dp
    val XXLarge: Dp = 48.dp

    // Backwards compatibility
    val ZeroX: Dp = None
    val OneX: Dp = XSmall

    fun times(x: Float = 2.0f): Dp {
        return OneX.times(x)
    }
}
