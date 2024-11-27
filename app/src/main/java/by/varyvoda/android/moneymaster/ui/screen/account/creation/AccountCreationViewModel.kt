package by.varyvoda.android.moneymaster.ui.screen.account.creation

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountCreationViewModel(
    private val accountService: AccountService
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AccountCreationUiState())
    val uiState = _uiState.asStateFlow()

    fun changeName(name: String) {
        this._uiState.update { it.copy(name = name) }
    }

    fun changeCurrency(currency: Currency) {
        this._uiState.update { it.copy(currency = currency) }
    }

    fun changeInitialBalance(initialBalance: String) {
        this._uiState.update { it.copy(initialBalance = initialBalance) }
    }

    fun onSelectTheme(theme: AccountTheme) {
        this._uiState.update { it.copy(theme = theme) }
    }

    fun canSave(): Boolean {
        return with(uiState.value) {
            name.isNotBlank() && currency != null
        }
    }

    fun onSaveClick() {
        if (!canSave())
            throw IllegalStateException("Cannot create account")

        uiState.value.let { (name, currency, initialBalance) ->
            viewModelScope.launch {
                accountService.createAccount(
                    name = name,
                    currencyCode = currency!!.code,
                    initialBalance = initialBalance.toLong(),
                    themeId = AccountTheme.Gray.id
                )
            }
        }
        navigateUp()
    }

    fun onBackClick() {
        navigateUp()
    }
}

data class AccountCreationUiState(
    val name: String = "",
    val currency: Currency? = null,
    val initialBalance: String = "",
    val theme: AccountTheme? = AccountTheme.Gray
)