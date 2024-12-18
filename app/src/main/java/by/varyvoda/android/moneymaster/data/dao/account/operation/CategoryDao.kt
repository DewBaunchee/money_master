package by.varyvoda.android.moneymaster.data.dao.account.operation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("DELETE FROM category WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM category WHERE id = :id")
    fun getById(id: Id): Flow<Category?>

    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<Category>>
}