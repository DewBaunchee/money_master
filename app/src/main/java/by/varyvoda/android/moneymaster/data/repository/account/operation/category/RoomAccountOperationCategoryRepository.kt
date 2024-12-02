package by.varyvoda.android.moneymaster.data.repository.account.operation.category

import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountOperationCategoryDao
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomAccountOperationCategoryRepository(
    private val dao: AccountOperationCategoryDao,
) : AccountOperationCategoryRepository {

    override suspend fun insert(category: AccountOperationCategory) = dao.insert(category)

    override suspend fun insert(vararg category: AccountOperationCategory) = dao.insert(*category)

    override suspend fun update(category: AccountOperationCategory) = dao.update(category)

    override suspend fun delete(category: AccountOperationCategory) = dao.delete(category)

    override suspend fun deleteById(id: Id) = dao.deleteById(id)

    override fun getById(id: Id): Flow<AccountOperationCategory> = dao.getById(id)

    override fun getAll(): Flow<List<AccountOperationCategory>> = dao.getAll()

    override fun getAll(searchString: String): Flow<List<AccountOperationCategory>> =
        dao.getAll()
            .map { categories ->
                categories.filter {
                    it.name.contains(
                        searchString,
                        ignoreCase = true
                    )
                }
            }
}