package by.varyvoda.android.moneymaster.data.service.currency.exchange

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.currency.CurrencyExchangeRate
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyExchangeRateRepository
import by.varyvoda.android.moneymaster.util.notNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyExchangeServiceImpl(
    private val currencyExchangeRateRepository: CurrencyExchangeRateRepository,
) : CurrencyExchangeService {

    override fun exchangeAmount(
        amount: MoneyAmount,
        currencyCode: String,
        targetCurrencyCode: String
    ): Flow<MoneyAmount> =
        currencyExchangeRateRepository.getByCodes(
            soldCurrencyCode = currencyCode,
            boughtCurrencyCode = targetCurrencyCode
        ).notNull {
            "Currency exchange rate not found: $currencyCode -> $targetCurrencyCode"
        }.map {
            amount * it.rate
        }

    override suspend fun updateExchangeRate(
        soldCurrencyCode: String,
        boughtCurrencyCode: String,
        rate: MoneyAmount,
    ) {
        currencyExchangeRateRepository.insert(
            CurrencyExchangeRate(
                soldCurrencyCode = soldCurrencyCode,
                boughtCurrencyCode = boughtCurrencyCode,
                rate = rate,
            )
        )
    }
}