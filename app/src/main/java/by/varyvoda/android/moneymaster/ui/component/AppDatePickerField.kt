package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.domain.DateSuggestion
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.model.domain.toDateString
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

@Composable
fun AppDatePickerField(
    date: PrimitiveDate?,
    onDateSelected: (PrimitiveDate?) -> Unit,
    @StringRes labelId: Int,
    @StringRes dateFormatId: Int,
    modifier: Modifier = Modifier,
) {
    var showModal by remember { mutableStateOf(false) }

    AppTextField(
        value = date?.toDateString(stringResource(R.string.pretty_date_format)) ?: "",
        onValueChange = { },
        asButton = true,
        label = { Text(stringResource(labelId)) },
        placeholder = { Text(stringResource(dateFormatId)) },
        trailingIcon = {
            AppIcon(IconRef.DatePicker.labeledBy(labelId))
        },
        modifier = modifier
            .pointerInput(date) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        AppDatePickerDialog(
            date = date,
            onDateSelected = onDateSelected,
            onDismiss = { showModal = false }
        )
    }
}

@Composable
fun DateSuggestions(
    dateSuggestions: List<DateSuggestion>,
    date: PrimitiveDate?,
    onDateSelected: (PrimitiveDate?) -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonSelector(
        options = dateSuggestions,
        isSelected = { it.date == date },
        onSelect = { onDateSelected(it.date) },
        modifier = modifier,
    ) {
        Text(text = it.label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    date: PrimitiveDate?,
    onDateSelected: (PrimitiveDate?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        modifier = modifier
    ) {
        DatePicker(state = datePickerState)
    }
}
