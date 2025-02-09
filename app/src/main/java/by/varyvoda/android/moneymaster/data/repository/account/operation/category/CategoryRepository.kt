package by.varyvoda.android.moneymaster.data.repository.account.operation.category

import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.util.notNull
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun upsert(category: Category)

    suspend fun update(category: Category)

    suspend fun delete(category: Category)

    suspend fun deleteById(id: Id)

    fun findById(id: Id): Flow<Category?>

    fun getById(id: Id): Flow<Category> = findById(id).notNull()

    fun getAll(): Flow<List<Category>>

    fun getAll(searchString: String, operationType: Operation.Type): Flow<List<Category>>
}