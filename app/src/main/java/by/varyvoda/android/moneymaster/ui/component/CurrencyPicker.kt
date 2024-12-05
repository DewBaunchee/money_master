package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R
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
            onClick = { onSelect(it) }
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.icon_and_label_row_padding))
        ) {
            Text(text = currency.name)
        }
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
    BottomDialog(onDismissRequest) {
        TitledContent(title = { AppTitle(R.string.currency) }) {
            SearchableContent(
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
    }
}
