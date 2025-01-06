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

    override fun calculateAccountBalance(
        account: Account,
        operation: Operation,
        addOperation: Boolean,
    ): Flow<MoneyAmount> =
        flowOf(
            balanceCalculatorProvider.getCalculator(operation)
                .calculateBalance(
                    account = account,
                    operation = operation,
                    addOperation = addOperation,
                )
        )
}

private interface BalanceCalculator<T : Operation> {

    fun calculateBalance(account: Account, operation: T, addOperation: Boolean): MoneyAmount

}

private class BalanceCalculatorProvider {

    private val calculatorMap: Map<Operation.Type, BalanceCalculator<out Operation>> = mapOf(
        Operation.Type.INCOME to object : BalanceCalculator<Income> {
            override fun calculateBalance(
                account: Account,
                operation: Income,
                addOperation: Boolean
            ) =
                if (addOperation)
                    account.currentBalance + operation.amount
                else
                    account.currentBalance - operation.amount
        },
        Operation.Type.EXPENSE to object : BalanceCalculator<Expense> {
            override fun calculateBalance(
                account: Account,
                operation: Expense,
                addOperation: Boolean
            ) =
                if (addOperation)
                    account.currentBalance - operation.amount
                else
                    account.currentBalance + operation.amount
        },
        Operation.Type.TRANSFER to object : BalanceCalculator<Transfer> {
            override fun calculateBalance(
                account: Account,
                operation: Transfer,
                addOperation: Boolean
            ) =
                when (account.id) {
                    operation.sourceAccountId ->
                        if (addOperation)
                            account.currentBalance - operation.sentAmount
                        else
                            account.currentBalance + operation.sentAmount

                    operation.destinationAccountId ->
                        if (addOperation)
                            account.currentBalance + operation.receivedAmount
                        else
                            account.currentBalance - operation.receivedAmount

                    else -> throw IllegalArgumentException(
                        "Cannot calculate balance, because account is not part of transfer"
                    )
                }

        },
        Operation.Type.BALANCE_EDIT to object : BalanceCalculator<BalanceEdit> {
            override fun calculateBalance(
                account: Account,
                operation: BalanceEdit,
                addOperation: Boolean
            ) =
                if (addOperation)
                    account.currentBalance + operation.correctionValue
                else
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