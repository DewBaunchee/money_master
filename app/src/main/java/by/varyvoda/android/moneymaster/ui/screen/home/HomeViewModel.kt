package by.varyvoda.android.moneymaster.ui.screen.home

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeDestination
import by.varyvoda.android.moneymaster.ui.screen.account.category.AccountOperationCategoryDestination
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseDestination
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

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    val accountDetails = accountRepository.getAllDetails().stateInThis(null)

    init {
        combine(accountDetails, _uiState) { accounts, uiState ->

            if (accounts == null) return@combine

            if (accounts.isEmpty()) {
                navigateTo(AccountEditDestination.route)
                return@combine
            }

            val currentAccountId: Id? = uiState.currentAccountId
            if (currentAccountId == null || accounts.all { it.id != currentAccountId }) {
                val firstAccount = accounts.first()
                changeCurrentAccount(firstAccount.id)
            }

        }.launchIn(scope = viewModelScope)
    }

    fun changeCurrentAccount(accountId: Id) {
        this._uiState.update { it.copy(currentAccountId = accountId) }
    }

    fun onAccountCreateClick() {
        navigateTo(AccountEditDestination.route)
    }

    fun onAddIncomeClick() {
        navigateTo(AddIncomeDestination.route(uiState.value.currentAccountId))
    }

    fun onAddExpenseClick() {
        navigateTo(AddExpenseDestination.route)
    }
}

data class HomeUiState(
    val currentAccountId: Id? = null,
)