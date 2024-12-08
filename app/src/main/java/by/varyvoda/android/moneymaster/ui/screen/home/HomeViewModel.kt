package by.varyvoda.android.moneymaster.ui.screen.home

import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.EditOperationDestination
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

object HomeDestination

class HomeViewModel(
    accountRepository: AccountRepository
) : BaseViewModel<HomeDestination>() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    val accountDetails = accountRepository.getAllDetails()
        .stateInThis(null)
        .apply {
            alwaysSelected(
                currentFlow = _uiState.map { it.currentAccountId },
                itemEqualsCurrent = { id == it },
                selector = { changeCurrentAccount(it.id) },
                ifEmpty = { navigateTo(AccountEditDestination.route) }
            )
        }

    override fun applyDestination(destination: HomeDestination) {
        TODO("Not yet implemented")
    }


    fun changeCurrentAccount(accountId: Id) {
        this._uiState.update { it.copy(currentAccountId = accountId) }
    }

    fun onAccountCreateClick() {
        navigateTo(AccountEditDestination.route)
    }

    fun onAddIncomeClick() {
        navigateTo(EditOperationDestination(uiState.value.currentAccountId))
    }

    fun onAddExpenseClick() {
        navigateTo(AddExpenseDestination())
    }
}

data class HomeUiState(
    val currentAccountId: Id? = null,
)