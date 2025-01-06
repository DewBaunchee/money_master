package by.varyvoda.android.moneymaster.data.model.account.operation

import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount

interface IncomeExpense : SingleAccountOperation {
    val amount: MoneyAmount
    val categoryId: Id
    val description: String
}