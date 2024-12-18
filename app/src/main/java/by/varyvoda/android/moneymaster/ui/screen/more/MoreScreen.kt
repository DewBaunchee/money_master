package by.varyvoda.android.moneymaster.ui.screen.more

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
import by.varyvoda.android.moneymaster.ui.component.IconAndText
import by.varyvoda.android.moneymaster.ui.component.ListPicker
import by.varyvoda.android.moneymaster.ui.component.ListPickerOption
import by.varyvoda.android.moneymaster.ui.component.SquareBox

@Composable
fun MoreScreen(viewModel: MoreViewModel = viewModel()) {
    val options = viewModel.uiState.collectAsState().value.options

    ListPicker(
        items = options,
    ) {
        MoreScreenOption(it)
    }
}

@Composable
fun MoreScreenOption(option: MoreScreenOptionModel, modifier: Modifier = Modifier) {
    ListPickerOption(
        option,
        isSelected = false,
        onClick = { option.onClick() },
        modifier = modifier,
    ) {
        IconAndText(
            icon = {
                SquareBox(
                    background = MaterialTheme.colorScheme.secondary.toBrush(),
                    iconRef = it.iconRef,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            text = { Text(text = stringResource(option.labelId)) },
        )
    }
}