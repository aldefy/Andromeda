package design.andromedacompose.components.internal

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.Surface
import design.andromedacompose.foundation.colors.defaultDarkColors
import design.andromedacompose.foundation.colors.defaultLightColors

@Composable
internal fun Preview(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = when (!isSystemInDarkTheme()) {
        true -> defaultLightColors()
        false -> defaultDarkColors()
    }
    AndromedaTheme(colors = colors) {
        Surface {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                content()
            }
        }
    }
}
