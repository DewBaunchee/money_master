package by.varyvoda.android.moneymaster.data.service.account

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.BalanceEdit
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
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

    override suspend fun createAccount(
        name: String,
        currencyCode: String,
        initialBalance: MoneyAmount,
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
            calculateAccountBalanceAndUpdate(accountId, it, calculateNextBalance = true)
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
            calculateAccountBalanceAndUpdate(accountId, it, calculateNextBalance = true)
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
            calculateAccountBalanceAndUpdate(sourceAccountId, it, calculateNextBalance = true)
            calculateAccountBalanceAndUpdate(destinationAccountId, it, calculateNextBalance = true)
        }
    }

    override suspend fun removeOperation(operationId: UUID) {
        val operation = operationRepository.getById(operationId).first()!!
        when (operation.type) {
            Operation.Type.INCOME, Operation.Type.EXPENSE, Operation.Type.BALANCE_EDIT ->
                calculateAccountBalanceAndUpdate(
                    getAccountId(operation),
                    operation,
                    calculateNextBalance = false,
                )

            Operation.Type.TRANSFER ->
                (operation as Transfer).apply {
                    calculateAccountBalanceAndUpdate(
                        sourceAccountId,
                        operation,
                        calculateNextBalance = false,
                    )
                    calculateAccountBalanceAndUpdate(
                        destinationAccountId,
                        operation,
                        calculateNextBalance = false,
                    )
                }
        }
        operationRepository.delete(operation)
    }

    // FIXME Very bad :(
    private fun getAccountId(operation: Operation) =
        when (operation.type) {
            Operation.Type.INCOME -> (operation as Income).accountId
            Operation.Type.EXPENSE -> (operation as Expense).accountId
            Operation.Type.BALANCE_EDIT -> (operation as BalanceEdit).accountId
            else -> throw IllegalArgumentException()
        }

    private suspend fun calculateAccountBalanceAndUpdate(
        accountId: Id,
        operation: Operation,
        calculateNextBalance: Boolean,
    ) = updateBalance(
        accountId = accountId,
        calculateAccountBalance(
            accountId = accountId,
            operation = operation,
            calculateNextBalance = calculateNextBalance
        )
    )

    private suspend fun calculateAccountBalance(
        accountId: Id,
        operation: Operation,
        calculateNextBalance: Boolean,
    ): MoneyAmount =
        accountRepository.getById(accountId)
            .notNull()
            .flatMapLatest {
                if (calculateNextBalance)
                    balanceService.calculateNextAccountBalance(account = it, operation = operation)
                else
                    balanceService.calculatePrevAccountBalance(account = it, operation = operation)
            }
            .first()

    private suspend fun updateBalance(accountId: Id, balance: MoneyAmount) {
        accountRepository.updateBalance(accountId = accountId, balance = balance)
    }
}