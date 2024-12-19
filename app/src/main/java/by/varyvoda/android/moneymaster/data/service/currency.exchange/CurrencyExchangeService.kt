package by.varyvoda.android.moneymaster.data.service.currency.exchange

import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeService {

    fun exchangeAmount(
        amount: MoneyAmount,
        currencyCode: String,
        targetCurrencyCode: String
    ): Flow<MoneyAmount>

    suspend fun updateExchangeRate(
        soldCurrencyCode: String,
        boughtCurrencyCode: String,
        rate: MoneyAmount,
    )
}