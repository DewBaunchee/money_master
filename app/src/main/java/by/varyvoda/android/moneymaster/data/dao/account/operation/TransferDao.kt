package by.varyvoda.android.moneymaster.data.dao.account.operation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TransferDao : OperationDao<Transfer> {

    @Query("DELETE FROM transfer WHERE id = :id")
    override suspend fun deleteById(id: UUID)

    @Query("SELECT * FROM transfer WHERE id = :id")
    override fun getById(id: UUID): Flow<Transfer?>

    @Query("SELECT * FROM transfer")
    override fun getAll(): Flow<List<Transfer>>

    @Query("SELECT * FROM transfer WHERE sourceAccountId = :accountId OR destinationAccountId = :accountId")
    override fun getByAccountId(accountId: Id): Flow<List<Transfer>>

    @Query("SELECT * FROM transfer WHERE date BETWEEN :from AND :to")
    override fun getAllBetween(from: PrimitiveDate, to: PrimitiveDate): Flow<List<Transfer>>
}