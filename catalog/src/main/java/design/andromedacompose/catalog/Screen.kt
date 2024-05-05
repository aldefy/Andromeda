package design.andromedacompose.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.BackButton
import design.andromedacompose.components.Icon
import design.andromedacompose.components.IconButton
import design.andromedacompose.components.Text
import design.andromedacompose.components.navbar.AndromedaNavBar
import design.andromedacompose.components.navbar.NavBarDefaultElevation
import design.andromedacompose.foundation.typography.LocalTextStyle
import androidx.compose.ui.graphics.Color as ComposeColor

typealias OnNavigateUp = () -> Unit
typealias ThemeToggle = () -> Unit

@Composable
fun Screen(
    title: String,
    onNavigateUp: OnNavigateUp? = null,
    themeToggle: ThemeToggle? = null,
    background: ComposeColor = AndromedaTheme.colors.primaryColors.background,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    val backButton = @Composable {
        BackButton(
            imageVector = Icons.Default.ArrowBack,
            onBackPressed = onNavigateUp ?: {}
        )
    }
    Scaffold(
        topBar = {
            AndromedaNavBar(
                backgroundColor = background,
                titleView = {
                    val textStyle = LocalTextStyle.current
                    val mergedTextStyle =
                        textStyle.copy(color = AndromedaTheme.colors.contentColors.normal)

                    Text(
                        text = title,
                        style = mergedTextStyle
                    )
                },
                elevation = if (onNavigateUp == null) NavBarDefaultElevation else 4.dp,
                navigationIcon = if (onNavigateUp != null) backButton else null,
                menuView = {
                    themeToggle?.let { _themeToggle ->
                        IconButton(onClick = _themeToggle) {
                            Icon(
                                modifier = Modifier.size(20.dp, 32.dp),
                                imageVector = Icons.Rounded.BrightnessMedium,
                                contentDescription = stringResource(
                                    R.string.accessibility_toggle_theme
                                ),
                            )
                        }
                    }
                }
            )
        },
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(background)
            ) {
                content(it)
            }
        },
    )
}
