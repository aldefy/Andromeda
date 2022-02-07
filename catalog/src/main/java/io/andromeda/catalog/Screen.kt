package io.andromeda.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.BrightnessMedium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.derivedWindowInsetsTypeOf
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.components.BackButton
import io.andromeda.design.components.navbar.AndromedaNavBar
import io.andromeda.design.components.navbar.NavBarDefaultElevation
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
                    Text(
                        text = title,
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
            val ime = LocalWindowInsets.current.ime
            val navBars = LocalWindowInsets.current.navigationBars
            val insets = remember(ime, navBars) { derivedWindowInsetsTypeOf(ime, navBars) }
            val contentPadding = rememberInsetsPaddingValues(insets)
            Box(
                Modifier
                    .fillMaxSize()
                    .background(background)
            ) {
                content(contentPadding)
            }
        },
    )
}
