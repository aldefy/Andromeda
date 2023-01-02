package design.andromedacompose.components.inputs.field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.foundation.typography.ProvideMergedTextStyle

@Composable
internal fun FieldLabel(
    content: @Composable () -> Unit,
) {
    ProvideMergedTextStyle(AndromedaTheme.typography.bodyModerateDefaultTypographyStyle) {
        Box(Modifier.padding(bottom = 4.dp)) {
            content()
        }
    }
}
