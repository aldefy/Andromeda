package design.andromedacompose.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

/**
 * Useful to get input field with date picker / expiry etc
 */
@Composable
fun ReadonlyTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
    defaultBorderNormalColor: Color = Color.Transparent,
    label: @Composable () -> Unit = {},
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
) {
    Box {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label,
            error = error,
            info = info,
            placeholder = placeholder,
            defaultBorderNormalColor = defaultBorderNormalColor,
            leadingIcon = leadingIcon,
            onLeadingIconClick = onLeadingIconClick,
            trailingIcon = trailingIcon,
            onTrailingIconClick = onTrailingIconClick
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}
