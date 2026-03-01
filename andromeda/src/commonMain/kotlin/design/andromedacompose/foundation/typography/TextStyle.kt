package design.andromedacompose.foundation.typography

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.text.TextStyle
import design.andromedacompose.foundation.LocalAndromedaTextStyle
import design.andromedacompose.foundation.ProvideAndromedaTextStyle

public val LocalTextStyle: ProvidableCompositionLocal<TextStyle> = LocalAndromedaTextStyle

@Composable
public fun ProvideMergedTextStyle(
    value: TextStyle,
    content: @Composable () -> Unit,
) {
    ProvideAndromedaTextStyle(value, content)
}
