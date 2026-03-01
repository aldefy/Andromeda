package design.andromedacompose.illustrations

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Illustration(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}
