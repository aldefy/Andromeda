package design.andromedacompose.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle

val LocalAndromedaTextStyle = compositionLocalOf { TextStyle.Default }

@Composable
fun ProvideAndromedaTextStyle(
    value: TextStyle,
    content: @Composable () -> Unit,
) {
    val mergedStyle = LocalAndromedaTextStyle.current.merge(value)
    CompositionLocalProvider(LocalAndromedaTextStyle provides mergedStyle, content = content)
}
