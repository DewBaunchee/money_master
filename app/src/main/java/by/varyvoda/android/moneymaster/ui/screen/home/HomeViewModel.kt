package by.varyvoda.android.moneymaster.ui.screen.home

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeDestination
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseDestination
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountCreationDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.collections.first

class HomeViewModel(
    accountRepository: AccountRepository
) : BaseViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val accounts = accountRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        combine(accounts, _uiState) { accounts, uiState ->

            if (accounts == null) return@combine

            if (accounts.isEmpty()) {
                navigateTo(AccountCreationDestination.route)
                return@combine
            }

            val currentAccountId: Id? = uiState.currentAccountId
            if (currentAccountId == null || accounts.all { it.id != currentAccountId }) {
                val firstAccount = accounts.first()
                changeCurrentAccount(firstAccount)
            }

        }.launchIn(scope = viewModelScope)
    }

    fun changeCurrentAccount(account: Account) {
        this._uiState.update { it.copy(currentAccountId = account.id, currentAccount = account) }
    }

    fun onAccountCreateClick() {
        navigateTo(AccountCreationDestination.route)
    }

    fun onAddIncomeClick() {
        navigateTo(AddIncomeDestination.route)
    }

    fun onAddExpenseClick() {
        navigateTo(AddExpenseDestination.route)
    }
}

data class HomeUiState(
    val currentAccountId: Id? = null,
    val currentAccount: Account? = null
)