package by.varyvoda.android.moneymaster.data.repository.account.operation.category

import by.varyvoda.android.moneymaster.data.dao.account.operation.CategoryDao
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCategoryRepository(
    private val dao: CategoryDao,
) : CategoryRepository {

    override suspend fun insert(category: Category) = dao.insert(category)

    override suspend fun insert(vararg category: Category) = dao.insert(*category)

    override suspend fun update(category: Category) = dao.update(category)

    override suspend fun delete(category: Category) = dao.delete(category)

    override suspend fun deleteById(id: Id) = dao.deleteById(id)

    override fun getById(id: Id): Flow<Category?> = dao.getById(id)

    override fun getAll(): Flow<List<Category>> = dao.getAll()

    override fun getAll(searchString: String, operationType: Operation.Type): Flow<List<Category>> =
        dao.getAllByOperationType(operationType)
            .map { categories ->
                categories.filter {
                    it.name.contains(
                        searchString,
                        ignoreCase = true
                    )
                }
            }
}