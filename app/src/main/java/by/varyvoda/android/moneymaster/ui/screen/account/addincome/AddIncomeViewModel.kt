package by.varyvoda.android.moneymaster.ui.screen.account.addincome

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountMutationCategory
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.util.allNotNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddIncomeViewModel(
    private val accountService: AccountService,
    accountRepository: AccountRepository
) : BaseViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val accounts = accountRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )
    private val _uiState = MutableStateFlow(AddIncomeUiState())
    val uiState = _uiState.asStateFlow()

    var currentAccount: Account? = null

    init {
        combine(accounts, _uiState) { accounts, uiState ->
            currentAccount = accounts.find { it.id == uiState.accountId }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
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

    fun canCreateMutation(): Boolean {
        return uiState.value.let {
            allNotNull(
                it.amount.toLongOrNull(),
                it.category,
                it.date
            )
        }
    }

    fun onCancelClick() {
        navigateUp()
    }

    fun canSave(): Boolean {
        return isAccountSelected() && canCreateMutation()
    }

    fun onSaveClick() {
        viewModelScope.launch {
            accountService.addIncome(
                getAccountId(),
                amount = getAmount(),
                category = getCategory(),
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

    fun getCategory(): AccountMutationCategory {
        return uiState.value.category ?: throw IllegalStateException("Category isn't selected")
    }

    fun getDate(): PrimitiveDate {
        return uiState.value.date ?: throw IllegalStateException("Date isn't selected")
    }

    fun getDescription(): String {
        return uiState.value.description
    }
}

data class AddIncomeUiState(
    val accountId: Id? = null,
    val amount: String = "0",
    val date: PrimitiveDate? = null,
    val category: AccountMutationCategory? = null,
    val description: String = "",
    val images: List<Int> = listOf()
)