package by.varyvoda.android.moneymaster.data.dao.account.operation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.model.account.operation.BalanceEdit
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface BalanceEditDao : OperationDao<BalanceEdit> {

    @Query("DELETE FROM balance_edit WHERE id = :id")
    override suspend fun deleteById(id: UUID)

    @Query("SELECT * FROM balance_edit WHERE id = :id")
    override fun getById(id: UUID): Flow<BalanceEdit?>

    @Query("SELECT * FROM balance_edit")
    override fun getAll(): Flow<List<BalanceEdit>>

    @Query("SELECT * FROM balance_edit WHERE accountId = :accountId")
    override fun getByAccountId(accountId: Id): Flow<List<BalanceEdit>>

    @Query("SELECT * FROM balance_edit WHERE date BETWEEN :from AND :to")
    override fun getAllBetween(from: PrimitiveDate, to: PrimitiveDate): Flow<List<BalanceEdit>>
}