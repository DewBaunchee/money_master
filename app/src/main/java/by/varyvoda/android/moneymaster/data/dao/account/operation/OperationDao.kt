package by.varyvoda.android.moneymaster.data.dao.account.operation

import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OperationDao<T : Operation> : BaseDao<T> {

    suspend fun deleteById(id: UUID)

    fun getById(id: UUID): Flow<T?>

    fun getAll(): Flow<List<T>>

    fun getByAccountId(accountId: Id): Flow<List<T>>
}