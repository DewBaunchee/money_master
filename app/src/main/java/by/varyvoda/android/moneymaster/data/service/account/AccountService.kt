package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate

interface AccountService {

    suspend fun createAccount(
        name: String, currencyCode: String, initialBalance: Money, themeId: Id
    )

    suspend fun addIncome(
        accountId: Id,
        amount: Money,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    )

    suspend fun addExpense(
        accountId: Id,
        amount: Money,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    )
}