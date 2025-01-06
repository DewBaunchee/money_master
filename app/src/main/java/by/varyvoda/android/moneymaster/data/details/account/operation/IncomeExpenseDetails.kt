package by.varyvoda.android.moneymaster.data.details.account.operation

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.IncomeExpense
import kotlinx.coroutines.flow.Flow

interface IncomeExpenseDetails : OperationDetails {
    val model: IncomeExpense
    val category: Flow<Category>
    val account: Flow<AccountDetails>
}