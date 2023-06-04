package design.andromedacompose.components.inputs

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import design.andromedacompose.AndromedaTheme
import design.andromedacompose.components.Icon
import design.andromedacompose.components.Text
import design.andromedacompose.components.internal.AndromedaPreview
import design.andromedacompose.components.internal.Preview
import design.andromedacompose.foundation.typography.LocalTextStyle
import java.time.LocalDate
import java.util.*

@Composable
fun DatePickerInputField(
    value: String,
    pastDateAllowed: Boolean = false,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit) = {
        Text(text = "Date")
    },
    shouldDisallowFutureDate: Boolean = false,
    onDatePicked: (LocalDate) -> Unit = {},
    defaultBorderNormalColor: Color = Color.Transparent,
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
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            onDatePicked(
                LocalDate.of(
                    selectedYear,
                    selectedMonth + 1, // month the initially selected month (0_11)
                    selectedDayOfMonth
                )
            )
        }, year, month, dayOfMonth
    )
    if (!pastDateAllowed)
        datePicker.datePicker.minDate = calendar.timeInMillis
    if (shouldDisallowFutureDate)
        datePicker.datePicker.maxDate = calendar.timeInMillis
    Column(modifier = modifier) {
        ReadonlyTextField(
            value = value,
            onClick = {
                datePicker.show()
            },
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
