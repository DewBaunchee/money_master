package by.varyvoda.android.moneymaster.data.repository.account.operation.category

import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun insert(category: Category)

    suspend fun insert(vararg category: Category)

    suspend fun update(category: Category)

    suspend fun delete(category: Category)

    suspend fun deleteById(id: Id)

    fun getById(id: Id): Flow<Category?>

    fun getAll(): Flow<List<Category>>

    fun getAll(searchString: String): Flow<List<Category>>
}