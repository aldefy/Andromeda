package design.andromedacompose.components.internal

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "A.Standard",
    group = "standard",
)
@Preview(
    name = "B.Large font",
    group = "large-font",
    fontScale = 1.6f,
)
@Preview(
    name = "C.Dark mode",
    group = "dark-mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
internal annotation class AndromedaPreview
