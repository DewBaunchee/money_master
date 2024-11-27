package by.varyvoda.android.moneymaster.data.repository.currency

import by.varyvoda.android.moneymaster.data.model.currency.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun insert(currency: Currency)

    suspend fun update(currency: Currency)

    suspend fun delete(code: String)

    fun getByCode(code: String): Flow<Currency?>

    fun getAll(): Flow<List<Currency>>
}