package by.varyvoda.android.moneymaster.data.service.balance

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import kotlinx.coroutines.flow.Flow

interface BalanceService {

    fun calculateTotalBalance(
        targetCurrencyCode: String,
        accounts: List<Account>
    ): Flow<MoneyAmount>

    fun calculateNextAccountBalance(
        account: Account,
        operation: Operation,
    ): Flow<MoneyAmount>

    fun calculatePrevAccountBalance(
        account: Account,
        operation: Operation,
    ): Flow<MoneyAmount>
}