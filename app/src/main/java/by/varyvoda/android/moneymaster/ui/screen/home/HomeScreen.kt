package by.varyvoda.android.moneymaster.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.ui.component.AccountSelect
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination
import by.varyvoda.android.moneymaster.ui.util.anyNull

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val accounts = viewModel.accounts.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value
    val currentAccount = uiState.currentAccount

    if (anyNull(accounts, currentAccount)) return

    Column(modifier = modifier) {

        HomeHeader(
            accounts = accounts,
            current = currentAccount,
            onAccountChange = { viewModel.changeCurrentAccount(it) },
            onAccountCreateClick = { viewModel.onAccountCreateClick() },
            onAddIncomeClick = { viewModel.onAddIncomeClick() },
            onAddExpenseClick = { viewModel.onAddExpenseClick() },
        )
    }
}

@Composable
fun HomeHeader(
    accounts: List<Account>,
    current: Account,
    onAccountChange: (Account) -> Unit,
    onAccountCreateClick: () -> Unit,
    onAddIncomeClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            AccountSelect(
                accounts = accounts,
                current = current,
                onSelect = onAccountChange,
                onCreateRequest = onAccountCreateClick
            )
            OutlinedButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.edit_balance)
                )
            }
        }
        Row {
            OutlinedButton(onClick = onAddIncomeClick) {
                Text(text = stringResource(R.string.add_income))
            }
            OutlinedButton(onClick = onAddExpenseClick) {
                Text(text = stringResource(R.string.add_expense))
            }
            OutlinedButton(onClick = {

            }) {
                Text(text = stringResource(R.string.add_transfer))
            }
        }
    }
}

