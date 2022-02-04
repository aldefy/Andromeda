package io.andromeda.design.foundation.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val ZeroX = 0.dp
    val OneX = 4.dp

    fun times(x: Float = 2.0f): Dp {
        return OneX.times(x)
    }
}
