package by.varyvoda.android.moneymaster.ui.screen.home

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.toMoneyAmount
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.balance.BalanceService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditDestination
import by.varyvoda.android.moneymaster.ui.screen.operation.edit.OperationEditDestination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
object HomeDestination

class HomeViewModel(
    accountRepository: AccountRepository,
    balanceService: BalanceService,
    operationRepository: OperationRepository,
    private val accountService: AccountService,
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

    val mainCurrency = MutableStateFlow(Currency.USD).asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val totalBalance = accounts
        .filterNotNull()
        .flatMapLatest { list ->
            balanceService.calculateTotalBalance(
                targetCurrencyCode = mainCurrency.value.code,
                accounts = list.map { it.model },
            )
        }
        .stateInThis(0.toMoneyAmount())

    @OptIn(ExperimentalCoroutinesApi::class)
    val operations = uiState
        .map { it.currentAccountId }
        .distinctUntilChanged()
        .flatMapLatest {
            it?.let {
                operationRepository.getAllDetailsByAccountId(it)
            } ?: flowOf(null)
        }
        .catch {
            // TODO Investigate
            println(it)
        }
        .stateInThis(null)

    val currentAccount get() = accounts.value?.find { it.id == uiState.value.currentAccountId }

    fun changeCurrentAccount(accountId: Id) {
        _uiState.update { it.copy(currentAccountId = accountId) }
    }

    fun selectOperation(operationId: UUID) {
        _uiState.update {
            it.copy(
                selectedOperationId =
                if (it.selectedOperationId != operationId)
                    operationId
                else null
            )
        }
    }

    fun onDeleteOperationClick(operationId: UUID){
        viewModelScope.launch {
            accountService.removeOperation(operationId)
        }
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
    val selectedOperationId: UUID? = null,
)