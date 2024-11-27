package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountIncome
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountMutationCategory
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountExpense
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.mutation.AccountMutationRepository

class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val accountMutationRepository: AccountMutationRepository
) : AccountService {

    override suspend fun addMutation(
        accountId: Id,
        mutation: AccountOperation
    ) {
//        accountMutationRepository.insert()
    }

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
        category: AccountMutationCategory,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    ) {
        accountMutationRepository.insert(
            AccountIncome(
                accountId = accountId,
                amount = amount,
                categoryId = category.id,
                date = date,
                description = description,
//                images = images
            )
        )
    }

    override suspend fun addExpense(
        accountId: Id,
        amount: Money,
        category: AccountMutationCategory,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    ) {
        accountMutationRepository.insert(
            AccountExpense(
                accountId = accountId,
                amount = amount,
                categoryId = category.id,
                date = date,
                description = description,
//                images = images
            )
        )
    }
}