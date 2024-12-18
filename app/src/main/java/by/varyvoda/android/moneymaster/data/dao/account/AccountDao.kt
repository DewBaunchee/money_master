package by.varyvoda.android.moneymaster.data.dao.account

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao : BaseDao<Account> {

    @Query("DELETE FROM account WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM account WHERE id = :id")
    fun getById(id: Id): Flow<Account>

    @Query("SELECT * FROM account")
    fun getAll(): Flow<List<Account>>
}