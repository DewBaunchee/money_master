package by.varyvoda.android.moneymaster.ui.home

import androidx.lifecycle.ViewModel
import by.varyvoda.android.moneymaster.data.account.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun changeCurrentAccount(account: Account) {
        this._uiState.update { it.copy(currentAccount = account) }
    }
}