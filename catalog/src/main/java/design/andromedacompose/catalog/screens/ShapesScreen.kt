package design.andromedacompose.catalog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.catalog.Screen
import design.andromedacompose.components.Text

@Composable
fun ShapesScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Shapes",
        onNavigateUp = onUpClick,
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Shape Tokens",
                style = AndromedaTheme.typography.titleModerateBoldTextStyle,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val shapes = listOf(
                "small (4dp)" to AndromedaTheme.shapes.small,
                "normal (6dp)" to AndromedaTheme.shapes.normal,
                "large (12dp)" to AndromedaTheme.shapes.large,
                "buttonShape (8dp)" to AndromedaTheme.shapes.buttonShape,
                "dialogShape (8dp)" to AndromedaTheme.shapes.dialogShape,
                "bottomSheet (16dp top)" to AndromedaTheme.shapes.bottomSheet,
            )

            shapes.forEach { (name, shape) ->
                ShapeItem(name, shape)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ShapeItem(name: String, shape: Shape) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Box(
            Modifier
                .size(width = 120.dp, height = 64.dp)
                .clip(shape)
                .background(AndromedaTheme.colors.primaryColors.active)
                .border(1.dp, AndromedaTheme.colors.borderColors.active, shape)
        )
    }
}
