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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.catalog.Screen
import design.andromedacompose.components.Text
import design.andromedacompose.foundation.tokens.AndromedaElevation
import design.andromedacompose.foundation.tokens.AndromedaOpacity
import design.andromedacompose.foundation.tokens.Spacing

@Composable
fun TokensScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Design Tokens",
        onNavigateUp = onUpClick,
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            SpacingSection()
            Spacer(Modifier.height(24.dp))
            ElevationSection()
            Spacer(Modifier.height(24.dp))
            OpacitySection()
        }
    }
}

@Composable
private fun SpacingSection() {
    Text(
        text = "Spacing",
        style = AndromedaTheme.typography.titleModerateBoldTextStyle,
        modifier = Modifier.padding(bottom = 8.dp),
    )
    val spacings =
        listOf(
            "None" to Spacing.None,
            "XXSmall (2dp)" to Spacing.XXSmall,
            "XSmall (4dp)" to Spacing.XSmall,
            "Small (8dp)" to Spacing.Small,
            "Medium (16dp)" to Spacing.Medium,
            "Large (24dp)" to Spacing.Large,
            "XLarge (32dp)" to Spacing.XLarge,
            "XXLarge (48dp)" to Spacing.XXLarge,
        )
    spacings.forEach { (name, value) ->
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = name,
                fontSize = 12.sp,
                modifier = Modifier.width(120.dp),
            )
            Box(
                Modifier
                    .height(16.dp)
                    .width(value.coerceAtLeast(1.dp))
                    .background(AndromedaTheme.colors.primaryColors.active),
            )
        }
    }
}

@Composable
private fun ElevationSection() {
    Text(
        text = "Elevation",
        style = AndromedaTheme.typography.titleModerateBoldTextStyle,
        modifier = Modifier.padding(bottom = 8.dp),
    )
    val elevations =
        listOf(
            "None" to AndromedaElevation.None,
            "XSmall (1dp)" to AndromedaElevation.XSmall,
            "Small (2dp)" to AndromedaElevation.Small,
            "Medium (4dp)" to AndromedaElevation.Medium,
            "Large (8dp)" to AndromedaElevation.Large,
            "XLarge (16dp)" to AndromedaElevation.XLarge,
        )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        elevations.forEach { (name, value) ->
            ElevationCard(name, value)
        }
    }
}

@Composable
private fun ElevationCard(
    name: String,
    elevation: Dp,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .size(48.dp)
                .shadow(elevation, AndromedaTheme.shapes.small)
                .background(
                    AndromedaTheme.colors.primaryColors.background,
                    AndromedaTheme.shapes.small,
                ),
        )
        Spacer(Modifier.height(4.dp))
        Text(text = name, fontSize = 10.sp)
    }
}

@Composable
private fun OpacitySection() {
    Text(
        text = "Opacity",
        style = AndromedaTheme.typography.titleModerateBoldTextStyle,
        modifier = Modifier.padding(bottom = 8.dp),
    )
    val opacities =
        listOf(
            "Full (1.0)" to AndromedaOpacity.Full,
            "Minor (0.80)" to AndromedaOpacity.Minor,
            "Subtle (0.66)" to AndromedaOpacity.Subtle,
            "Disabled (0.48)" to AndromedaOpacity.Disabled,
            "Divider (0.12)" to AndromedaOpacity.Divider,
            "Transparent (0)" to AndromedaOpacity.Transparent,
        )
    opacities.forEach { (name, alpha) ->
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = name,
                fontSize = 12.sp,
                modifier = Modifier.width(120.dp),
            )
            Box(
                Modifier
                    .height(24.dp)
                    .fillMaxWidth()
                    .border(1.dp, AndromedaTheme.colors.borderColors.inactive)
                    .background(
                        AndromedaTheme.colors.primaryColors.active.copy(alpha = alpha),
                    ),
            )
        }
    }
}
