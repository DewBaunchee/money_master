package by.varyvoda.android.moneymaster.data.dao.currency

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.currency.CurrencyExchangeRate
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyExchangeRateDao : BaseDao<CurrencyExchangeRate> {

    @Query(
        "DELETE FROM currency_exchange_rate " +
                "WHERE soldCurrencyCode = :soldCurrencyCode " +
                "AND boughtCurrencyCode = :boughtCurrencyCode"
    )
    suspend fun deleteByCodes(soldCurrencyCode: String, boughtCurrencyCode: String)

    @Query(
        "SELECT * FROM currency_exchange_rate " +
                "WHERE soldCurrencyCode = :soldCurrencyCode " +
                "AND boughtCurrencyCode = :boughtCurrencyCode"
    )
    fun getByCodes(soldCurrencyCode: String, boughtCurrencyCode: String): Flow<CurrencyExchangeRate>

    @Query("SELECT * FROM currency_exchange_rate")
    fun getAll(): Flow<List<CurrencyExchangeRate>>
}