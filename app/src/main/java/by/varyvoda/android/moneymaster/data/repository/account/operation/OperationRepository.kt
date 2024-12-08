package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface OperationRepository {

    suspend fun insert(operation: Operation)

    fun getOperationsByAccountId(accountId: Id): Flow<List<Operation>>
}