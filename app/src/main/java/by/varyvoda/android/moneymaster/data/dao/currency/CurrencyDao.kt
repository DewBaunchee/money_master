package by.varyvoda.android.moneymaster.data.dao.currency

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao : BaseDao<Currency> {

    @Query("DELETE FROM currency WHERE code = :code")
    suspend fun deleteByCode(code: String)

    @Query("SELECT * FROM currency WHERE code = :code")
    fun getByCode(code: String): Flow<Currency>

    @Query("SELECT * FROM currency")
    fun getAll(): Flow<List<Currency>>
}