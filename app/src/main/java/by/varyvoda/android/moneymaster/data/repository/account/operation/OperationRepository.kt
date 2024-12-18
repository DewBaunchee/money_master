package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.details.account.operation.OperationDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OperationRepository {

    suspend fun insert(operation: Operation)

    suspend fun update(operation: Operation)

    suspend fun delete(id: UUID, operationType: Operation.Type? = null)

    fun getById(id: UUID): Flow<Operation?>

    fun getAll(): Flow<List<Operation>>

    fun getDetailsById(id: UUID): Flow<OperationDetails?>

    fun getAllDetails(): Flow<List<OperationDetails>>
}