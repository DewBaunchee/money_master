package by.varyvoda.android.moneymaster.data.service.balance

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.BalanceEdit
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.service.currency.exchange.CurrencyExchangeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

class BalanceServiceImpl(
    private val currencyExchangeService: CurrencyExchangeService,
) : BalanceService {

    private val balanceCalculatorProvider = BalanceCalculatorProvider()

    override fun calculateTotalBalance(
        targetCurrencyCode: String,
        accounts: List<Account>,
    ): Flow<MoneyAmount> =
        combine(
            accounts.map {
                if (it.currencyCode == targetCurrencyCode)
                    flowOf(it.currentBalance)
                else
                    currencyExchangeService.exchangeAmount(
                        it.currentBalance,
                        it.currencyCode,
                        targetCurrencyCode,
                    )
            }
        ) { amounts ->
            amounts.reduce { acc, moneyAmount -> acc + moneyAmount }
        }

    override fun calculateNextAccountBalance(
        account: Account,
        operation: Operation
    ): Flow<MoneyAmount> =
        flowOf(
            balanceCalculatorProvider.getCalculator(operation)
                .calculateNextBalance(
                    account = account,
                    operation = operation,
                )
        )

    override fun calculatePrevAccountBalance(
        account: Account,
        operation: Operation
    ): Flow<MoneyAmount> =
        flowOf(
            balanceCalculatorProvider.getCalculator(operation)
                .calculatePrevBalance(
                    account = account,
                    operation = operation,
                )
        )
}

private interface BalanceCalculator<T : Operation> {

    fun calculateNextBalance(account: Account, operation: T): MoneyAmount

    fun calculatePrevBalance(account: Account, operation: T): MoneyAmount
}

private class BalanceCalculatorProvider {

    private val calculatorMap: Map<Operation.Type, BalanceCalculator<out Operation>> = mapOf(
        Operation.Type.INCOME to object : BalanceCalculator<Income> {
            override fun calculateNextBalance(account: Account, operation: Income) =
                account.currentBalance + operation.amount

            override fun calculatePrevBalance(account: Account, operation: Income) =
                account.currentBalance - operation.amount
        },
        Operation.Type.EXPENSE to object : BalanceCalculator<Expense> {
            override fun calculateNextBalance(account: Account, operation: Expense) =
                account.currentBalance - operation.amount

            override fun calculatePrevBalance(account: Account, operation: Expense) =
                account.currentBalance + operation.amount
        },
        Operation.Type.TRANSFER to object : BalanceCalculator<Transfer> {
            override fun calculateNextBalance(account: Account, operation: Transfer) =
                when (account.id) {
                    operation.sourceAccountId -> account.currentBalance - operation.sentAmount
                    operation.destinationAccountId -> account.currentBalance + operation.receivedAmount
                    else -> throw IllegalArgumentException(
                        "Cannot calculate balance, because account is not part of transfer"
                    )
                }

            override fun calculatePrevBalance(account: Account, operation: Transfer) =
                when (account.id) {
                    operation.sourceAccountId -> account.currentBalance + operation.sentAmount
                    operation.destinationAccountId -> account.currentBalance - operation.receivedAmount
                    else -> throw IllegalArgumentException(
                        "Cannot calculate balance, because account is not part of transfer"
                    )
                }
        },
        Operation.Type.BALANCE_EDIT to object : BalanceCalculator<BalanceEdit> {
            override fun calculateNextBalance(account: Account, operation: BalanceEdit) =
                account.currentBalance + operation.correctionValue

            override fun calculatePrevBalance(account: Account, operation: BalanceEdit) =
                account.currentBalance - operation.correctionValue
        },
    )

    fun getCalculator(operation: Operation): BalanceCalculator<Operation> =
        getCalculator(operation.type)

    @Suppress("UNCHECKED_CAST")
    fun getCalculator(operationType: Operation.Type): BalanceCalculator<Operation> {
        return calculatorMap.getOrElse(operationType) {
            throw IllegalArgumentException("Cannot find balance calculator for $operationType")
        } as BalanceCalculator<Operation>
    }
}