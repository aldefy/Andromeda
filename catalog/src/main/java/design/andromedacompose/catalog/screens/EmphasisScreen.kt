package design.andromedacompose.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.catalog.Screen
import design.andromedacompose.components.Icon
import design.andromedacompose.components.Text
import design.andromedacompose.foundation.ContentEmphasis
import design.andromedacompose.foundation.ProvideContentEmphasis
import design.andromedacompose.icons.AndromedaSystemIcons

@Composable
fun EmphasisScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Content Emphasis",
        onNavigateUp = onUpClick,
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Text(
                text = "Emphasis Levels",
                style = AndromedaTheme.typography.titleModerateBoldTextStyle,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = "Controls text and icon visibility to create visual hierarchy",
                fontSize = 12.sp,
                color = AndromedaTheme.colors.contentColors.subtle,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            val emphases =
                listOf(
                    "Normal (1.0)" to ContentEmphasis.Normal,
                    "Minor (0.80)" to ContentEmphasis.Minor,
                    "Subtle (0.66)" to ContentEmphasis.Subtle,
                    "Disabled (0.48)" to ContentEmphasis.Disabled,
                )

            emphases.forEach { (name, emphasis) ->
                EmphasisRow(name, emphasis)
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Applied to Text",
                style = AndromedaTheme.typography.titleModerateBoldTextStyle,
                modifier = Modifier.padding(bottom = 12.dp),
            )

            emphases.forEach { (_, emphasis) ->
                ProvideContentEmphasis(emphasis) {
                    Text(
                        text = "The quick brown fox jumps over the lazy dog",
                        style = AndromedaTheme.typography.bodyModerateDefaultTypographyStyle,
                        emphasis = emphasis,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun EmphasisRow(
    name: String,
    emphasis: ContentEmphasis,
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProvideContentEmphasis(emphasis) {
            Icon(
                imageVector = AndromedaSystemIcons.Info,
                contentDescription = null,
                emphasis = emphasis,
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = name,
                emphasis = emphasis,
                style = AndromedaTheme.typography.titleModerateDemiTextStyle,
            )
        }
    }
}
