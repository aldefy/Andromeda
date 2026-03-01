package design.andromedacompose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.icons.AndromedaSystemIcons

/**
 * Basic back button, that shows an icon and calls [onBackPressed] when tapped.
 *
 * @param imageVector The icon to show.
 * @param onBackPressed Handler for the back action.
 * @param modifier Modifier for styling.
 */
@Composable
fun BackButton(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector = AndromedaSystemIcons.ArrowBack,
) {
    IconButton(
        modifier = modifier,
        onClick = onBackPressed
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = AndromedaTheme.colors.iconColors.default,
        )
    }
}

/**
 * Basic back button, that shows an icon and calls [onBackPressed] when tapped.
 *
 * @param painter The icon resource to show.
 * @param onBackPressed Handler for the back action.
 * @param modifier Modifier for styling.
 */
@Composable
fun BackButton(
    painter: Painter,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = onBackPressed
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = AndromedaTheme.colors.iconColors.default,
        )
    }
}
