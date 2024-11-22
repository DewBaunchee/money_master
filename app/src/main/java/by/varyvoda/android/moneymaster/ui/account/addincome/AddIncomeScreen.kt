package by.varyvoda.android.moneymaster.ui.account.addincome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import by.varyvoda.android.moneymaster.ui.AppViewModel
import by.varyvoda.android.moneymaster.ui.component.AccountSelect
import by.varyvoda.android.moneymaster.ui.component.AppDatePicker
import by.varyvoda.android.moneymaster.ui.component.BalanceField
import by.varyvoda.android.moneymaster.ui.component.CategoryPicker
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination

object AddIncomeDestination : NavigationDestination {
    override val route = "income/add"
    override val titleRes: Int = R.string.app_name
}

@Composable
fun AddIncomeScreen(
    appViewModel: AppViewModel,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddIncomeViewModel = viewModel()
) {
    val (accounts) =
        appViewModel.uiState.collectAsState().value
    val (accountId, amount, date, category, description, images) =
        viewModel.uiState.collectAsState().value

    val account = accountId?.let { appViewModel.getAccountById(it) }

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
        CategoryPicker()
        Spacer(modifier = Modifier.weight(1f))
        Row {
            OutlinedButton(onClick = {
                navigateUp()
            }) {
                Text(text = stringResource(R.string.cancel))
            }
            Button(
                onClick = {
                    appViewModel.addAccountMutation(
                        viewModel.getAccountId(),
                        viewModel.getAccountMutation()
                    )
                    navigateUp()
                },
                enabled = viewModel.isAccountSelected() && viewModel.canCreateMutation()
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}