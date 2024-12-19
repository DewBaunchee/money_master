package by.varyvoda.android.moneymaster.data.repository.currency

import by.varyvoda.android.moneymaster.data.model.currency.CurrencyExchangeRate
import kotlinx.coroutines.flow.Flow

interface CurrencyExchangeRateRepository {

    suspend fun insert(currencyExchangeRate: CurrencyExchangeRate)

    suspend fun update(currencyExchangeRate: CurrencyExchangeRate)

    suspend fun deleteByCodes(soldCurrencyCode: String, boughtCurrencyCode: String)

    fun getByCodes(
        soldCurrencyCode: String,
        boughtCurrencyCode: String
    ): Flow<CurrencyExchangeRate?>

    fun getAll(): Flow<List<CurrencyExchangeRate>>
}