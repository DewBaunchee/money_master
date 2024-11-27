package by.varyvoda.android.moneymaster.data.dao.account.mutation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountMutationCategory
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountMutationCategoryDao : BaseDao<AccountMutationCategory> {

    @Query("DELETE FROM account_mutation_category WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM account_mutation_category WHERE id = :id")
    fun getById(id: Id): Flow<AccountMutationCategory>

    @Query("SELECT * FROM account_mutation_category")
    fun getAll(): Flow<List<AccountMutationCategory>>
}