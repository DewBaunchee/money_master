package by.varyvoda.android.moneymaster.ui.screen.account.addincome

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.domain.DateSuggestion
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.AccountOperationCategoryRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.category.AccountOperationCategoryEditDestination
import by.varyvoda.android.moneymaster.util.allNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddIncomeViewModel(
    private val accountService: AccountService,
    private val categoryRepository: AccountOperationCategoryRepository,
    accountRepository: AccountRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AddIncomeUiState())
    val uiState = _uiState.asStateFlow()

    val accounts = accountRepository.getAllDetails().stateInThis()

    val dateSuggestions = MutableStateFlow(DateSuggestion.DEFAULT)

    @OptIn(ExperimentalCoroutinesApi::class)
    val categories = _uiState
        .map { it.categorySearchString }
        .distinctUntilChanged()
        .flatMapLatest { categoryRepository.getAll(it) }
        .stateInThis()

    var currentAccount: AccountDetails? = null

    init {
        combine(accounts, _uiState) { accounts, uiState ->
            currentAccount = accounts.find { it.id == uiState.accountId }
        }.launchIn(viewModelScope)
    }

    fun applyNavigationArguments(accountId: Id?) {
        if (accountId == null) return
        selectAccount(accountId)
    }

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
        navigateTo(AccountOperationCategoryEditDestination.route)
    }

    fun isAccountSelected(): Boolean {
        return uiState.value.accountId != null
    }

    fun canCreateOperation(): Boolean {
        return uiState.value.let {
            allNotNull(
                it.amount.toLongOrNull(),
                it.date,
                it.categoryId,
            )
        }
    }

    fun onBackClick() {
        navigateUp()
    }

    fun canSave(): Boolean {
        return isAccountSelected() && canCreateOperation()
    }

    fun onSaveClick() {
        viewModelScope.launch {
            accountService.addIncome(
                getAccountId(),
                amount = getAmount(),
                categoryId = getCategoryId(),
                date = getDate(),
                description = getDescription(),
                images = listOf()
            )
            navigateUp()
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

data class AddIncomeUiState(
    val accountId: Id? = null,
    val amount: String = "",
    val date: PrimitiveDate? = null,
    val categoryId: Id? = null,
    val description: String = "",
    val images: List<Int> = listOf(),
    val categorySearchString: String = "",
)