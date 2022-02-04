package io.andromeda.design.foundation.tokens

import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit as ComposeTextUnit

object TextUnit {
    private val Default = 1.sp
    val Small = 12.sp
    val Standard = 14.sp
    val Crisp = 16.sp

    /**
     * Helper function to generate a multiplied factor on 1.sp - default unit
     */
    fun times(x: Float = 14.0f): ComposeTextUnit {
        return Default.times(x)
    }
}
