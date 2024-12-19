package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

interface AccountService {

    suspend fun createAccount(
        name: String,
        currencyCode: String,
        initialBalance: MoneyAmount,
        iconRef: IconRef,
        theme: ColorTheme,
    )

    suspend fun addIncome(
        accountId: Id,
        amount: MoneyAmount,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    )

    suspend fun addExpense(
        accountId: Id,
        amount: MoneyAmount,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    )

    suspend fun addTransfer(
        date: PrimitiveDate,
        sourceAccountId: Id,
        destinationAccountId: Id,
        sentAmount: MoneyAmount,
        receivedAmount: MoneyAmount,
        description: String,
    )
}