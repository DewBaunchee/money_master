package by.varyvoda.android.moneymaster.data.dao.account.operation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDate
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface IncomeDao : OperationDao<Income> {

    @Query("DELETE FROM income WHERE id = :id")
    override suspend fun deleteById(id: UUID)

    @Query("SELECT * FROM income WHERE id = :id")
    override fun getById(id: UUID): Flow<Income?>

    @Query("SELECT * FROM income")
    override fun getAll(): Flow<List<Income>>

    @Query("SELECT * FROM income WHERE accountId = :accountId")
    override fun getByAccountId(accountId: Id): Flow<List<Income>>

    @Query("SELECT * FROM income WHERE date BETWEEN :from AND :to")
    override fun getAllBetween(from: PrimitiveDate, to: PrimitiveDate): Flow<List<Income>>
}