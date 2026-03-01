package design.andromedacompose.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import design.andromedacompose.components.selection.LabeledCheckbox
import design.andromedacompose.components.selection.LabeledRadioButton
import design.andromedacompose.components.selection.LabeledSwitch
import design.andromedacompose.components.selection.RadioGroup
import design.andromedacompose.components.selection.Switch
import design.andromedacompose.components.selection.ToggleableState
import design.andromedacompose.components.selection.TriStateCheckbox

@Composable
fun SelectionControlsScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Selection Controls",
        onNavigateUp = onUpClick,
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            // --- Checkbox ---
            SectionHeader("Checkbox")

            var check1 by remember { mutableStateOf(false) }
            var check2 by remember { mutableStateOf(true) }
            var check3 by remember { mutableStateOf(false) }

            LabeledCheckbox(checked = check1, onCheckedChange = { check1 = it }) {
                Text("Accept terms and conditions")
            }
            LabeledCheckbox(checked = check2, onCheckedChange = { check2 = it }) {
                Text("Receive notifications")
            }
            LabeledCheckbox(checked = check3, onCheckedChange = { check3 = it }, enabled = false) {
                Text("Disabled checkbox")
            }

            Spacer(Modifier.height(12.dp))

            // Tri-state
            Text(
                "Tri-State",
                fontSize = 12.sp,
                color = AndromedaTheme.colors.contentColors.subtle,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                val states = listOf(ToggleableState.Unchecked, ToggleableState.Checked, ToggleableState.Indeterminate)
                var stateIndex by remember { mutableIntStateOf(0) }
                TriStateCheckbox(
                    state = states[stateIndex],
                    onClick = { stateIndex = (stateIndex + 1) % states.size },
                )
                Spacer(Modifier.padding(start = 12.dp))
                Text("Tap to cycle: ${states[stateIndex].name}")
            }

            Spacer(Modifier.height(24.dp))

            // --- RadioButton ---
            SectionHeader("RadioButton")

            var selectedOption by remember { mutableIntStateOf(0) }
            val options = listOf("Option A", "Option B", "Option C")

            RadioGroup {
                options.forEachIndexed { index, label ->
                    LabeledRadioButton(
                        selected = selectedOption == index,
                        onClick = { selectedOption = index },
                    ) {
                        Text(label)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                "Selected: ${options[selectedOption]}",
                fontSize = 12.sp,
                color = AndromedaTheme.colors.contentColors.subtle,
            )

            Spacer(Modifier.height(24.dp))

            // --- Switch ---
            SectionHeader("Switch")

            var switch1 by remember { mutableStateOf(false) }
            var switch2 by remember { mutableStateOf(true) }
            var switch3 by remember { mutableStateOf(false) }

            LabeledSwitch(checked = switch1, onCheckedChange = { switch1 = it }) {
                Text("Dark Mode")
            }
            LabeledSwitch(checked = switch2, onCheckedChange = { switch2 = it }) {
                Text("Notifications")
            }
            LabeledSwitch(checked = switch3, onCheckedChange = { switch3 = it }, enabled = false) {
                Text("Disabled switch")
            }

            Spacer(Modifier.height(12.dp))

            // Standalone switches
            Text(
                "Standalone",
                fontSize = 12.sp,
                color = AndromedaTheme.colors.contentColors.subtle,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                var standalone by remember { mutableStateOf(false) }
                Switch(checked = standalone, onCheckedChange = { standalone = it })
                Spacer(Modifier.padding(start = 12.dp))
                Text(if (standalone) "On" else "Off")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = AndromedaTheme.typography.titleModerateBoldTextStyle,
        modifier = Modifier.padding(bottom = 12.dp),
    )
}
