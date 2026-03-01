package design.andromedacompose.foundation.accessibility

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

/**
 * Enforces the minimum 48dp × 48dp touch target size recommended by
 * WCAG 2.5.8 and the European Accessibility Act (EAA 2025).
 */
public fun Modifier.minimumInteractiveSize(): Modifier = this.sizeIn(minWidth = 48.dp, minHeight = 48.dp)

/**
 * Adds toggleable semantics (role + state description) for
 * Checkbox, Switch, and RadioButton components.
 *
 * @param checked Whether the control is currently checked/selected.
 * @param role The semantic role (e.g., [Role.Checkbox], [Role.Switch], [Role.RadioButton]).
 * @param checkedLabel Label announced when checked. Defaults to "Checked".
 * @param uncheckedLabel Label announced when unchecked. Defaults to "Not checked".
 */
public fun Modifier.toggleableSemantics(
    checked: Boolean,
    role: Role,
    checkedLabel: String = "Checked",
    uncheckedLabel: String = "Not checked",
): Modifier =
    semantics {
        this.role = role
        stateDescription = if (checked) checkedLabel else uncheckedLabel
    }

/**
 * Adds semantic state description for indeterminate checkbox state.
 */
public fun Modifier.indeterminateSemantics(role: Role = Role.Checkbox): Modifier =
    semantics {
        this.role = role
        stateDescription = "Indeterminate"
    }
