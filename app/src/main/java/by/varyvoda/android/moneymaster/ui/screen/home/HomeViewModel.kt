package by.varyvoda.android.moneymaster.ui.screen.home

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.OperationEditDestination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

class HomeViewModel(
    accountRepository: AccountRepository,
    operationRepository: OperationRepository,
) : BaseViewModel<HomeDestination>() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    val accounts = accountRepository.getAllDetails()
        .stateInThis(null)
        .apply {
            alwaysSelected(
                currentFlow = _uiState.map { it.currentAccountId },
                itemEqualsCurrent = { id == it },
                selector = { changeCurrentAccount(it.id) },
                ifEmpty = { navigateTo(AccountEditDestination()) }
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val operations = uiState
        .map { it.currentAccountId }
        .distinctUntilChanged()
        .flatMapLatest {
            it?.let {
                operationRepository.getAllDetailsByAccountId(it)
            } ?: flowOf(null)
        }
        .stateInThis(null)

    val currentAccount get() = accounts.value?.find { it.id == uiState.value.currentAccountId }

    fun changeCurrentAccount(accountId: Id) {
        this._uiState.update { it.copy(currentAccountId = accountId) }
    }

    fun onAddIncomeClick() {
        navigateTo(
            OperationEditDestination(
                operationType = Operation.Type.INCOME,
                accountId = uiState.value.currentAccountId
            )
        )
    }

    fun onAddExpenseClick() {
        navigateTo(
            OperationEditDestination(
                operationType = Operation.Type.EXPENSE,
                accountId = uiState.value.currentAccountId
            )
        )
    }

    fun onAddTransferClick() {
        navigateTo(
            OperationEditDestination(
                operationType = Operation.Type.TRANSFER,
                accountId = uiState.value.currentAccountId
            )
        )
    }
}

data class HomeUiState(
    val currentAccountId: Id? = null,
)