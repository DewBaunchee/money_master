package by.varyvoda.android.moneymaster.data.dao.account.mutation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountTransfer
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountTransferDao : BaseDao<AccountTransfer> {

    @Query("DELETE FROM account_transfer WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM account_transfer WHERE id = :id")
    fun getById(id: Id): Flow<AccountTransfer>

    @Query("SELECT * FROM account_transfer")
    fun getAll(): Flow<List<AccountTransfer>>

    @Query("SELECT * FROM account_transfer WHERE accountId = :accountId")
    fun getByAccountId(accountId: Id): Flow<List<AccountTransfer>>
}