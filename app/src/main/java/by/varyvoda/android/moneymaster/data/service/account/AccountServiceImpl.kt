package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import java.util.UUID

class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val operationRepository: OperationRepository
) : AccountService {

    override suspend fun createAccount(
        name: String,
        currencyCode: String,
        initialBalance: Money,
        iconRef: IconRef,
        theme: ColorTheme,
    ) {
        accountRepository.insert(
            Account(
                name = name,
                currencyCode = currencyCode,
                initialBalance = initialBalance,
                currentBalance = initialBalance,
                iconRef = iconRef,
                theme = theme,
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
        operationRepository.insert(
            Income(
                id = UUID.randomUUID(),
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
        operationRepository.insert(
            Expense(
                id = UUID.randomUUID(),
                accountId = accountId,
                amount = amount,
                categoryId = categoryId,
                date = date,
                description = description,
//                images = images
            )
        )
    }

    override suspend fun addTransfer(
        date: PrimitiveDate,
        sourceAccountId: Id,
        destinationAccountId: Id,
        sentAmount: Money,
        receivedAmount: Money,
        description: String
    ) {
        operationRepository.insert(
            Transfer(
                id = UUID.randomUUID(),
                date = date,
                sourceAccountId = sourceAccountId,
                destinationAccountId = destinationAccountId,
                sentAmount = sentAmount,
                receivedAmount = receivedAmount,
                description = description,
            )
        )
    }
}