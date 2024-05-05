package design.andromedacompose.catalog.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.catalog.Screen
import design.andromedacompose.components.Icon
import design.andromedacompose.components.Text
import design.andromedacompose.icons.AndromedaIcons
import kotlin.reflect.full.memberProperties

@Composable
fun IconsScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Icons",
        onNavigateUp = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            IconsScreenContent()
        }
    }
}

@Preview
@Composable
fun IconsScreenContent() {
    val icons: List<Pair<String, Painter>> = AndromedaIcons::class.memberProperties.map {
        it.name to (it.getter.call(AndromedaIcons, currentComposer, 0) as Painter)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(96.dp),
    ) {
        items(icons) { (name, icon) ->
            Column(
                Modifier
                    .padding(8.dp)
                    .clickable(
                        onClick = { /* Ignoring onClick */ },
                        indication = rememberRipple(
                            bounded = false,
                            color = AndromedaTheme.colors.contentColors.normal
                        ),
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = icon, contentDescription = name)
                Text(
                    name,
                    Modifier.padding(top = 4.dp),
                    style = AndromedaTheme.typography.captionModerateDemiDefaultTypographyStyle,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
