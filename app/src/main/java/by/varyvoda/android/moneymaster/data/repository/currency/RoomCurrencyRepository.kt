package by.varyvoda.android.moneymaster.data.repository.currency

import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import kotlinx.coroutines.flow.Flow

class RoomCurrencyRepository(
    private val currencyDao: CurrencyDao
) : CurrencyRepository {

    override suspend fun insert(currency: Currency) = currencyDao.insert(currency)

    override suspend fun update(currency: Currency) = currencyDao.update(currency)

    override suspend fun delete(code: String) = currencyDao.deleteByCode(code)

    override fun getByCode(code: String): Flow<Currency?> = currencyDao.getByCode(code)

    override fun getAll(): Flow<List<Currency>> = currencyDao.getAll()
}