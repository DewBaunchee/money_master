package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import kotlinx.coroutines.flow.Flow

data class ExpenseDetails(
    val model: Expense,
    val category: Flow<Category>,
    val accountDetails: Flow<AccountDetails>,
) : OperationDetails {
    override val id get() = model.id
    override val type get() = model.type
}