package by.varyvoda.android.moneymaster.data.dao.account.operation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ExpenseDao : OperationDao<Expense> {

    @Query("DELETE FROM expense WHERE id = :id")
    override suspend fun deleteById(id: UUID)

    @Query("SELECT * FROM expense WHERE id = :id")
    override fun getById(id: UUID): Flow<Expense?>

    @Query("SELECT * FROM expense")
    override fun getAll(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE accountId = :accountId")
    override fun getByAccountId(accountId: Id): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE date BETWEEN :from AND :to")
    override fun getAllBetween(from: PrimitiveDate, to: PrimitiveDate): Flow<List<Expense>>
}