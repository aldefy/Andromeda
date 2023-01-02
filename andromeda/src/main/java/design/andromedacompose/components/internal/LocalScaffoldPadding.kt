package design.andromedacompose.components.internal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

public val LocalScaffoldPadding: ProvidableCompositionLocal<PaddingValues> =
    staticCompositionLocalOf { PaddingValues(0.dp) }
