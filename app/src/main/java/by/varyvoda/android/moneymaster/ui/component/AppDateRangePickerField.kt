package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.domain.DateRangeSuggestion
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
import by.varyvoda.android.moneymaster.data.model.domain.toDateRangeString
import by.varyvoda.android.moneymaster.data.model.domain.today
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

@Composable
fun AppDateRangePickerField(
    dateRange: PrimitiveDateRange?,
    onDateRangeSelected: (PrimitiveDateRange?) -> Unit,
    @StringRes labelId: Int,
    @StringRes dateFormatId: Int,
    modifier: Modifier = Modifier,
) {
    var showModal by remember { mutableStateOf(false) }

    AppTextField(
        value = dateRange?.toDateRangeString(stringResource(R.string.pretty_date_format)) ?: "",
        onValueChange = { },
        asButton = true,
        label = { Text(stringResource(labelId)) },
        placeholder = { Text(stringResource(dateFormatId)) },
        trailingIcon = {
            AppIcon(IconRef.DatePicker.labeledBy(labelId))
        },
        modifier = modifier
            .pointerInput(dateRange) {
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
        AppDateRangePickerDialog(
            dateRange = dateRange,
            onDateRangeSelected = onDateRangeSelected,
            onDismiss = { showModal = false }
        )
    }
}

@Composable
fun AppDateRangePickerIcon(
    dateRange: PrimitiveDateRange?,
    onDateRangeSelected: (PrimitiveDateRange?) -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    var showModal by remember { mutableStateOf(false) }

    AppIconButton(
        onClick = { showModal = true },
        iconRef = IconRef.DatePicker,
        isSecondary = !isSelected,
        text = null,
        modifier = modifier,
    )

    if (showModal) {
        AppDateRangePickerDialog(
            dateRange = dateRange,
            onDateRangeSelected = onDateRangeSelected,
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateRangePickerDialog(
    dateRange: PrimitiveDateRange?,
    onDateRangeSelected: (PrimitiveDateRange?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = dateRange?.first,
        initialSelectedEndDateMillis = dateRange?.second
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateRangeSelected(
                    Pair(
                        dateRangePickerState.selectedStartDateMillis ?: today(),
                        dateRangePickerState.selectedEndDateMillis ?: today(),
                    )
                )
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
        DateRangePicker(state = dateRangePickerState)
    }
}

@Composable
fun DateRangeSuggestions(
    dateRangeSuggestions: List<DateRangeSuggestion>,
    dateRange: PrimitiveDateRange?,
    onDateRangeSelected: (PrimitiveDateRange?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            for (index in dateRangeSuggestions.indices) {
                val item = dateRangeSuggestions[index]
                val isSelected = item.range == dateRange

                if (index > 0) {
                    VerticalDivider(
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .fillMaxHeight(0.6f),
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary
                        )
                        .clickable {
                            onDateRangeSelected(item.range)
                        }
                ) {
                    Text(
                        text = item.label,
                        color =
                        if (isSelected) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}
