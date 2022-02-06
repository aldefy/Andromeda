package io.andromeda.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.andromeda.catalog.Screen
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.components.Text
import io.andromeda.illustrations.AndromedaIllustrations
import kotlin.reflect.full.memberProperties

@Composable
fun IllustrationsScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Illustrations",
        onNavigateUp = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            IllustrationsScreenContent()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun IllustrationsScreenContent() {
    val illustrations: List<Pair<String, Painter>> =
        AndromedaIllustrations::class.memberProperties.map {
            it.name to (it.getter.call(AndromedaIllustrations, currentComposer, 0) as Painter)
        }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(256.dp),
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.navigationBars,
            additionalStart = 8.dp,
            additionalTop = 8.dp,
            additionalEnd = 8.dp,
            additionalBottom = 8.dp
        )
    ) {
        items(illustrations) { (name, icon) ->
            Card(
                Modifier.padding(8.dp),
                backgroundColor = AndromedaTheme.colors.primaryColors.background,
                elevation = 4.dp
            ) {
                Column {
                    Text(
                        name,
                        Modifier.padding(top = 4.dp, start = 6.dp),
                        style = AndromedaTheme.typography.titleSmallDemiTextStyle,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        painter = icon,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        contentDescription = name
                    )
                }
            }
        }
    }
}
