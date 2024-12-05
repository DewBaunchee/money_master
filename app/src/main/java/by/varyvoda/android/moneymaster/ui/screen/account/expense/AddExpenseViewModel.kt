package by.varyvoda.android.moneymaster.ui.screen.account.expense

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.util.allNotNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    private val accountService: AccountService,
    accountRepository: AccountRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState = _uiState.asStateFlow()

    val accounts = accountRepository.getAll().stateInThis()

    var currentAccount: Account? = null

    init {
        combine(accounts, _uiState) { accounts, uiState ->
            currentAccount = accounts.find { it.id == uiState.accountId }
        }
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

    fun isAccountSelected(): Boolean {
        return uiState.value.accountId != null
    }

    fun canCreateOperation(): Boolean {
        return uiState.value.let {
            allNotNull(
                it.amount.toLongOrNull(),
                it.categoryId,
                it.date
            )
        }
    }

    fun onCancelClick() {
        navigateUp()
    }

    fun canSave(): Boolean {
        return isAccountSelected() && canCreateOperation()
    }

    fun onSaveClick() {
        viewModelScope.launch {
            accountService.addExpense(
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

    fun getAccountId(): Id {
        if (uiState.value.accountId == null)
            throw IllegalStateException("Account isn't selected")

        return uiState.value.accountId!!
    }

    fun getAmount(): Money {
        return uiState.value.amount.toLongOrNull() ?: throw IllegalStateException("Invalid amount")
    }

    fun getCategoryId(): Id {
        return uiState.value.categoryId ?: throw IllegalStateException("Category isn't selected")
    }

    fun getDate(): PrimitiveDate {
        return uiState.value.date ?: throw IllegalStateException("Date isn't selected")
    }

    fun getDescription(): String {
        return uiState.value.description
    }
}

data class AddExpenseUiState(
    val accountId: Id? = null,
    val amount: String = "0",
    val date: PrimitiveDate? = null,
    val categoryId: Id? = null,
    val description: String = "",
    val images: List<Int> = listOf()
)