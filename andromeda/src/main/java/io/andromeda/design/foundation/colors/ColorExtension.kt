package io.andromeda.design.foundation.colors

import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color as ComposeColor

fun ComposeColor.Companion.parse(colorString: String): ComposeColor =
    ComposeColor(color = AndroidColor.parseColor(colorString))
