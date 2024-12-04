package by.varyvoda.android.moneymaster.ui.screen.account.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.ui.component.AccountSelect
import by.varyvoda.android.moneymaster.ui.component.AppDatePicker
import by.varyvoda.android.moneymaster.ui.component.BalanceField
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination

object AddExpenseDestination : NavigationDestination {
    private const val ACCOUNT_ID_ROUTE_ARG = "accountId"
    override val route = "expense/add?$ACCOUNT_ID_ROUTE_ARG={$ACCOUNT_ID_ROUTE_ARG}"

    fun route(accountId: Id?): String {
        return "expense/add?$ACCOUNT_ID_ROUTE_ARG=$accountId"
    }
}

@Composable
fun AddExpenseScreen(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseViewModel = viewModel()
) {
    val accounts =
        viewModel.accounts.collectAsState().value
    val (accountId, amount, date, category, description, images) =
        viewModel.uiState.collectAsState().value

    val account = viewModel.currentAccount

    FormBox(
        buttons = listOf {
            Row {
                OutlinedButton(onClick = { viewModel.onCancelClick() }) {
                    Text(text = stringResource(R.string.cancel))
                }
                Button(
                    onClick = { viewModel.onSaveClick() },
                    enabled = viewModel.canSave()
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    ) {
        Column(modifier = modifier) {
            Row {
                AccountSelect(
                    accounts = accounts,
                    current = account,
                    onSelect = { viewModel.selectAccount(it.id) },
                    onCreateRequest = { },
                )
                BalanceField(
                    value = amount,
                    onValueChange = { viewModel.changeAmount(it) },
                    labelId = R.string.amount,
                    imeAction = ImeAction.Done
                )
            }
            TextField(
                value = description,
                onValueChange = { viewModel.changeDescription(it) }
            )
            AppDatePicker(
                date = date,
                onDateSelected = { viewModel.changeDate(it) },
                R.string.select_date,
                R.string.transaction_date_format
            )
//        CategoryPicker()
        }
    }
}