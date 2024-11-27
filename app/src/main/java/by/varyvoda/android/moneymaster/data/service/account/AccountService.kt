package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountMutationCategory
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate

interface AccountService {

    suspend fun addMutation(accountId: Id, mutation: AccountOperation)

    suspend fun createAccount(
        name: String, currencyCode: String, initialBalance: Money, themeId: Id
    )

    suspend fun addIncome(
        accountId: Id,
        amount: Money,
        category: AccountMutationCategory,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    )

    suspend fun addExpense(
        accountId: Id,
        amount: Money,
        category: AccountMutationCategory,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    )
}