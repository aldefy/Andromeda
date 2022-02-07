package io.andromeda.design.components.navbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.andromeda.design.AndromedaTheme
import io.andromeda.design.foundation.ContentEmphasis
import io.andromeda.design.foundation.LocalContentEmphasis
import io.andromeda.design.foundation.colors.contentColorFor
import io.andromeda.design.foundation.typography.ProvideMergedTextStyle
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun AndromedaNavBar(
    modifier: Modifier = Modifier,
    backgroundColor: ComposeColor = AndromedaTheme.colors.primaryColors.active,
    elevation: Dp = NavBarDefaultElevation,
    title: String = "",
    subTitle: String = "",
    contentPadding: PaddingValues = ContentPadding,
    contentColor: ComposeColor = contentColorFor(backgroundColor = backgroundColor),
    barHeight: Dp = NavBarHeight,
    shape: Shape = RectangleShape,
    navigationIcon: @Composable (() -> Unit)? = null,
    titleView: @Composable RowScope.(Modifier) -> Unit = { titleModifier ->
        DefaultAndromedaNavBarTitle(
            modifier = titleModifier,
            title = title,
            subTitle = subTitle
        )
    },
    menuView: @Composable RowScope.(Modifier) -> Unit = { DefaultAndromedaNavBarMenu(it) },
) {
    Surface(
        color = backgroundColor,
        elevation = elevation,
        shape = shape,
        contentColor = contentColor,
        modifier = modifier.then(
            Modifier.padding(
                rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.systemBars,
                    applyBottom = false
                )
            )
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(barHeight),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                if (navigationIcon == null) {
                    Spacer(TitleInsetWithoutIcon)
                } else {
                    Row(TitleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                            content = navigationIcon
                        )
                    }
                }

                ProvideMergedTextStyle(
                    value = TextStyle(
                        fontSize = 19.sp,
                        letterSpacing = 0.15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                ) {
                    CompositionLocalProvider(
                        LocalContentEmphasis provides ContentEmphasis.Normal
                    ) {
                        Box(
                            Modifier.semantics(mergeDescendants = true) {
                                testTag = "title"
                            }
                        ) {
                            this@Row.titleView(Modifier.fillMaxWidth())
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                menuView(Modifier.padding(end = 8.dp))
            }
        )
    }
}

@Composable
fun DefaultAndromedaNavBarTitle(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title,
            style = AndromedaTheme.typography.titleModerateBoldTextStyle,
            color = AndromedaTheme.colors.contentColors.normal
        )
        Text(
            text = subTitle,
            style = AndromedaTheme.typography.titleSmallDemiTextStyle,
            color = AndromedaTheme.colors.contentColors.subtle
        )
    }
}

@Preview(group = "Andromeda")
@Composable
fun DefaultAndromedaNavBarTitlePreview() {
    AndromedaTheme {
        DefaultAndromedaNavBarTitle("Heelo world", "i am subtitle")
    }
}

@Composable
fun DefaultAndromedaNavBarMenu(modifier: Modifier) {
}

val ContentPadding = PaddingValues(
    start = 4.dp,
    end = 4.dp
)
val NavBarNoElevation = 0.dp
val NavBarDefaultElevation = 2.dp
private val NavBarHeight = 56.dp
private val NavBarHorizontalPadding = 4.dp

// Start inset for the title when there is no navigation icon provided
private val TitleInsetWithoutIcon = Modifier.width(16.dp - NavBarHorizontalPadding)

// Start inset for the title when there is a navigation icon provided
private val TitleIconModifier = Modifier
    .fillMaxHeight()
    .width(72.dp - NavBarHorizontalPadding)
