package design.andromedacompose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import design.andromedacompose.catalog.Screen
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.Text
import design.andromedacompose.components.buttons.Button

@Composable
fun ButtonScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Buttons",
        onNavigateUp = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            ButtonScreenContent()
        }
    }
}

@Preview
@Composable
fun ButtonScreenContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        val maxWidth = Modifier.fillMaxWidth()

        Button(onClick = {}, modifier = maxWidth) { Text("Primary colors") }
        Button(
            onClick = {},
            modifier = maxWidth,
            backgroundColor = AndromedaTheme.colors.secondaryColors.background
        ) { Text("Secondary colors") }
        Button(
            onClick = {},
            modifier = maxWidth,
            backgroundColor = AndromedaTheme.colors.tertiaryColors.background
        ) { Text("Tertiary colors") }
    }
}
