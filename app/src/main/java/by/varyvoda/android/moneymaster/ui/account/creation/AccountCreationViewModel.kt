package by.varyvoda.android.moneymaster.ui.account.creation

import androidx.lifecycle.ViewModel
import by.varyvoda.android.moneymaster.data.currency.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AccountCreationViewModel : ViewModel() {

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
}