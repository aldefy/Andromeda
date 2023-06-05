package design.andromedacompose.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.DisabledByDefault
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import design.andromedacompose.catalog.Screen
import design.andromedacompose.components.Icon
import design.andromedacompose.components.Text
import design.andromedacompose.components.inputs.DatePickerInputField
import design.andromedacompose.components.inputs.TextField
import design.andromedacompose.foundation.colors.parse
import java.time.LocalDate

@Composable
fun TextFieldScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Text Fields",
        onNavigateUp = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextFieldScreenContent()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun TextFieldScreenContent() {
    val selections = listOf("Random 1", "Random 2", "Some Lorem 3")
    var options = listOf("Random 1", "Random 2", "Some Lorem 3")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    LazyColumn {
        item {
            val text = remember {
                mutableStateOf("John")
            }
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.Close, contentDescription = null) },
                onTrailingIconClick = {
                    text.value = ""
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        item {
            val text = remember {
                mutableStateOf("Doe")
            }
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.Close, contentDescription = null) },
                onTrailingIconClick = {
                    text.value = ""
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        item {
            val date = remember {
                mutableStateOf(LocalDate.now())
            }
            DatePickerInputField(
                value = date.value.toString(),
                onDatePicked = { date.value = it },
                label = { Text("Date picker") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            )
        }
        item {
            val text = remember {
                mutableStateOf("Random text")
            }
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Meta info") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                enabled = false,
                leadingIcon = { Icon(Icons.Default.DisabledByDefault, contentDescription = null) },
                info = {
                    Text("this field is not editable", fontSize = 9.sp)
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        item {
            val text = remember {
                mutableStateOf("")
            }
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Meta info") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.Close, contentDescription = null) },
                onTrailingIconClick = {
                    text.value = ""
                },
                error = {
                    Text("This must be set", fontSize = 9.sp)
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
        }
        item {
            val text = remember {
                mutableStateOf("123.29")
            }
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("MRP *") },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.CurrencyRupee, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.Close, contentDescription = null) },
                onTrailingIconClick = {
                    text.value = ""
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                val text = remember {
                    mutableStateOf("123.29")
                }
                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text("GST *") },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(200.dp),
                    leadingIcon = { Icon(Icons.Default.CurrencyRupee, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.Close, contentDescription = null) },
                    onTrailingIconClick = {
                        text.value = ""
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                val text2 = remember {
                    mutableStateOf("1")
                }
                TextField(
                    value = text2.value,
                    onValueChange = { text2.value = it },
                    label = { Text("No of Units.") },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(200.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Default.AddShoppingCart,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Dropdown test",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    },
                    modifier = Modifier,
                ) {
                    TextField(
                        modifier = Modifier,
                        value = selectedOptionText.text,
                        defaultBorderNormalColor = if (selectedOptionText.text.isEmpty()) Color.Red
                        else Color.parse(
                            "#7F8186"
                        ),
                        onValueChange = { newString ->
                            selectedOptionText = TextFieldValue(
                                text = newString,
                                selection = if (newString.isNotEmpty()) TextRange(newString.length)
                                else TextRange(0)
                            )
                            options = selections.filter {
                                it.contains(newString, ignoreCase = true)
                            }
                            if (newString.isNotEmpty() && !expanded) {
                                expanded = true
                            }
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                }
                            )
                        },
                        singleLine = true,
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionText = TextFieldValue(
                                        selectionOption,
                                        selection = if (selectionOption.isNotEmpty())
                                            TextRange(selectionOption.length)
                                        else
                                            TextRange(0)
                                    )
                                    expanded = false
                                }
                            ) {
                                Text(text = selectionOption)
                            }
                        }
                    }
                }
            }
        }
    }
}
