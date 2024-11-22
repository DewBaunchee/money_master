package by.varyvoda.android.moneymaster.ui.home

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
import by.varyvoda.android.moneymaster.data.account.Account
import by.varyvoda.android.moneymaster.data.currency.Currency
import by.varyvoda.android.moneymaster.ui.AppViewModel
import by.varyvoda.android.moneymaster.ui.account.addincome.AddIncomeDestination
import by.varyvoda.android.moneymaster.ui.account.creation.AccountCreationDestination
import by.varyvoda.android.moneymaster.ui.component.AccountSelect
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {
    val appUiState = appViewModel.uiState.collectAsState().value
    val homeUiState = homeViewModel.uiState.collectAsState().value

    val accounts: List<Account> = appUiState.accounts
    val currentAccount = homeUiState.currentAccount

    if (currentAccount == null) {
        if (accounts.isEmpty()) {
            appViewModel.createAccount("Default", Currency.USD, 0)
            return
        }
        homeViewModel.changeCurrentAccount(accounts.first())
        return
    }

    Column(modifier = modifier) {
        HomeHeader(
            navigateTo = navigateTo,
            accounts = accounts,
            current = currentAccount,
            onAccountChange = { homeViewModel.changeCurrentAccount(it) },
            onCreateAccountRequest = { navigateTo(AccountCreationDestination.route) }
        )
    }
}

@Composable
fun HomeHeader(
    navigateTo: (String) -> Unit,
    accounts: List<Account>,
    current: Account,
    onAccountChange: (Account) -> Unit,
    onCreateAccountRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            AccountSelect(
                accounts = accounts,
                current = current,
                onSelect = onAccountChange,
                onCreateRequest = onCreateAccountRequest
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
            OutlinedButton(onClick = {
                navigateTo(AddIncomeDestination.route)
            }) {
                Text(text = stringResource(R.string.add_income))
            }
            OutlinedButton(onClick = {

            }) {
                Text(text = stringResource(R.string.add_outcome))
            }
            OutlinedButton(onClick = {

            }) {
                Text(text = stringResource(R.string.add_transfer))
            }
        }
    }
}

