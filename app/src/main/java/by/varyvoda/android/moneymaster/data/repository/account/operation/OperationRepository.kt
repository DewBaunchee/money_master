package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.details.account.operation.OperationDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OperationRepository {

    suspend fun insert(operation: Operation)

    suspend fun update(operation: Operation)

    suspend fun delete(operation: Operation)

    suspend fun delete(id: UUID)

    suspend fun delete(id: UUID, operationType: Operation.Type)

    fun getById(id: UUID): Flow<Operation?>

    fun getAll(): Flow<List<Operation>>

    fun getDetailsById(id: UUID): Flow<OperationDetails?>

    fun getAllDetails(): Flow<List<OperationDetails>>

    fun getAllDetailsByAccountId(accountId: Id): Flow<List<OperationDetails>>

    fun getAllDetailsByTypeAndBetweenDateRange(
        operationType: Operation.Type,
        dateRange: PrimitiveDateRange
    ): Flow<List<OperationDetails>>
}