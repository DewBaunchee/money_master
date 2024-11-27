package by.varyvoda.android.moneymaster.data.dao.account.theme

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountThemeDao : BaseDao<AccountTheme> {

    @Query("DELETE FROM account_theme WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM account_theme WHERE id = :id")
    fun getById(id: Id): Flow<AccountTheme>

    @Query("SELECT * FROM account_theme")
    fun getAll(): Flow<List<AccountTheme>>
}