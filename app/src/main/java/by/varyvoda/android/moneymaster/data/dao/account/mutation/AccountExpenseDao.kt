package by.varyvoda.android.moneymaster.data.dao.account.mutation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountExpense
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountExpenseDao : BaseDao<AccountExpense> {

    @Query("DELETE FROM account_expense WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM account_expense WHERE id = :id")
    fun getById(id: Id): Flow<AccountExpense>

    @Query("SELECT * FROM account_expense")
    fun getAll(): Flow<List<AccountExpense>>

    @Query("SELECT * FROM account_expense WHERE accountId = :accountId")
    fun getByAccountId(accountId: Id): Flow<List<AccountExpense>>
}