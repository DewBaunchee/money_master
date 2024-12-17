package by.varyvoda.android.moneymaster.ui.screen.account.operation.edit

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.DateSuggestion
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditDestination
import by.varyvoda.android.moneymaster.util.allNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class OperationEditDestination(
    val operationId: Id? = null,
    val operationType: Operation.Type? = null,
    val accountId: Id? = null,
)

class OperationEditViewModel(
    private val accountService: AccountService,
    private val categoryRepository: CategoryRepository,
    accountRepository: AccountRepository,
) : BaseViewModel<OperationEditDestination>() {

    private val _uiState = MutableStateFlow(OperationEditUiState())
    val uiState = _uiState.asStateFlow()

    val operationType = uiState
        .map { it.operationType }
        .distinctUntilChanged()

    private val accounts = accountRepository.getAllDetails().stateInThis()

    private val dateSuggestions = MutableStateFlow(DateSuggestion.DEFAULT)

    val incomeViewModel = createIncomeExpenseViewModel(true)
    val expenseViewModel = createIncomeExpenseViewModel(false)

    override fun applyDestination(destination: OperationEditDestination) {
        val (operationId, operationType, accountId) = destination
        if (operationId == null) {
//            accountId?.let { selectAccount(accountId) } TODO
            changeOperationType(operationType ?: Operation.Type.EXPENSE)
        } else {
            // TODO Load operation
        }
    }

    fun changeOperationType(operationType: Operation.Type) {
        _uiState.update { it.copy(operationType = operationType) }
    }

    fun onBackClick() {
        navigateUp()
    }

    private fun onSaveWrapper(logic: suspend () -> Unit) {
        viewModelScope.launch {
            logic()
            navigateUp()
        }
    }

    private fun createIncomeExpenseViewModel(income: Boolean): IncomeExpenseEditViewModel =
        IncomeExpenseEditViewModel(
            income = income,
            accountService = accountService,
            categoryRepository = categoryRepository,
            onSaveClickWrapper = this::onSaveWrapper,
            accounts = accounts,
            dateSuggestions = dateSuggestions,
            addCategoryClick = { navigateTo(CategoryEditDestination()) },
        )
}

data class OperationEditUiState(
    val operationType: Operation.Type? = Operation.Type.DEFAULT,
)

data class IncomeExpenseEditUiState(
    val accountId: Id? = null,
    val amount: String = "",
    val date: PrimitiveDate? = null,
    val categoryId: Id? = null,
    val description: String = "",
    val images: List<Int> = listOf(),
    val categorySearchString: String = "",
)

class IncomeExpenseEditViewModel(
    val income: Boolean,
    private val accountService: AccountService,
    private val categoryRepository: CategoryRepository,
    private val onSaveClickWrapper: (logic: suspend () -> Unit) -> Unit,
    val accounts: StateFlow<List<AccountDetails>>,
    val dateSuggestions: StateFlow<List<DateSuggestion>>,
    private val addCategoryClick: () -> Unit,
) : BaseViewModel<Unit>() {

    private val _uiState = MutableStateFlow(IncomeExpenseEditUiState())
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val categories = _uiState
        .map { it.categorySearchString }
        .distinctUntilChanged()
        .flatMapLatest { categoryRepository.getAll(it) }
        .stateInThis()

    val currentAccount get() = accounts.value.find { it.id == uiState.value.accountId }

    fun selectAccount(accountId: Id) {
        _uiState.update { it.copy(accountId = accountId) }
    }

    fun changeAmount(amount: String) {
        _uiState.update { it.copy(amount = amount) }
    }

    fun changeDate(date: PrimitiveDate?) {
        _uiState.update { it.copy(date = date) }
    }

    fun changeDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun changeCategory(categoryId: Id?) {
        _uiState.update { it.copy(categoryId = categoryId) }
    }

    fun changeCategorySearchString(searchString: String) {
        _uiState.update { it.copy(categorySearchString = searchString) }
    }

    fun onAddCategoryClick() {
        addCategoryClick()
    }

    fun canSave(): Boolean {
        return uiState.value.let {
            allNotNull(
                it.accountId,
                it.amount.toLongOrNull(),
                it.date,
                it.categoryId,
            )
        }
    }

    fun onSaveClick() {
        onSaveClickWrapper {
            if (income) {
                accountService.addIncome(
                    getAccountId(),
                    amount = getAmount(),
                    categoryId = getCategoryId(),
                    date = getDate(),
                    description = getDescription(),
                    images = listOf()
                )
            } else {
                accountService.addExpense(
                    getAccountId(),
                    amount = getAmount(),
                    categoryId = getCategoryId(),
                    date = getDate(),
                    description = getDescription(),
                    images = listOf()
                )
            }
        }
    }

    private fun getAccountId(): Id {
        if (uiState.value.accountId == null)
            throw IllegalStateException("Account isn't selected")

        return uiState.value.accountId!!
    }

    private fun getAmount(): Money {
        return uiState.value.amount.toLongOrNull() ?: throw IllegalStateException("Invalid amount")
    }

    private fun getDate(): PrimitiveDate {
        return uiState.value.date ?: throw IllegalStateException("Date isn't selected")
    }

    private fun getDescription(): String {
        return uiState.value.description
    }

    private fun getCategoryId(): Id {
        return uiState.value.categoryId ?: throw IllegalStateException("Category isn't selected")
    }
}