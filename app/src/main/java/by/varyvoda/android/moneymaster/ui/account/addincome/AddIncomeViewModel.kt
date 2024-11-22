package by.varyvoda.android.moneymaster.ui.account.addincome

import androidx.lifecycle.ViewModel
import by.varyvoda.android.moneymaster.data.account.AccountIncome
import by.varyvoda.android.moneymaster.data.domain.Id
import by.varyvoda.android.moneymaster.data.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.ui.util.allNotNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddIncomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AddIncomeUiState())
    val uiState = _uiState.asStateFlow()

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

    fun getAccountId(): Id {
        if (uiState.value.accountId == null)
            throw IllegalStateException("Account isn't selected")

        return uiState.value.accountId!!
    }

    fun getAccountMutation(): AccountIncome {
        if (!canCreateMutation())
            throw IllegalStateException("Can't create mutation")

        return uiState.value.let {
            AccountIncome(
                income = it.amount.toLongOrNull()!!,
                category = it.category!!,
                date = it.date!!,
                description = it.description,
                images = it.images
            )
        }
    }
}