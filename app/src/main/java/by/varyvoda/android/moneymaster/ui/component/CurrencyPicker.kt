package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.data.model.currency.Currency

@Composable
fun CurrencyListPicker(
    currencies: List<Currency>,
    isSelected: (Currency) -> Boolean,
    onSelect: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPicker(
        items = currencies,
        modifier = modifier,
    ) {
        CurrencyOptionButton(
            currency = it,
            isSelected = isSelected(it),
            onClick = { onSelect(it) },
        )
    }
}

@Composable
fun CurrencyOptionButton(
    currency: Currency,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPickerOption(
        item = currency,
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
    ) {
        IconAndText(
            icon = {},
            text = { Text(text = "${currency.name} (${currency.symbol})") }
        )
    }
}

@Composable
fun CurrencyListPickerDialog(
    currencies: List<Currency>,
    isSelected: (Currency) -> Boolean,
    onSelect: (Currency) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    searchEnabled: Boolean = false,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
) {
    TitledSearchableBottomDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        searchEnabled = searchEnabled,
        searchString = searchString,
        onSearchStringChange = onSearchStringChange,
    ) {
        CurrencyListPicker(
            currencies = currencies,
            isSelected = isSelected,
            onSelect = onSelect,
        )
    }
}
