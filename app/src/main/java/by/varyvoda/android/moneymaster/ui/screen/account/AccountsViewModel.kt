package by.varyvoda.android.moneymaster.ui.screen.account

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

@Serializable
object AccountsDestination

class AccountsViewModel(
    accountRepository: AccountRepository,
) : BaseViewModel<AccountsDestination>() {

    private val _uiState = MutableStateFlow(AccountsUiState())
    val uiState = _uiState.asStateFlow()

    val accounts = accountRepository.getAllDetails().stateInThis(null)

    fun onBackClick() {
        navigateUp()
    }

    fun onAccountSelect(it: AccountDetails) {
        navigateTo(AccountEditDestination(accountId = it.id))
    }

    fun onAddAccountClick() {
        navigateTo(AccountEditDestination())
    }

    fun onAccountSearchStringChange(accountSearchString: String) {
        _uiState.update { it.copy(accountSearchString = accountSearchString) }
    }
}

data class AccountsUiState(
    val accountSearchString: String = "",
)