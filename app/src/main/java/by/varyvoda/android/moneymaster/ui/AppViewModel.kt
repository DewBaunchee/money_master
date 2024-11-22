package by.varyvoda.android.moneymaster.ui

import androidx.lifecycle.ViewModel
import by.varyvoda.android.moneymaster.data.account.Account
import by.varyvoda.android.moneymaster.data.account.AccountMutation
import by.varyvoda.android.moneymaster.data.currency.Currency
import by.varyvoda.android.moneymaster.data.domain.Id
import by.varyvoda.android.moneymaster.data.domain.Money
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {

    private val _appUiState = MutableStateFlow(AppUiState())
    val uiState = _appUiState.asStateFlow()

    fun createAccount(
        name: String,
        currency: Currency,
        initialBalance: Money
    ) {
        val newAccount = Account(
            name = name,
            currency = currency,
            initialBalance = initialBalance,
        )
        _appUiState.update {
            it.copy(
                accounts = it.accounts.plus(newAccount)
            )
        }
    }

    fun addAccountMutation(accountId: Id, mutation: AccountMutation) {
        this._appUiState.update { state ->
            state.copy(accounts = state.accounts.map { account ->
                if (account.id != accountId) {
                    return@map account
                }
                return@map account.copy(mutations = account.mutations.plus(mutation))
            })
        }
    }

    fun getAccountById(accountId: Id): Account? {
        return uiState.value.accounts.find { it.id == accountId }
    }
}