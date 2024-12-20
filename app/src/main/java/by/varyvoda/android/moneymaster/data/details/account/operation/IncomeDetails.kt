package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import kotlinx.coroutines.flow.Flow

data class IncomeDetails(
    val model: Income,
    val category: Flow<Category>,
    val accounts: Flow<AccountDetails>,
) : OperationDetails {
    override val id get() = model.id
    override val type get() = model.type
    override val date get() = model.date
}