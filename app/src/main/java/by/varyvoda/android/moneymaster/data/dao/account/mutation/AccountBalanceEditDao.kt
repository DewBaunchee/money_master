package by.varyvoda.android.moneymaster.data.dao.account.mutation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountBalanceEdit
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountBalanceEditDao : BaseDao<AccountBalanceEdit> {

    @Query("DELETE FROM account_balance_edit WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM account_balance_edit WHERE id = :id")
    fun getById(id: Id): Flow<AccountBalanceEdit>

    @Query("SELECT * FROM account_balance_edit")
    fun getAll(): Flow<List<AccountBalanceEdit>>

    @Query("SELECT * FROM account_balance_edit WHERE accountId = :accountId")
    fun getByAccountId(accountId: Id): Flow<List<AccountBalanceEdit>>
}