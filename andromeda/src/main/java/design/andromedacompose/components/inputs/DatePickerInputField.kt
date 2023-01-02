package design.andromedacompose.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.Icon
import design.andromedacompose.components.Text
import design.andromedacompose.components.internal.AndromedaPreview
import design.andromedacompose.components.internal.Preview
import design.andromedacompose.foundation.typography.LocalTextStyle
import java.time.LocalDate

@Composable
fun DatePickerInputField(
    value: String,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit) = {
        Text(text = "Date")
    },
    onDatePicked: (LocalDate) -> Unit = {},
    error: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = {
        Icon(Icons.Default.DateRange, contentDescription = null)
    },
    onLeadingIconClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
) {
    val dialogState = rememberMaterialDialogState()
    val textStyle = LocalTextStyle.current
    val mergedTextStyle =
        textStyle.copy(color = AndromedaTheme.colors.primaryColors.active)

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok", mergedTextStyle)
            negativeButton("Cancel", mergedTextStyle)
        }
    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = AndromedaTheme.colors.primaryColors.active,
                dateActiveBackgroundColor = AndromedaTheme.colors.primaryColors.active,
            )
        ) { date ->
            onDatePicked(date)
        }
    }
    Column(modifier = modifier) {
        ReadonlyTextField(
            value = value,
            onClick = {
                dialogState.show()
            },
            label = label,
            error = error,
            info = info,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            onLeadingIconClick = onLeadingIconClick,
            trailingIcon = trailingIcon,
            onTrailingIconClick = onTrailingIconClick
        )
    }
}

@AndromedaPreview
@Composable
internal fun DatePickerInputFieldPreview() {
    Preview {
        DatePickerInputField(
            value = "11 Dec 2023",
            label = { Text("Expiry") },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            trailingIcon = { Icon(Icons.Default.Close, contentDescription = null) },
        )

        DatePickerInputField(
            value = "11 Dec 2023",
            label = { Text("Expiry") },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            trailingIcon = { Icon(Icons.Default.Close, contentDescription = null) },
            info = {
                Text("Expiry must be set", fontSize = 9.sp)
            }
        )
    }
}
