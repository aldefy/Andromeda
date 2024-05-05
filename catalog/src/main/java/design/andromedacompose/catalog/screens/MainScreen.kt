package design.andromedacompose.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.icons.rounded.Colorize
import androidx.compose.material.icons.rounded.FormatBold
import androidx.compose.material.icons.rounded.Gamepad
import androidx.compose.material.icons.rounded.Input
import androidx.compose.material.icons.rounded.SpaceBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import design.andromedacompose.catalog.MainActions
import design.andromedacompose.catalog.Screen
import design.andromedacompose.catalog.ThemeToggle
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.Icon
import design.andromedacompose.components.Text
import design.andromedacompose.icons.AndromedaIcons
import androidx.compose.material.icons.Icons as MaterialIcons

@Composable
fun MainScreen(
    actions: MainActions,
    onToggleTheme: ThemeToggle,
) {
    val foundation = listOf<Triple<String, @Composable () -> Unit, () -> Unit>>(
        Triple(
            "Colors",
            {
                Icon(
                    MaterialIcons.Rounded.Colorize,
                    null
                )
            },
            actions::showColors
        ),
        Triple(
            "Icons",
            {
                Icon(
                    AndromedaIcons.Alert,
                    null
                )
            },
            actions::showIcons
        ),
        Triple(
            "Illustrations",
            {
                Icon(
                    AndromedaIcons.Photos,
                    null
                )
            },
            actions::showIllustrations
        ),
        Triple(
            "Typography",
            {
                Icon(
                    MaterialIcons.Rounded.FormatBold,
                    null
                )
            },
            actions::showTypography
        ),
    )

    val components = listOf<Triple<String, @Composable () -> Unit, () -> Unit>>(
        Triple(
            "Button",
            {
                Icon(
                    MaterialIcons.Rounded.Gamepad,
                    null
                )
            },
            actions::showButton
        ),
        Triple(
            "Nav Bar",
            {
                Icon(
                    MaterialIcons.Rounded.SpaceBar,
                    null
                )
            },
            actions::showNavBar
        ),
        Triple(
            "Text Fields",
            {
                Icon(
                    MaterialIcons.Rounded.Input,
                    null
                )
            },
            actions::showTextFields
        ),
    )

    Screen(title = "Andromeda Catalog", themeToggle = onToggleTheme) {
        BoxWithConstraints {
            val columns = (maxWidth / 156.dp).toInt().coerceAtLeast(1)
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.size(16.dp))
                CardRowItems("Foundation", foundation, 1)
                CardRowItems("Components", components, 1)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardRowItems(
    title: String,
    items: List<Triple<String, @Composable () -> Unit, () -> Unit>>,
    columns: Int,
) {
    Text(
        text = title,
        style = AndromedaTheme.typography.titleModerateBoldTextStyle,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
    Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        for (rowItems in items.chunked(columns)) {
            Row {
                Items(rowItems)
                val missingColumns = columns - rowItems.size
                if (missingColumns > 0) {
                    Spacer(Modifier.weight(missingColumns.toFloat()))
                }
            }
        }
    }
}

@Composable
private fun RowScope.Items(rowItems: List<Triple<String, @Composable () -> Unit, () -> Unit>>) {
    for (item in rowItems) {
        Item(item.first, item.second, item.third)
    }
}

@Composable
private fun RowScope.Item(title: String, icon: @Composable () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .weight(1f),
        backgroundColor = AndromedaTheme.colors.primaryColors.background,
        elevation = 2.dp
    ) {
        Row(
            Modifier
                .clip(AndromedaTheme.shapes.normal)
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Spacer(Modifier.size(8.dp))
            Text(text = title, style = AndromedaTheme.typography.titleModerateDemiTextStyle)
        }
    }
}
