package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import kotlinx.coroutines.flow.Flow

data class ExpenseDetails(
    override val model: Expense,
    override val category: Flow<Category>,
    override val account: Flow<AccountDetails>,
) : IncomeExpenseDetails {
    override val id get() = model.id
    override val type get() = model.type
    override val date get() = model.date
}