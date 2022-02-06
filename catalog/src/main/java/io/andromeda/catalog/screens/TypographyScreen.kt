package io.andromeda.catalog.screens

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
import io.andromeda.design.components.Divider
import io.andromeda.catalog.Screen
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.components.Text

@Composable
fun TypographyScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Typography",
        onNavigateUp = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            TypographyScreenContent()
        }
    }
}

@Preview
@Composable
private fun TypographyScreenContent() {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Title styles", style = AndromedaTheme.typography.titleModerateDemiTextStyle)
        Divider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text("Hero Title", style = AndromedaTheme.typography.titleHeroTextStyle)
        Text("Moderate Demi Title", style = AndromedaTheme.typography.titleModerateDemiTextStyle)
        Text("Moderate Bold Title", style = AndromedaTheme.typography.titleModerateBoldTextStyle)
        Text("Small Demi Title", style = AndromedaTheme.typography.titleSmallDemiTextStyle)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Body styles", style = AndromedaTheme.typography.titleModerateDemiTextStyle)
        Divider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text("Small Body", style = AndromedaTheme.typography.bodySmallDefaultTypographyStyle)
        Text("Moderate Body", style = AndromedaTheme.typography.bodyModerateDefaultTypographyStyle)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Caption styles", style = AndromedaTheme.typography.titleModerateDemiTextStyle)
        Divider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Moderate Demi Caption",
            style = AndromedaTheme.typography.captionModerateDemiDefaultTypographyStyle
        )
        Text(
            "Moderate Book Caption",
            style = AndromedaTheme.typography.captionModerateBookDefaultTypographyStyle
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
