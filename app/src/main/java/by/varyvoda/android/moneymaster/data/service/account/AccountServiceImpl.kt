package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountExpense
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountIncome
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.AccountOperationRepository

class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val accountOperationRepository: AccountOperationRepository
) : AccountService {

    override suspend fun createAccount(
        name: String,
        currencyCode: String,
        initialBalance: Money,
        themeId: Id
    ) {
        accountRepository.insert(
            Account(
                name = name,
                currencyCode = currencyCode,
                initialBalance = initialBalance,
                currentBalance = initialBalance,
                themeId = themeId
            )
        )
    }

    override suspend fun addIncome(
        accountId: Id,
        amount: Money,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    ) {
        accountOperationRepository.insert(
            AccountIncome(
                accountId = accountId,
                amount = amount,
                categoryId = categoryId,
                date = date,
                description = description,
//                images = images
            )
        )
    }

    override suspend fun addExpense(
        accountId: Id,
        amount: Money,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    ) {
        accountOperationRepository.insert(
            AccountExpense(
                accountId = accountId,
                amount = amount,
                categoryId = categoryId,
                date = date,
                description = description,
//                images = images
            )
        )
    }
}