package by.varyvoda.android.moneymaster.ui.account.creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.currency.Currency
import by.varyvoda.android.moneymaster.ui.AppViewModel
import by.varyvoda.android.moneymaster.ui.component.BalanceField
import by.varyvoda.android.moneymaster.ui.component.SelectDialog
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination

object AccountCreationDestination : NavigationDestination {
    override val route = "account/create"
    override val titleRes = R.string.account_create_title
}

@Composable
fun AccountCreationScreen(
    appViewModel: AppViewModel,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    accountCreationViewModel: AccountCreationViewModel = viewModel()
) {
    val (name, currency, initialBalance) = accountCreationViewModel.uiState.collectAsState().value
    var currencySelectionShown by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.card_padding))
    ) {
        TextField(
            value = name,
            label = { Text(text = stringResource(R.string.account_creation_name_field_label)) },
            onValueChange = { accountCreationViewModel.changeName(it) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        BalanceField(
            value = initialBalance,
            onValueChange = { accountCreationViewModel.changeInitialBalance(it) },
            labelId = R.string.account_creation_balance_field_label,
            imeAction = ImeAction.Done
        )
        Button({
            currencySelectionShown = true
        }) {
            Text(text = currency?.name ?: stringResource(R.string.select_currency))

            if (currencySelectionShown) {
                SelectDialog(
                    items = listOf(Currency.USD, Currency.BYN),
                    current = currency,
                    onSelect = {
                        currencySelectionShown = false
                        accountCreationViewModel.changeCurrency(it)
                    },
                    onDismissRequest = { currencySelectionShown = false },
                    onCreateRequest = {}
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = navigateUp
            ) {
                Text(text = stringResource(R.string.cancel))
            }
            Button(
                onClick = {
                    appViewModel.createAccount(
                        name = name,
                        currency = currency!!,
                        initialBalance = initialBalance.toLongOrNull() ?: 0
                    )
                    navigateUp()
                },
                enabled = name.isNotBlank() && currency != null,
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}