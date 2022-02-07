package io.andromeda.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.andromeda.catalog.Screen
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.components.BackButton
import io.andromeda.design.components.Divider
import io.andromeda.design.components.Icon
import io.andromeda.design.components.Text
import io.andromeda.design.components.navbar.AndromedaNavBar
import io.andromeda.design.foundation.tokens.Spacing
import io.andromeda.icons.AndromedaIcons

@Composable
fun NavBarScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Nav Bar",
        onNavigateUp = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            NavBarScreenContent()
        }
    }
}

@Preview
@Composable
fun NavBarScreenContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = "Title and subtitle",
            style = AndromedaTheme.typography.titleModerateDemiTextStyle
        )
        AndromedaNavBar(title = "Title", subTitle = "Subtitle")
        Spacer(modifier = Modifier.height(Spacing.times(2f)))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "Custom Title", style = AndromedaTheme.typography.titleModerateDemiTextStyle)
        AndromedaNavBar(titleView = {
            Text(
                text = "Custom title",
            )
        })
        Spacer(modifier = Modifier.height(Spacing.times(2f)))
        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "Menu showcase", style = AndromedaTheme.typography.titleModerateDemiTextStyle)
        AndromedaNavBar(
            titleView = {
                Text(
                    text = "Custom menu",
                )
            },
            menuView = {
                Icon(
                    painter = AndromedaIcons.InformationCircle,
                    contentDescription = "Demo icon",
                    onClick = {}
                )
            },
            navigationIcon = {
                BackButton(onBackPressed = { /*TODO*/ })
            }
        )
        Spacer(modifier = Modifier.height(Spacing.times(2f)))
        Divider(modifier = Modifier.fillMaxWidth())
    }
}
