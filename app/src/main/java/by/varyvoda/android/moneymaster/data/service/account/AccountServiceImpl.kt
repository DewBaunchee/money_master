package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.operation.SingleAccountOperation
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import by.varyvoda.android.moneymaster.data.service.balance.BalanceService
import by.varyvoda.android.moneymaster.util.notNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import java.util.UUID

class AccountServiceImpl(
    private val accountRepository: AccountRepository,
    private val operationRepository: OperationRepository,
    private val balanceService: BalanceService,
) : AccountService {

    override suspend fun createOrUpdateAccount(
        id: Id,
        name: String,
        currencyCode: String,
        initialBalance: MoneyAmount,
        iconRef: IconRef,
        theme: ColorTheme,
    ) {
        accountRepository.upsert(
            Account(
                id = id,
                name = name,
                currencyCode = currencyCode,
                initialBalance = initialBalance,
                currentBalance = initialBalance,
                iconRef = iconRef,
                theme = theme,
            )
        )
    }

    override suspend fun deleteAccount(accountId: Id) {
        accountRepository.delete(id = accountId)
    }

    override suspend fun getAccount(accountId: Id) =
        accountRepository.getDetailsById(accountId).notNull()

    override suspend fun addIncome(
        accountId: Id,
        amount: MoneyAmount,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    ) {
        Income(
            id = UUID.randomUUID(),
            accountId = accountId,
            amount = amount,
            categoryId = categoryId,
            date = date,
            description = description,
//                images = images
        ).let {
            operationRepository.insert(it)
            calculateAccountBalanceAndUpdate(accountId, it, addOperation = true)
        }
    }

    override suspend fun addExpense(
        accountId: Id,
        amount: MoneyAmount,
        categoryId: Id,
        date: PrimitiveDate,
        description: String,
        images: List<Int>
    ) {
        Expense(
            id = UUID.randomUUID(),
            accountId = accountId,
            amount = amount,
            categoryId = categoryId,
            date = date,
            description = description,
//                images = images
        ).let {
            operationRepository.insert(it)
            calculateAccountBalanceAndUpdate(accountId, it, addOperation = true)
        }
    }

    override suspend fun addTransfer(
        date: PrimitiveDate,
        sourceAccountId: Id,
        destinationAccountId: Id,
        sentAmount: MoneyAmount,
        receivedAmount: MoneyAmount,
        description: String
    ) {
        Transfer(
            id = UUID.randomUUID(),
            date = date,
            sourceAccountId = sourceAccountId,
            destinationAccountId = destinationAccountId,
            sentAmount = sentAmount,
            receivedAmount = receivedAmount,
            description = description,
        ).let {
            operationRepository.insert(it)
            calculateAccountBalanceAndUpdate(sourceAccountId, it, addOperation = true)
            calculateAccountBalanceAndUpdate(destinationAccountId, it, addOperation = true)
        }
    }

    override suspend fun removeOperation(operationId: UUID) {
        val operation = operationRepository.getById(operationId).first()!!
        when (operation) {
            is SingleAccountOperation ->
                calculateAccountBalanceAndUpdate(
                    operation.accountId,
                    operation,
                    addOperation = false,
                )

            is Transfer ->
                operation.apply {
                    calculateAccountBalanceAndUpdate(
                        sourceAccountId,
                        operation,
                        addOperation = false,
                    )
                    calculateAccountBalanceAndUpdate(
                        destinationAccountId,
                        operation,
                        addOperation = false,
                    )
                }
        }
        operationRepository.delete(operation)
    }

    private suspend fun calculateAccountBalanceAndUpdate(
        accountId: Id,
        operation: Operation,
        addOperation: Boolean,
    ) = updateBalance(
        accountId = accountId,
        calculateAccountBalance(
            accountId = accountId,
            operation = operation,
            addOperation = addOperation
        )
    )

    private suspend fun calculateAccountBalance(
        accountId: Id,
        operation: Operation,
        addOperation: Boolean,
    ): MoneyAmount =
        accountRepository.getById(accountId)
            .notNull()
            .flatMapLatest {
                balanceService.calculateAccountBalance(
                    account = it,
                    operation = operation,
                    addOperation = addOperation
                )
            }
            .first()

    private suspend fun updateBalance(accountId: Id, balance: MoneyAmount) {
        accountRepository.updateBalance(accountId = accountId, balance = balance)
    }
}