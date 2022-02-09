package design.andromedacompose.foundation.typography

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.text.TextStyle

public val LocalTextStyle: ProvidableCompositionLocal<TextStyle> = LocalTextStyle

@Composable
public fun ProvideMergedTextStyle(value: TextStyle, content: @Composable () -> Unit) {
    ProvideTextStyle(value, content)
}
