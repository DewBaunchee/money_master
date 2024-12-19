package by.varyvoda.android.moneymaster.data.service.balance

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import kotlinx.coroutines.flow.Flow

interface BalanceService {

    fun calculateTotalBalance(
        targetCurrencyCode: String,
        accounts: List<AccountDetails>
    ): Flow<MoneyAmount>
}