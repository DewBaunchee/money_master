package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.currency.Currency

@Composable
fun CurrencySelect(
    currencies: List<Currency>,
    selectedCurrency: Currency?,
    onSelect: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currencySelectionShown by remember { mutableStateOf(false) }
    AppTextField(
        value = selectedCurrency?.name ?: "",
        onValueChange = {},
        asButton = true,
        label = {
            Text(
                text = stringResource(
                    if (selectedCurrency == null)
                        R.string.select_currency
                    else
                        R.string.currency
                )
            )
        },
        modifier = modifier
            .clickable(onClick = { currencySelectionShown = true }),
    )
    if (currencySelectionShown) {
        CurrencyListPickerDialog(
            currencies = currencies,
            isSelected = { it.code == selectedCurrency?.code },
            onSelect = {
                currencySelectionShown = false
                onSelect(it)
            },
            onDismissRequest = { currencySelectionShown = false },
        )
    }
}