package by.varyvoda.android.moneymaster.data.repository.account.operation.category

import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface AccountOperationCategoryRepository {

    suspend fun insert(category: AccountOperationCategory)

    suspend fun insert(vararg category: AccountOperationCategory)

    suspend fun update(category: AccountOperationCategory)

    suspend fun delete(category: AccountOperationCategory)

    suspend fun deleteById(id: Id)

    fun getById(id: Id): Flow<AccountOperationCategory>

    fun getAll(): Flow<List<AccountOperationCategory>>

    fun getAll(searchString: String): Flow<List<AccountOperationCategory>>
}