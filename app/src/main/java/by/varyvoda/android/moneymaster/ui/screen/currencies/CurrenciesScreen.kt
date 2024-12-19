package by.varyvoda.android.moneymaster.ui.screen.currencies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.ui.component.BalanceField
import by.varyvoda.android.moneymaster.ui.component.CurrencySelect
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.SaveButton
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun CurrenciesScreen(viewModel: CurrenciesViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        MainTopBar(
            titleId = R.string.currencies,
            onBackClick = { viewModel.onBackClick() }
        )
        CurrenciesScreenBody(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
fun CurrenciesScreenBody(viewModel: CurrenciesViewModel, modifier: Modifier = Modifier) {
    val (_, _, firstToSecondRate, secondToFirstRate) =
        viewModel.uiState.collectAsState().value
    val currencies = viewModel.currencies.collectAsState().value ?: return

    val firstCurrency = viewModel.firstCurrency
    val secondCurrency = viewModel.secondCurrency

    FormBox(
        buttons = listOf {
            SaveButton(viewModel = viewModel)
        },
        modifier = modifier,
    ) { buttonColumnHeightDp ->
        Column(
            verticalArrangement = Arrangement.formSpacedBy(),
            modifier = modifier
                .formPadding()
        ) {
            Row(
                horizontalArrangement = Arrangement.formSpacedBy(),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                CurrencySelect(
                    currencies = currencies,
                    selectedCurrency = firstCurrency,
                    onSelect = { viewModel.selectFirstCurrency(it.code) },
                    modifier = Modifier.weight(1f),
                )
                CurrencySelect(
                    currencies = currencies,
                    selectedCurrency = secondCurrency,
                    onSelect = { viewModel.selectSecondCurrency(it.code) },
                    modifier = Modifier.weight(1f),
                )
            }
            BalanceField(
                value = firstToSecondRate,
                onValueChange = { viewModel.changeFirstToSecondRate(it) },
                labelId = R.string.first_to_second_rate,
                imeAction = ImeAction.Next,
                modifier = Modifier
                    .fillMaxWidth()
            )
            BalanceField(
                value = secondToFirstRate,
                onValueChange = { viewModel.changeSecondToFirstRate(it) },
                labelId = R.string.second_to_first_rate,
                imeAction = ImeAction.Done,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Spacer(Modifier.height(buttonColumnHeightDp))
    }
}