package by.varyvoda.android.moneymaster.ui.screen.account

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.ui.component.AccountsPickerForm
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.util.collectValue

@Composable
fun AccountsScreen(viewModel: AccountsViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        MainTopBar(
            titleId = R.string.accounts,
            onBackClick = { viewModel.onBackClick() }
        )
        AccountsScreenBody(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
private fun AccountsScreenBody(viewModel: AccountsViewModel, modifier: Modifier = Modifier) {
    val accounts = viewModel.accounts.collectValue() ?: return
    val (accountSearchString) = viewModel.uiState.collectValue()

    AccountsPickerForm(
        accounts = accounts,
        isSelected = { false },
        onSelect = { viewModel.onAccountSelect(it) },
        searchString = accountSearchString,
        onSearchStringChange = { viewModel.onAccountSearchStringChange(it) },
        onAddAccountClick = { viewModel.onAddAccountClick() },
        onClose = { viewModel.onBackClick() },
        modifier = modifier,
    )
}