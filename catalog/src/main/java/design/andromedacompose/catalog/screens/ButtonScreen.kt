package design.andromedacompose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.catalog.Screen
import design.andromedacompose.components.Text
import design.andromedacompose.components.buttons.Button
import design.andromedacompose.components.buttons.ButtonDefaults
import design.andromedacompose.components.buttons.ButtonSize
import design.andromedacompose.components.buttons.ButtonVariant
import design.andromedacompose.icons.AndromedaSystemIcons

@Composable
fun ButtonScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Buttons",
        onNavigateUp = onUpClick,
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            // --- Variants ---
            SectionTitle("Variants")

            val fullWidth = Modifier.fillMaxWidth()

            Button(onClick = {}, modifier = fullWidth) {
                Text("Filled (Default)")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = {}, modifier = fullWidth, variant = ButtonVariant.Outlined) {
                Text("Outlined")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = {}, modifier = fullWidth, variant = ButtonVariant.Ghost) {
                Text("Ghost")
            }

            Spacer(Modifier.height(24.dp))

            // --- Sizes ---
            SectionTitle("Sizes")
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = {}, size = ButtonSize.Small) { Text("Small") }
                Button(onClick = {}) { Text("Medium") }
                Button(onClick = {}, size = ButtonSize.Large) { Text("Large") }
            }

            Spacer(Modifier.height(24.dp))

            // --- Color Families ---
            SectionTitle("Color Families")

            Button(onClick = {}, modifier = fullWidth) {
                Text("Primary (Default)")
            }
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {},
                modifier = fullWidth,
                colors =
                    ButtonDefaults.filledColors(
                        backgroundColor = AndromedaTheme.colors.secondaryColors.active,
                    ),
            ) {
                Text("Secondary")
            }
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {},
                modifier = fullWidth,
                colors =
                    ButtonDefaults.filledColors(
                        backgroundColor = AndromedaTheme.colors.tertiaryColors.active,
                    ),
            ) {
                Text("Tertiary")
            }

            Spacer(Modifier.height(24.dp))

            // --- Outlined with Color Families ---
            SectionTitle("Outlined Colors")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}, variant = ButtonVariant.Outlined) {
                    Text("Primary")
                }
                Button(
                    onClick = {},
                    variant = ButtonVariant.Outlined,
                    colors =
                        ButtonDefaults.outlinedColors(
                            contentColor = AndromedaTheme.colors.secondaryColors.active,
                            borderColor = AndromedaTheme.colors.secondaryColors.active,
                        ),
                ) {
                    Text("Secondary")
                }
                Button(
                    onClick = {},
                    variant = ButtonVariant.Outlined,
                    colors =
                        ButtonDefaults.outlinedColors(
                            contentColor = AndromedaTheme.colors.tertiaryColors.active,
                            borderColor = AndromedaTheme.colors.tertiaryColors.active,
                        ),
                ) {
                    Text("Tertiary")
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Loading State ---
            SectionTitle("Loading State")
            var isLoading by remember { mutableStateOf(false) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { isLoading = !isLoading }, isLoading = isLoading) {
                    Text(if (isLoading) "Loading..." else "Tap to Load")
                }
                Button(
                    onClick = {},
                    variant = ButtonVariant.Outlined,
                    isLoading = true,
                ) {
                    Text("Outlined")
                }
                Button(
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    isLoading = true,
                ) {
                    Text("Ghost")
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Disabled State ---
            SectionTitle("Disabled State")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}, enabled = false) { Text("Filled") }
                Button(onClick = {}, enabled = false, variant = ButtonVariant.Outlined) {
                    Text("Outlined")
                }
                Button(onClick = {}, enabled = false, variant = ButtonVariant.Ghost) {
                    Text("Ghost")
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Icon + Text ---
            SectionTitle("Icon + Text")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}) {
                    design.andromedacompose.components.Icon(
                        imageVector = AndromedaSystemIcons.Edit,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text("Edit")
                }
                Button(onClick = {}, variant = ButtonVariant.Outlined) {
                    design.andromedacompose.components.Icon(
                        imageVector = AndromedaSystemIcons.Info,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text("Info")
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Size Comparison (all variants at each size) ---
            SectionTitle("Size × Variant Matrix")
            for (size in ButtonSize.entries) {
                Text(
                    text = size.name,
                    fontSize = 12.sp,
                    color = AndromedaTheme.colors.contentColors.subtle,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 12.dp),
                ) {
                    Button(onClick = {}, size = size) { Text("Filled") }
                    Button(onClick = {}, size = size, variant = ButtonVariant.Outlined) {
                        Text("Outlined")
                    }
                    Button(onClick = {}, size = size, variant = ButtonVariant.Ghost) {
                        Text("Ghost")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = AndromedaTheme.typography.titleModerateBoldTextStyle,
        modifier = Modifier.padding(bottom = 12.dp),
    )
}
