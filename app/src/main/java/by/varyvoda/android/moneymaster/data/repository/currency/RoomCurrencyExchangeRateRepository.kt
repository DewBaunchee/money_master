package by.varyvoda.android.moneymaster.data.repository.currency

import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyExchangeRateDao
import by.varyvoda.android.moneymaster.data.model.currency.CurrencyExchangeRate
import kotlinx.coroutines.flow.Flow

class RoomCurrencyExchangeRateRepository(
    private val currencyExchangeRateDao: CurrencyExchangeRateDao
) : CurrencyExchangeRateRepository {

    override suspend fun insert(currencyExchangeRate: CurrencyExchangeRate) =
        currencyExchangeRateDao.insert(currencyExchangeRate)

    override suspend fun update(currencyExchangeRate: CurrencyExchangeRate) =
        currencyExchangeRateDao.update(currencyExchangeRate)

    override suspend fun deleteByCodes(soldCurrencyCode: String, boughtCurrencyCode: String) =
        currencyExchangeRateDao.deleteByCodes(soldCurrencyCode, boughtCurrencyCode)

    override fun getByCodes(
        soldCurrencyCode: String,
        boughtCurrencyCode: String
    ): Flow<CurrencyExchangeRate?> =
        currencyExchangeRateDao.getByCodes(soldCurrencyCode, boughtCurrencyCode)

    override fun getAll(): Flow<List<CurrencyExchangeRate>> = currencyExchangeRateDao.getAll()
}