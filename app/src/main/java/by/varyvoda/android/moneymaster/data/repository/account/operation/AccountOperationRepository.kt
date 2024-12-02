package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface AccountOperationRepository {

    suspend fun insert(operation: AccountOperation)

    fun getAccountOperationsByAccountId(accountId: Id): Flow<List<AccountOperation>>
}