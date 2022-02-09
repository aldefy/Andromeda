package design.andromedacompose.illustrations

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Illustration(
    illustration: IllustrationResource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Image(
        modifier = modifier,
        painter = illustration.resource(),
        contentDescription = contentDescription ?: illustration.resourceName()
    )
}
