package by.varyvoda.android.moneymaster.data.dao.account.operation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao : BaseDao<Expense> {

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM expense WHERE id = :id")
    fun getById(id: Id): Flow<Expense>

    @Query("SELECT * FROM expense")
    fun getAll(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE accountId = :accountId")
    fun getByAccountId(accountId: Id): Flow<List<Expense>>
}