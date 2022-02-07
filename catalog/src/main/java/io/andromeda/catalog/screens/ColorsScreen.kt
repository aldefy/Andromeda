package io.andromeda.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.andromeda.catalog.Screen
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.components.Text
import io.andromeda.design.foundation.colors.FillColors
import io.andromeda.design.foundation.colors.contentColorFor
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun ColorsScreen(
    onUpClick: () -> Unit,
) {
    Screen(
        title = "Colors",
        onNavigateUp = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ColorsScreenContent()
        }
    }
}

@Preview
@Composable
private fun ColorsScreenContent() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = "Primary",
                style = AndromedaTheme.typography.titleHeroTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            FillColors(colors = AndromedaTheme.colors.primaryColors)
        }
        item {
            Text(
                text = "Secondary",
                style = AndromedaTheme.typography.titleHeroTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            FillColors(colors = AndromedaTheme.colors.secondaryColors)
        }
        item {
            Text(
                text = "Tertiary",
                style = AndromedaTheme.typography.titleHeroTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            FillColors(colors = AndromedaTheme.colors.tertiaryColors)
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FillColors(colors: FillColors) {
    val listOfColors = listOf(
        ColorToShow(
            color = colors.background,
            name = "Background",
            contentColor = AndromedaTheme.colors.contentColors.normal
        ),
        ColorToShow(
            color = colors.active,
            name = "Active",
            contentColor = AndromedaTheme.colors.contentColors.normal
        ),
        ColorToShow(
            color = colors.error,
            name = "Error",
            contentColor = AndromedaTheme.colors.contentColors.normal
        ),
        ColorToShow(
            color = colors.mute,
            name = "Mute",
            contentColor = AndromedaTheme.colors.contentColors.normal
        ),
        ColorToShow(
            color = colors.pressed,
            name = "Pressed",
            contentColor = AndromedaTheme.colors.contentColors.normal
        ),
        ColorToShow(
            color = colors.alt,
            name = "Alt",
            contentColor = AndromedaTheme.colors.contentColors.minor
        ),
    )
    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            ColorItem(colorToShow = listOfColors[0])
            ColorItem(colorToShow = listOfColors[1])
        }
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            ColorItem(colorToShow = listOfColors[2])
            ColorItem(colorToShow = listOfColors[3])
        }
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            ColorItem(colorToShow = listOfColors[4])
            ColorItem(colorToShow = listOfColors[5])
        }
    }
    /*LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(listOfColors) {
            ColorItem(it)
        }
    }*/
}

data class ColorToShow(
    val name: String,
    val color: ComposeColor,
    val contentColor: ComposeColor
)

@Composable
private fun ColorItem(
    colorToShow: ColorToShow,
) {
    Box(
        Modifier
            .background(colorToShow.color)
            .clickable(
                onClick = { /* Ignoring onClick */ },
                indication = rememberRipple(bounded = true, color = colorToShow.contentColor),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
            .width(100.dp)
            .height(52.dp)
    ) {
        Text(
            colorToShow.name,
            Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            color = colorToShow.contentColor.takeOrElse { contentColorFor(colorToShow.color) },
        )
    }
}
